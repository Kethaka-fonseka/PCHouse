package com.example.pchouse;



import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Cart extends AppCompatActivity {
    private RecyclerView recyclerView;
    private CartAdapter adapter;
    private List<TheCart> cartList;
    DatabaseReference dbRef;
    Button home;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        home=findViewById(R.id.btnHome);
        recyclerView = findViewById(R.id.recyclerView2);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        cartList = new ArrayList<>();
        adapter = new CartAdapter(this, cartList);
        recyclerView.setAdapter(adapter);
        dbRef= FirebaseDatabase.getInstance().getReference().child("Cart");
        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.hasChildren()){
                    for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){
                        TheCart cart =dataSnapshot1.getValue(TheCart.class);
                        cartList.add(cart);
                    }
                    adapter.notifyDataSetChanged();
                }
                else{}
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Cart.this,MainActivity.class);
                startActivity(intent);
            }
        });
    }
}