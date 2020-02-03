package com.example.myapplication;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.style.UpdateLayout;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class AdminAddNewItem extends AppCompatActivity {

    private ImageView item1, item2, item3;

    private String categoryName, description, price, pname, saveCurrentDate, saveCurrentTime;

    private Button addNewProductButton;

    private ImageView inputImage;

    private EditText inputProductName, inputProductDescription, inputProductPrice;

    private static final int Imagepick=1;

    private Uri ImageUri;

    private String productrandomkey, downloadImageURL;

    private StorageReference productImagesreference;

    private DatabaseReference productsref;

    private ProgressDialog loadingbar;

    @Override
    protected void onCreate (Bundle savedInstance) {

        super.onCreate(savedInstance);

        setContentView(R.layout.activity_admin_add_product_activity);

       categoryName = getIntent().getExtras().get("Category").toString();
        productImagesreference = FirebaseStorage.getInstance().getReference().child("Product Images");
        productsref= FirebaseDatabase.getInstance().getReference().child("Products");

        addNewProductButton = (Button) findViewById(R.id.admin_button);
        inputImage = (ImageView) findViewById(R.id.select_product_image);
        inputProductName= (EditText) findViewById(R.id.product_name);
        inputProductDescription= (EditText) findViewById(R.id.product_description);
        inputProductPrice= (EditText) findViewById(R.id.product_price);
        loadingbar = new ProgressDialog(this);

        inputImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                openImages();
            }
        });

        addNewProductButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateProductData();
            }
        });

    }

    private void openImages() {
        Intent imageIntent = new Intent();
        imageIntent.setAction(Intent.ACTION_GET_CONTENT);
        imageIntent.setType("image/*");
        startActivityForResult(imageIntent,Imagepick);
    }

    private void validateProductData() {

        description= inputProductDescription.getText().toString();
        price= inputProductPrice.getText().toString();
        pname= inputProductName.getText().toString();

        if(ImageUri==null){
            Toast.makeText(this,"Please select an image", Toast.LENGTH_SHORT);
        }
        else if(TextUtils.isEmpty(description))

        {
            Toast.makeText(this,"Please input a description", Toast.LENGTH_SHORT);

        }
        else if(TextUtils.isEmpty(price))

        {
            Toast.makeText(this,"Please input a price", Toast.LENGTH_SHORT);

        }
        else if(TextUtils.isEmpty(pname))

        {
            Toast.makeText(this,"Please input a product name", Toast.LENGTH_SHORT);

        }

        else{
            StoreProductInformation();
        }
    }

    private void StoreProductInformation() {

        loadingbar.setTitle("Adding new Product");
        loadingbar.setMessage("Please wait while we are adding a new product");
        loadingbar.setCanceledOnTouchOutside(false);
        loadingbar.show();
        Calendar calendar = Calendar.getInstance();

        SimpleDateFormat currentDate= new SimpleDateFormat("MMM dd, yyyy");
        saveCurrentDate= currentDate.format(calendar.getTime());

        SimpleDateFormat currentTime= new SimpleDateFormat("HH:MM:ss");
        saveCurrentTime= currentTime.format(calendar.getTime());

        productrandomkey= saveCurrentDate + saveCurrentTime;

        final StorageReference pathfile= productImagesreference.child(ImageUri.getLastPathSegment()
                + productrandomkey);

        final UploadTask uploadTask = pathfile.putFile(ImageUri);

        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                String message = e.toString();
                Toast.makeText(AdminAddNewItem.this,"Wrong: "+ message, Toast.LENGTH_SHORT);
                loadingbar.dismiss();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(AdminAddNewItem.this,"Image uploaded successfully ", Toast.LENGTH_SHORT);

                Task<Uri> uriTask= uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                    @Override
                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {

                        if(!task.isSuccessful()){
                            throw task.getException();
                        }

                        downloadImageURL = pathfile.getDownloadUrl().toString();
                        return pathfile.getDownloadUrl();
                    }
                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                        if (task.isSuccessful()){
                            downloadImageURL= task.getResult().toString();
                            Toast.makeText(AdminAddNewItem.this, "loading Image Saved to the database....", Toast.LENGTH_SHORT).show();

                            saveProductInfoToDatabase();
                        }
                    }
                });

            }
        });
    }

    private void saveProductInfoToDatabase() {

        HashMap<String,Object> productmap= new HashMap<>();
        productmap.put("id",productrandomkey);
        productmap.put("date",saveCurrentDate);
        productmap.put("time",saveCurrentTime);
        productmap.put("description",description);
        productmap.put("image",downloadImageURL);
        productmap.put("category",categoryName);
        productmap.put("price",price);
        productmap.put("name",pname);

        productsref.child(productrandomkey).updateChildren(productmap).addOnCompleteListener(
                new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        if (task.isSuccessful()){
                            Intent intent = new Intent(AdminAddNewItem.this, AdminCategory.class);
                            startActivity(intent);
                            loadingbar.dismiss();

                            Toast.makeText(AdminAddNewItem.this, "Product is added", Toast.LENGTH_SHORT).show();
                        }

                        else{
                            loadingbar.dismiss();
                            String message= task.getException().toString();
                            Toast.makeText(AdminAddNewItem.this, "Error: ", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        );

    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

//        if (requestCode == Imagepick && requestCode==RESULT_OK && data!=null){

            ImageUri= data.getData();
            inputImage.setImageURI(ImageUri);
        //}
    }
}