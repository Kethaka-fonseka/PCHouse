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
import android.widget.EditText;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity  implements  ItemAdapter.OnItemClickListener {

    private RecyclerView recyclerView;
    private ItemAdapter adapter;
    private List<Item> itemList;
    DatabaseReference dbRef;
    ImageView cart,profile;
    Item item;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cart=findViewById(R.id.cart);
        profile=findViewById(R.id.btnProfile);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this,2));
        itemList = new ArrayList<>();
        adapter = new ItemAdapter(this, itemList);
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(MainActivity.this);
        dbRef=FirebaseDatabase.getInstance().getReference().child("Item");
        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChildren()){
                    for (DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){
                        Item item1=dataSnapshot1.getValue(Item.class);
                        itemList.add(item1);

                    }
                    adapter.notifyDataSetChanged();

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,Cart.class));
            }
        });
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getApplicationContext(),Loging.class));
                finish();
            }
        });

    }


    @Override
    public void OnItemClick(int position) {
        Intent intent=new Intent(this,Item_Display.class);
        Item item1=itemList.get(position);
        intent.putExtra("item",item1);
        startActivity(intent);
    }
}