package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.myapplication.Interface.ItemClickListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ProductViewHolder> {

    Context context;
    List<Products> products;

    public MyAdapter(Context context , List<Products> products)
    {
        this.context = context;
        this.products = products;
    }


    @Override
    public MyAdapter.ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.products_items_layout,
                parent,false);
        return new ProductViewHolder(view.getRootView());
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder productViewHolder, final int i) {
        productViewHolder.productName.setText(products.get(i).getName());
        productViewHolder.productDescription.setText(products.get(i).getDescription());
        productViewHolder.productPrice.setText("Price = £" + products.get(i).getPrice());
        Picasso.get().load(products.get(i).getImage()).into(productViewHolder.imageView);

        Products product= products.get(i);

        productViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, ProductDetailsActivity.class);
                intent.putExtra("id",products.get(i).getId());

            }
        });
    }



    @Override
    public int getItemCount() {
        return products.size();
    }

    class ProductViewHolder extends RecyclerView.ViewHolder{
        TextView productName, productDescription, productPrice;
        ImageView imageView;
        CardView cardView;
        ItemClickListener listener;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = (ImageView) itemView.findViewById(R.id.product_image);
            productName = (TextView) itemView.findViewById(R.id.product_name);
            productDescription = (TextView) itemView.findViewById(R.id.product_description);
            productPrice = (TextView) itemView.findViewById(R.id.product_price);
            cardView = (CardView) itemView.findViewById(R.id.cv_id);
            cardView.setCardElevation(-5);

        }

    }
}
