package com.example.ecommerce;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class PopularAdapter extends RecyclerView.Adapter<PopularAdapter.ImageViewHolder> {
    Context context;
    List<Popular> mPopulars;

    public PopularAdapter(Context context, List<Popular> mPopulars) {
        this.context = context;
        this.mPopulars = mPopulars;
    }

    @NonNull
    @Override
    public PopularAdapter.ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.popular_item,parent,false);
        return new ImageViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder, int position) {

        Popular popularcur = mPopulars.get(position);
        holder.prod_name.setText(popularcur.getProduct_title());
        holder.prod_price.setText(popularcur.getProduct_price());
        Picasso.get()
                .load(popularcur.getProduct_image())
                .placeholder(R.drawable.ic_launcher_background)
                .fit()
                .centerCrop()
                .into(holder.prod_img);
    }

    @Override
    public int getItemCount() {
        return mPopulars.size();
    }

    public class ImageViewHolder extends RecyclerView.ViewHolder {
        TextView prod_name , prod_price ;
        ImageView prod_img;
        public ImageViewHolder(@NonNull View itemView) {
            super(itemView);
            prod_img = itemView.findViewById(R.id.item_img_id);
            prod_price = itemView.findViewById(R.id.item_price_id);
            prod_name = itemView.findViewById(R.id.item_product_id);
        }
    }
}
