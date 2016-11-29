package com.batmad.birddefense;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.batmad.birddefense.core.FlappyDefense;
import com.batmad.birddefense.core.IabServices;
import com.batmad.birddefense.core.PlayServices;
import com.batmad.birddefense.util.IabHelper;

import com.batmad.birddefense.util.IabResult;
import com.batmad.birddefense.util.Inventory;
import com.batmad.birddefense.util.Purchase;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.example.games.basegameutils.GameHelper;
import com.google.android.gms.games.Games;
import com.google.android.gms.ads.AdView;

public class AndroidLauncher extends AndroidApplication implements PlayServices, IabServices {
	private GameHelper gameHelper;
	private final static int requestCode = 1;
	private IabHelper iabHelper;
	SharedPreferences prefs;
	SharedPreferences.Editor editor;
	boolean isAdRemoved;
	InterstitialAd adView;

	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();

		adView = new InterstitialAd(this);
		adView.setAdUnitId(getString(R.string.banner_ad_unit_id));
		adView.setAdListener(new AdListener() {
			@Override
			public void onAdLoaded() {
//				Toast.makeText(getApplicationContext(), "Finished Loading Interstitial", Toast.LENGTH_SHORT).show();
			}

			@Override
			public void onAdClosed() {
//				Toast.makeText(getApplicationContext(), "Closed Interstitial", Toast.LENGTH_SHORT).show();
			}
		});

		requestNewInterstitial();

		initialize(new FlappyDefense(this), config);

		prefs = getSharedPreferences("myPrefs", Context.MODE_PRIVATE);
		isAdRemoved = prefs.getBoolean("adsRemoved", false);
		editor = prefs.edit();
//		editor.putBoolean("adsRemoved", false);
//		editor.commit();

		gameHelper = new GameHelper(this, GameHelper.CLIENT_GAMES);
		gameHelper.enableDebugLog(false);

		GameHelper.GameHelperListener gameHelperListener = new GameHelper.GameHelperListener()
		{
			@Override
			public void onSignInFailed(){ }

			@Override
			public void onSignInSucceeded(){ }
		};

		gameHelper.setup(gameHelperListener);

		String base64EncodedPublicKey = getString(R.string.google_play_key);
		iabHelper = new IabHelper(this, base64EncodedPublicKey);

		iabHelper.startSetup(new IabHelper.OnIabSetupFinishedListener() {
			public void onIabSetupFinished(IabResult result) {
				if (!result.isSuccess()) {
//					processPurchases();
				}
//				processPurchases();
			}
		});


	}

	@Override
	public void showOrLoadInterstital() {
		try {
			runOnUiThread(new Runnable() {
				public void run() {
					if(!isAdRemoved) {
						if (adView.isLoaded()) {
							adView.show();
							requestNewInterstitial();
//						Toast.makeText(getApplicationContext(), "Showing Interstitial", Toast.LENGTH_SHORT).show();
						} else {
							AdRequest interstitialRequest = new AdRequest.Builder().build();
							adView.loadAd(interstitialRequest);
//						Toast.makeText(getApplicationContext(), "Loading Interstitial", Toast.LENGTH_SHORT).show();
						}
					}
				}
			});
		} catch (Exception e) {
		}
	}

	private void requestNewInterstitial() {
		AdRequest adRequest = new AdRequest.Builder()
				.addTestDevice("91807A400740D88E1C4AB543AEB65234")
				.build();

		adView.loadAd(adRequest);
	}

	@Override
	protected void onStart()
	{
		super.onStart();
		gameHelper.onStart(this);
	}

	@Override
	protected void onStop()
	{
		super.onStop();
		gameHelper.onStop();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		super.onActivityResult(requestCode, resultCode, data);
		gameHelper.onActivityResult(requestCode, resultCode, data);
		if(iabHelper != null){
			iabHelper.handleActivityResult(requestCode, resultCode, data);
		}
	}

	@Override
	public void signIn()
	{
		try
		{
			runOnUiThread(new Runnable()
			{
				@Override
				public void run()
				{
					gameHelper.beginUserInitiatedSignIn();
				}
			});
		}
		catch (Exception e)
		{
			Gdx.app.log("MainActivity", "Log in failed: " + e.getMessage() + ".");
		}
	}

	@Override
	public void signOut()
	{
		try
		{
			runOnUiThread(new Runnable()
			{
				@Override
				public void run()
				{
					gameHelper.signOut();
				}
			});
		}
		catch (Exception e)
		{
			Gdx.app.log("MainActivity", "Log out failed: " + e.getMessage() + ".");
		}
	}

	@Override
	public void rateGame()
	{
		String str = "Your PlayStore Link";
		startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(str)));
	}

	@Override
	public void unlockAchievement(String str) {
		//TODO
		switch (str){
			case "achievement_conquest":
				Games.Achievements.unlock(gameHelper.getApiClient(), getString(R.string.achievement_conquest));
				break;
			case "achievement_war":
				Games.Achievements.unlock(gameHelper.getApiClient(), getString(R.string.achievement_war));
				break;
			case "achievement_famine":
				Games.Achievements.unlock(gameHelper.getApiClient(), getString(R.string.achievement_famine));
				break;
			case "achievement_death":
				Games.Achievements.unlock(gameHelper.getApiClient(), getString(R.string.achievement_death));
				break;
			case "achievement_apocalypse":
				Games.Achievements.unlock(gameHelper.getApiClient(), getString(R.string.achievement_apocalypse));
				break;
		}

	}

	@Override
	public void submitScore(int highScore)
	{
		if (isSignedIn() == true)
		{
			Games.Leaderboards.submitScore(gameHelper.getApiClient(),
					getString(R.string.leaderboard_highscore), highScore);
		}
	}

	@Override
	public void showAchievement()
	{
		if (isSignedIn() == true)
		{
			startActivityForResult(Games.Achievements.getAchievementsIntent(gameHelper.getApiClient()), requestCode);
		}
		else
		{
			signIn();
		}
	}

	@Override
	public void showScore()
	{
		if (isSignedIn() == true)
		{
			startActivityForResult(Games.Leaderboards.getLeaderboardIntent(gameHelper.getApiClient(),
					getString(R.string.leaderboard_highscore)), requestCode);
		}
		else
		{
			signIn();
		}
	}

	@Override
	public boolean isSignedIn()
	{
		return gameHelper.isSignedIn();
	}

	@Override
	public void removeAds() {
		iabHelper.launchPurchaseFlow(this, SKU_REMOVE_ADS, RC_REQUEST,	mPurchaseFinishedListener, "HANDLE_PAYLOADS");
	}

	@Override
	public void processPurchases() {
		this.runOnUiThread(new Runnable() {
			@Override
			public void run() {
				iabHelper.queryInventoryAsync(mGotInventoryListener);
			}
		});
	}

	IabHelper.QueryInventoryFinishedListener mGotInventoryListener = new IabHelper.QueryInventoryFinishedListener() {
		public void onQueryInventoryFinished(IabResult result, Inventory inventory) {

			if (iabHelper == null) {
				Log.d("CON", "Error in mGotInventoryListener1");
				return;
			}

			if (result.isFailure()) {
				Log.d("CON", "Error in mGotInventoryListener2");
				return;
			}
			else {
				iabHelper.consumeAsync(inventory.getPurchase(SKU_REMOVE_ADS), mConsumeFinishedListener);
				Log.d("CON", "consumeAsync started");
			}

		}
	};

	IabHelper.OnIabPurchaseFinishedListener mPurchaseFinishedListener = new IabHelper.OnIabPurchaseFinishedListener() {
		public void onIabPurchaseFinished(IabResult result, Purchase purchase) {
			if ( purchase == null) return;

			if (iabHelper == null) return;

			if (result.isFailure()){

				return;
			}

			if (purchase.getSku().equals(SKU_REMOVE_ADS)) {
				isAdRemoved = true;
				editor.putBoolean("adsRemoved", true);
				editor.commit();
				Toast.makeText(getContext(), "Платеж проведен успешно! Реклама убрана", Toast.LENGTH_LONG).show();
			}


		}
	};

	IabHelper.OnConsumeFinishedListener mConsumeFinishedListener =
			new IabHelper.OnConsumeFinishedListener() {
				public void onConsumeFinished(Purchase purchase, IabResult result) {
					if (result.isSuccess()) {
						// provision the in-app purchase to the user
						// (for example, credit 50 gold coins to player's character)
						Log.d("CONSUME","SUCCESSFULLY CONSUMED");
					}
					else {
						// handle error
						Log.d("CONSUME","ERROR IN CONSUMING");
					}
				}
			};

}
