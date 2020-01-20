package com.sanstudios.catchthattile.productsRecycler;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.anjlab.android.iab.v3.SkuDetails;
import com.sanstudios.catchthattile.R;

import java.util.List;

public class RecViewGoldenTilesProductsAdapter extends RecyclerView.Adapter<RecViewGoldenTilesProductsHolder> {

    private Context ctx;
    private List<SkuDetails> products;


    public RecViewGoldenTilesProductsAdapter(Context ctx, List<SkuDetails> products ){

        this.ctx = ctx;
        this.products = products;
    }
    @NonNull
    @Override
    public RecViewGoldenTilesProductsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new RecViewGoldenTilesProductsHolder(LayoutInflater.from(ctx).inflate(R.layout.products_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecViewGoldenTilesProductsHolder holder, int position) {
        holder.productName.setText(products.get(position).title);
        holder.productPrice.setText(products.get(position).priceText);

    }

    @Override
    public int getItemCount() {
        return products.size();
    }
}
