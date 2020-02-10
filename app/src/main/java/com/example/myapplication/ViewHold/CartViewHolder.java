package com.example.myapplication.ViewHold;

import android.view.View;
import android.widget.TextView;

import com.example.myapplication.Interface.ItemClickListener;
import com.example.myapplication.R;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CartViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView txtProductName, txtProductPrice, txtProductQuantity;
    private ItemClickListener itemClickListener;

    public CartViewHolder(@NonNull View itemView) {
        super(itemView);

        txtProductName = itemView.findViewById(R.id.product_name_cart);
        txtProductPrice = itemView.findViewById(R.id.product_price_cart);
        txtProductQuantity = itemView.findViewById(R.id.product_quantity_cart);

    }


    @Override
    public void onClick(View v) {
        itemClickListener.onClick(v,getAdapterPosition(),false);
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }
}
