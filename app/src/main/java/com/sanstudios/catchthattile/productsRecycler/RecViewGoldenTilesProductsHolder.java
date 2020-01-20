package com.sanstudios.catchthattile.productsRecycler;


import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.sanstudios.catchthattile.R;

public class RecViewGoldenTilesProductsHolder extends RecyclerView.ViewHolder {
    TextView productName;
    Button productPrice;

    public RecViewGoldenTilesProductsHolder(View itemView) {
        super(itemView);
        productName = (TextView)itemView.findViewById(R.id.productName);
        productPrice = (Button)itemView.findViewById(R.id.buyProduct);
    }
}
