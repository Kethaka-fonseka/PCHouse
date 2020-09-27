package com.example.pchouse;



import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Item_Display extends AppCompatActivity {
    TextView title,price,qty;
    ImageView image;
    Button add,plus,minus;
    int val1=0;

    DatabaseReference dbRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item__display);

        title=findViewById(R.id.text_title);
        price=findViewById(R.id.text_price);
        image=findViewById(R.id.imageView);
        add=findViewById(R.id.buttonCart);
        qty=findViewById(R.id.text_qty);
        plus=findViewById(R.id.plus);
        minus=findViewById(R.id.minus);





        Intent intent=getIntent();

        final Item i1=intent.getParcelableExtra("item");


        Glide.with(this).load(i1.getUrl()).into(image);
        title.setText(i1.getName());
        price.setText("RS"+String.valueOf(i1.getPrice()));



        plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(val1==i1.getQty()){
                    plus.setEnabled(false);
                }
                else {
                    val1++;
                    minus.setEnabled(true);
                    qty.setText(String.valueOf(val1));
                }
            }
        });

        minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                val1--;
                if(val1!=-1) {
                    plus.setEnabled(true);
                    qty.setText(String.valueOf(val1));
                }
                else{
                    minus.setEnabled(false);
                }
            }
        });
        qty.setText(String.valueOf(val1));


        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dbRef= FirebaseDatabase.getInstance().getReference().child("Cart");
                TheCart cart=new TheCart();
                cart.setID(i1.getID());
                cart.setName(i1.getName());
                cart.setPrice(i1.getPrice()*val1);
                cart.setUrl(i1.getUrl());
                cart.setQty(val1);

                Toast.makeText(getApplicationContext(),"Item successfully added",Toast.LENGTH_SHORT).show();
                dbRef.child(String.valueOf(cart.getID())).setValue(cart);
            }
        });

    }





}