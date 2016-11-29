package com.batmad.birddefense.core;

import java.util.ArrayList;

/**
 * Created by tm on 11.02.2016.
 */
public interface IabServices {
    public String SKU_REMOVE_ADS = "ads_free";
    public String  SKU_BUY_BIRD1 = "bird1";
    public String  SKU_BUY_BIRD2 = "bird2";
    public String  SKU_BUY_BIRD3 = "bird3";
    // (arbitrary) request code for the purchase flow
    static final int RC_REQUEST = 10001;
    public void removeAds();
//    public void buyBird(String bird);
    public void processPurchases();
//    public ArrayList<String> getPrices();
}
