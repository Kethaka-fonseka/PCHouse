package com.example.pchouse;



import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {
    private Context mCtx;
    private List<TheCart> cartList;
    DatabaseReference dbRef;




    public CartAdapter(Context mCtx, List<TheCart> cartList) {
        this.mCtx = mCtx;
        this.cartList = cartList;
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mCtx).inflate(R.layout.recyclerview_cart, parent, false);
        return new CartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final CartViewHolder holder, int position) {
        final TheCart cart=cartList.get(position);
        Glide.with(mCtx).load(cart.getUrl()).into(holder.imageView1);
        holder.textViewName1.setText("Name: " + cart.getName());
        holder.textViewPrice1.setText("Price: "+cart.getPrice());
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteItem(cart.getID());
                Intent intent=new Intent(holder.delete.getContext(),Cart.class);
                holder.delete.getContext().startActivity(intent);
            }
        });
    }

    public void deleteItem(String id){
        dbRef=FirebaseDatabase.getInstance().getReference().child("Cart").child(id);
        dbRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                dbRef.removeValue();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return cartList.size();
    }

    class CartViewHolder extends RecyclerView.ViewHolder{

        TextView textViewName1,textViewPrice1;
        ImageView imageView1;
        Button delete;
        public CartViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView1=itemView.findViewById(R.id.image_view_id1);
            textViewName1=itemView.findViewById(R.id.text_view_name1);
            textViewPrice1=itemView.findViewById(R.id.text_view_price1);
            delete=itemView.findViewById(R.id.btnDelete);
        }
    }
}
