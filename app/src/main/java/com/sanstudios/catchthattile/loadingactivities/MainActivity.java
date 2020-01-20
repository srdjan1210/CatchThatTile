package com.sanstudios.catchthattile.loadingactivities;


import android.animation.Animator;
import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.anjlab.android.iab.v3.BillingProcessor;
import com.anjlab.android.iab.v3.SkuDetails;
import com.sanstudios.catchthattile.R;
import com.sanstudios.catchthattile.ads.AdsInterstitial;
import com.sanstudios.catchthattile.ads.AdsVideoReward;
import com.sanstudios.catchthattile.classicmode.ClassicModeScreen;
import com.sanstudios.catchthattile.fabActivies.RatingActivity;
import com.sanstudios.catchthattile.fabActivies.SettingsActivity;
import com.sanstudios.catchthattile.inAppBilling.appBilling;
import com.sanstudios.catchthattile.productsRecycler.RecViewGoldenTilesProductsAdapter;
import com.sanstudios.catchthattile.skillmode.SkillModeScreen;
import com.sanstudios.catchthattile.timemode.TimeModeScreen;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    private int gems = 10;
    private int btnIds[] = {R.id.ClassicModeBtn, R.id.TimeModeBtn, R.id.SkillModeBtn};
    private int duration = 2000;
    private int colorIndex = 0;
    private int currentBtnId = 0;

    private String colors[] = {"#039BE5", "#e53935", "#43A047", "#FDD835", "#FF9800"};
    private String currentColor = colors[0];

    private TextView goldenTiles;
    private ObjectAnimator anim;

    private AdsVideoReward videoAd;
    public String rsa = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAkH3LGd9AvmNzo01bq8/3Or/4oJOKsWOiR" +
            "I2/aoawKgjMjhi6co8G+orKbI0SWXkvG5Qq1lSv3QwkrOivC64dbwHkjIH1gyBmtStv8iFn2vsvjqKQXq9IqWxt2HGA+" +
            "p+KzM0SA+DFJxgUICbXd5cXxrWn45PWJqJYSdc+AULHp8aKBEQrmBB0KrA0IJvKclG5SllDQEUlfM/bBm/pPAUNiFUWmxgxd9MkUXEILCgu/uzAXiQX8wTsKYHuYjSVpIiQpY1kxM1zcsWpw" +
            "lFKpstq5bNlE62X61BUNaIxDh9rN2QddLnp3vhBYPST8WWzVR+esXvAWZ/DkqY3XekorEuGUQIDAQAB";

    private BillingProcessor bp;
    private appBilling billing;


    @Override
    public void onActivityReenter(int resultCode, Intent data) {
        super.onActivityReenter(resultCode, data);
        gems = getGemsFromPrefs();
        goldenTiles.setText("" + gems);

    }
    @Override
    public void onCreate(Bundle SavedInstanceState) {
        super.onCreate(SavedInstanceState);
        setContentView(R.layout.activity_main);
        billing = new appBilling(this, this);
        bp = billing.returnBillingInstance(this, null);



        anim = ObjectAnimator.ofObject(btnIds[0], "backgroundColor", new ArgbEvaluator(), Color.parseColor(currentColor), Color.parseColor("#e53935"));
        anim.setDuration(duration).start();
        anim.addListener(getAnimations(btnIds[currentBtnId]));

        goldenTiles = findViewById(R.id.golden_tiles);

        gems = getGemsFromPrefs();
        goldenTiles.setText("" + gems);

        videoAd = new AdsVideoReward(this,"ca-app-pub-3940256099942544/5224354917",goldenTiles);
        AdsInterstitial.getInstance(this,"ca-app-pub-3940256099942544/8691691433");



    }

    public void goClassicMode(View v) {
        Intent intent = new Intent(MainActivity.this, ClassicModeScreen.class);
        startActivity(intent);
    }

    public void goSkillMode(View v) {
        Intent intent = new Intent(MainActivity.this, SkillModeScreen.class);
        startActivity(intent);
    }

    public void goTimeMode(View v) {
        Intent intent = new Intent(MainActivity.this, TimeModeScreen.class);
        startActivity(intent);
    }

    private void setGemsInPrefs(int gemsNum) {
        SharedPreferences.Editor highScorePrefs = getSharedPreferences("gems", MODE_PRIVATE).edit();
        highScorePrefs.putInt("gems", gemsNum);
        highScorePrefs.apply();
        highScorePrefs.commit();
    }

    private int getGemsFromPrefs() {
        SharedPreferences sp = getSharedPreferences("gems", MODE_PRIVATE);
        return sp.getInt("gems", 0);
    }

    public void rewardVideo(View v){
       if(videoAd.getmRewardedVideoAd().isLoaded()) {
           videoAd.showRewardedVideo();
       }else{
           Toast.makeText(this, "Sorry! No videos currently available!", Toast.LENGTH_SHORT).show();
       }
        videoAd.loadRewardedVideoAd("ca-app-pub-3940256099942544/5224354917");


    }

    public void rateGame(View v){
        Intent intent = new Intent(MainActivity.this, RatingActivity.class);
        startActivity(intent);
    }


    private String getNextColor() {
        if (colorIndex == colors.length) {
            colorIndex = 1;
            currentColor = colors[0];
            return colors[0];
        }
        colorIndex++;
        currentColor = colors[colorIndex - 1];
        return colors[colorIndex - 1];

    }

    private int getButtonIndex() {
        currentBtnId++;
        if (currentBtnId == btnIds.length) {
            currentBtnId = 0;
            return currentBtnId;
        }

        return currentBtnId;

    }

    public void settings(View v){
        Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
        startActivity(intent);
    }

    private Animator.AnimatorListener getAnimations(int id) {
        final Button btn = findViewById(id);

        return new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                // Trigger the events after animation is ended
                anim = ObjectAnimator.ofObject(btn, "backgroundColor", new ArgbEvaluator(), Color.parseColor(currentColor), Color.parseColor(getNextColor()));
                anim.addListener(getAnimations(btnIds[getButtonIndex()]));
                anim.setDuration(duration).start();
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }

        };

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (!bp.handleActivityResult(requestCode, resultCode, data)) {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }


    @Override
    public void onDestroy() {
        setGemsInPrefs(gems);
        if (bp != null) {
            bp.release();
        }
        super.onDestroy();
    }

    public void createDialogForBuyingGoldenTiles(View v) {
        List<String> productIds = bp.listOwnedProducts();

        List<SkuDetails> products = bp.getPurchaseListingDetails((ArrayList<String>)productIds);

        /*

        Log.i("test", productDetails.get(0).title + "  "+productDetails.get(0).currency + "  "+productDetails.get(0).priceValue.toString());

        ArrayList<Double> priceList = new ArrayList<>();
        priceList.add(1.99);
        priceList.add(2.95);
        priceList.add(3.00);
        priceList.add(5.00);


        ArrayList<Integer> tileQuantity = new ArrayList<>();
        tileQuantity.add(10);
        tileQuantity.add(50);

        tileQuantity.add(100);
        tileQuantity.add(500);
        */
        createProductsDialog(products);

    }

    private void createProductsDialog(List<SkuDetails> products) {

        AlertDialog.Builder productsDialog = new AlertDialog.Builder(this);
        View convertView = LayoutInflater.from(this).inflate(R.layout.products_layout, null);
        productsDialog.setView(convertView);
        productsDialog.create();
        setProductsRecycler(products, convertView);
        productsDialog.show();
    }

    private void setProductsRecycler(List<SkuDetails> products, View convertView) {
        RecyclerView rv = (RecyclerView) convertView.findViewById(R.id.recViewProducts);
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setAdapter(new RecViewGoldenTilesProductsAdapter(this,products));
    }


}


