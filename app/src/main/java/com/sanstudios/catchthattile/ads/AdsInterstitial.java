package com.sanstudios.catchthattile.ads;

import android.content.Context;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;

public class AdsInterstitial extends AdListener {

    private static InterstitialAd mInterstitialAd;
    private Context ctx;
    private static AdsInterstitial instance = null;

    protected AdsInterstitial(Context ctx,String adUnit){

        this.ctx = ctx;

        mInterstitialAd = new InterstitialAd(this.ctx);
        mInterstitialAd.setAdUnitId(adUnit);
        mInterstitialAd.loadAd(new AdRequest.Builder().build());
        mInterstitialAd.setAdListener(this);


    }

    public static  void showAdd(){
        if(mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
        }
    }

    @Override
    public void onAdClosed() {
        mInterstitialAd.loadAd(new AdRequest.Builder().build());
    }

    @Override
    public void onAdFailedToLoad(int i) {
        Toast.makeText(ctx, "Failed to load!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onAdLoaded() {

    }

    @Override
    public void onAdClicked() {
        Toast.makeText(ctx, "Clicked", Toast.LENGTH_SHORT).show();
    }

    public static AdsInterstitial getInstance(Context ctx,String adUnit){
        if(instance == null){
           instance = new AdsInterstitial(ctx,adUnit);
        }
        return instance;
    }
}
