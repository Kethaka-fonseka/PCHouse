package com.example.pchouse;



import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ItemAdapter extends
        RecyclerView.Adapter<ItemAdapter.ItemViewHolder> {

    private Context mCtx;
    private List<Item> itemList;
    private OnItemClickListener mLister;

    public interface OnItemClickListener{
        void OnItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        mLister=listener;

    }

    public ItemAdapter(Context mCtx, List<Item> itemList) {
        this.mCtx = mCtx;
        this.itemList = itemList;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mCtx).inflate(R.layout.recyclervew_items, parent, false);
        return new ItemViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ItemAdapter.ItemViewHolder holder, int position) {
        Item item =itemList.get(position);
        Glide.with(mCtx).load(item.getUrl()).into(holder.imageView);
        holder.textViewName.setText("Name: " + item.getName());
        holder.textViewPrice.setText("Price: "+item.getPrice());

    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    class  ItemViewHolder extends  RecyclerView.ViewHolder{

        TextView textViewName,textViewPrice;
        ImageView imageView;


        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.image_view_id);
            textViewName=itemView.findViewById(R.id.text_view_name);
            textViewPrice=itemView.findViewById(R.id.text_view_price);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(mLister!=null) {
                        int position = getAdapterPosition();
                        if (position!=RecyclerView.NO_POSITION) {
                            mLister.OnItemClick(position);

                        }
                    }
                }
            });

        }
    }
}
