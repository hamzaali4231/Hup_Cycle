package com.example.myapplication;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.ViewHold.CartViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


public  class CartActivity extends AppCompatActivity {


    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private Button nextProcess;
    private TextView totalamount_txt;

    private double totalprice = 0.00;

    @Override
    public void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        recyclerView = findViewById(R.id.cart_list);
        recyclerView.setHasFixedSize(true);
        layoutManager= new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        nextProcess = findViewById(R.id.cart_but_2);
        totalamount_txt = findViewById(R.id.cart_total);


        nextProcess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), PaymentMethodMenu.class);
                intent.putExtra("userEmail", getIntent().getStringExtra("userEmail"));
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();

        final DatabaseReference cartlistref = FirebaseDatabase.getInstance().getReference()
                .child("Cart List");

        final FirebaseRecyclerOptions<Cart> options = new FirebaseRecyclerOptions.Builder<Cart>().setQuery
                (cartlistref.child("User View").child("Products"),Cart.class).build();

        FirebaseRecyclerAdapter<Cart, CartViewHolder> adapter = new
                FirebaseRecyclerAdapter<Cart, CartViewHolder>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull CartViewHolder cartViewHolder, int i,
                                                    @NonNull final Cart cartmodel) {
                        cartViewHolder.txtProductQuantity.setText("Quantity: " + cartmodel.getQuantity());
                        cartViewHolder.txtProductPrice.setText(cartmodel.getPrice());
                        cartViewHolder.txtProductName.setText(cartmodel.getName());
                        Picasso.get().load(cartmodel.getImageView()).into(cartViewHolder.imageHolder);

                        String productPrice= cartmodel.getPrice();
                        productPrice= productPrice.replaceAll("[^\\d.]", "");


                        double oneProductPrice= ((Double.valueOf(productPrice))) * Integer.valueOf(cartmodel.getQuantity());
                        totalprice=oneProductPrice+totalprice;
                        totalamount_txt.setText((String.valueOf("£"+totalprice)));

                        cartViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                            @Override

                            public void onClick(View v) {
                                CharSequence options[] = new CharSequence[]{
                                        "Edit",
                                        "Delete"
                                };
                                AlertDialog.Builder builder = new AlertDialog.Builder(CartActivity.this);
                                builder.setTitle("Carts items");
                                builder.setItems(options, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int i) {
                                        if(i==0){
                                            Intent intent = new Intent(CartActivity.this, ProductDetailsActivity.class);
                                            intent.putExtra("id", cartmodel.getId());
                                            startActivity(intent);
                                        }
                                        if(i==1){
                                            cartlistref.child("User View").child("Products").child(cartmodel.getId()).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {

                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    if(task.isSuccessful()){
                                                        Toast.makeText(CartActivity.this, "Item deleted", Toast.LENGTH_SHORT).show();
                                                        System.out.println("Hello" +cartlistref);
                                                    }
                                                }
                                            });
                                        }
                                    }
                                });
                                builder.show();
                            }
                        });

                    }

                    @NonNull
                    @Override
                    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                        View view  = LayoutInflater.from(parent.getContext()).inflate(R.
                                layout.cart_items_layout,parent,false);
                        CartViewHolder holder = new CartViewHolder(view);
                        return holder;
                    }
                };

        recyclerView.setAdapter(adapter);
        adapter.startListening();
    }
}
