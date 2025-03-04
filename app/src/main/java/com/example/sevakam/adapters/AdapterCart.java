package com.example.sevakam.adapters;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sevakam.R;

import java.util.ArrayList;

public class AdapterCart extends RecyclerView.Adapter<AdapterCart.CartViewHolder>{

    private Context context;
    private Activity activity;
    private ArrayList<String> cart_id, service_name, service_cost, service_detail;
    private ArrayList<byte[]> service_images;

    public AdapterCart(Activity activity, Context context,
                               ArrayList<String> cart_id,
                               ArrayList<String> service_name,
                               ArrayList<String> service_cost,
                               ArrayList<String> service_detail,
                               ArrayList<byte[]> service_images) {  // Add images
        this.activity = activity;
        this.context = context;
        this.cart_id = cart_id;
        this.service_name = service_name;
        this.service_cost = service_cost;
        this.service_detail = service_detail;
        this.service_images = service_images;
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.cart_services_row, parent, false);
        return new CartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        holder.service_name.setText(service_name.get(position));
        holder.service_cost.setText(service_cost.get(position));
        holder.service_detail.setText(service_detail.get(position));

        byte[] imageBytes = service_images.get(position);
        Bitmap bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
        holder.service_img.setImageBitmap(bitmap);
    }

    @Override
    public int getItemCount() {
        return cart_id.size();
    }

    public class CartViewHolder extends RecyclerView.ViewHolder{

        ImageView service_img;
        TextView service_name, service_cost, service_detail;
        Button buy_service;

        public CartViewHolder(@NonNull View itemView) {
            super(itemView);
            service_img = itemView.findViewById(R.id.service_img);
            service_name = itemView.findViewById(R.id.service_name);
            service_cost = itemView.findViewById(R.id.service_cost);
            service_detail = itemView.findViewById(R.id.service_detail);
            buy_service = itemView.findViewById(R.id.buy_service);
        }
    }
}
