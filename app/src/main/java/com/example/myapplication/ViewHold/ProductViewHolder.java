package com.example.myapplication.ViewHold;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myapplication.Interface.ItemClickListener;
import com.example.myapplication.R;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;


public class ProductViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView productName, productDescription, productPrice;
    public ImageView imageView;
    public CardView cardView;
    public ItemClickListener listener;

    public ProductViewHolder( View itemView) {
        super(itemView);

        imageView = (ImageView) itemView.findViewById(R.id.product_image);
        productName = (TextView) itemView.findViewById(R.id.product_name);
        productDescription = (TextView) itemView.findViewById(R.id.product_description);
        productPrice = (TextView) itemView.findViewById(R.id.product_price);
        cardView = (CardView) itemView.findViewById(R.id.cv_id);
//     cardView.setCardElevation(0);


    }

    public void setItemClickListener(ItemClickListener listener){

        this.listener= listener;

    }

    @Override
    public void onClick(View v) {

        listener.onClick(v,getAdapterPosition(),false);
    }
}
