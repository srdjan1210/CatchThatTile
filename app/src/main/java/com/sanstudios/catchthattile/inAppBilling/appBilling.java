package com.sanstudios.catchthattile.inAppBilling;


import android.app.Activity;
import android.content.Context;
import android.widget.Toast;

import com.anjlab.android.iab.v3.BillingProcessor;
import com.anjlab.android.iab.v3.TransactionDetails;


public class appBilling implements BillingProcessor.IBillingHandler {

    static BillingProcessor bp;
    static Context ctx;
    private Activity activity;
    public appBilling(Context ctx, Activity activity){
        this.ctx = ctx;
        this.activity = activity;

    }

    public  BillingProcessor returnBillingInstance(Context ctx, String rsa){
        return bp = new BillingProcessor(ctx, rsa, this);
    }
    @Override
    public void onProductPurchased(String productId, TransactionDetails details) {
        Toast.makeText(ctx, "Product purchased", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPurchaseHistoryRestored() {
        Toast.makeText(ctx, "Product history restored", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBillingError(int errorCode, Throwable error) {
        Toast.makeText(ctx, "Product error", Toast.LENGTH_SHORT).show();
        Toast.makeText(ctx, ""+errorCode, Toast.LENGTH_SHORT).show();
        Toast.makeText(ctx, ""+error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBillingInitialized() {
        Toast.makeText(ctx, "Product initialized", Toast.LENGTH_SHORT).show();

    }
}

