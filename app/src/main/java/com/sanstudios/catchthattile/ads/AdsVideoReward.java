package com.sanstudios.catchthattile.ads;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.reward.RewardItem;
import com.google.android.gms.ads.reward.RewardedVideoAd;
import com.google.android.gms.ads.reward.RewardedVideoAdListener;

public class AdsVideoReward implements RewardedVideoAdListener  {

    private RewardedVideoAd mRewardedVideoAd;
    private Context ctx;
    private TextView gt;
    private String adUnit;



    public AdsVideoReward(Context ctx,String adUnit,TextView gt){
        this.ctx = ctx;
        this.gt = gt;
        this.adUnit = adUnit;

        mRewardedVideoAd = MobileAds.getRewardedVideoAdInstance(this.ctx);
        mRewardedVideoAd.setRewardedVideoAdListener(this);
        loadRewardedVideoAd(adUnit);
    }


    public void loadRewardedVideoAd(String adUnit) {
        mRewardedVideoAd.loadAd(adUnit, new AdRequest.Builder().build());
    }

    private void setGemsInPrefs(int gemsNum) {
        SharedPreferences.Editor highScorePrefs = ctx.getSharedPreferences("gems", ctx.MODE_PRIVATE).edit();
        highScorePrefs.putInt("gems", gemsNum);
        highScorePrefs.apply();
        highScorePrefs.commit();
    }

    private int getGemsFromPrefs() {
        SharedPreferences sp = ctx.getSharedPreferences("gems", ctx.MODE_PRIVATE);
        return sp.getInt("gems", 0);
    }

    @Override
    public void onRewardedVideoAdLoaded() {

    }

    @Override
    public void onRewardedVideoAdOpened() {

    }

    @Override
    public void onRewardedVideoStarted() {

    }

    @Override
    public void onRewardedVideoAdClosed() {
        loadRewardedVideoAd(adUnit);
    }

    @Override
    public void onRewarded(RewardItem rewardItem) {

    }

    @Override
    public void onRewardedVideoAdLeftApplication() {
        Toast.makeText(ctx, "Sorry, no reward!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRewardedVideoAdFailedToLoad(int i) {

    }

    @Override
    public void onRewardedVideoCompleted() {
        int gems = getGemsFromPrefs()+2;
        setGemsInPrefs(gems);
        gt.setText(""+gems);
        Toast.makeText(ctx, "You earned 2 golden tiles!", Toast.LENGTH_SHORT).show();
    }

    public void showRewardedVideo(){
        mRewardedVideoAd.show();
    }

    public RewardedVideoAd getmRewardedVideoAd(){
        return mRewardedVideoAd;
    }






}
