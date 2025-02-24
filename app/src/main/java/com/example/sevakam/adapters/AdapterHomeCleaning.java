package com.example.sevakam.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sevakam.R;
import com.example.sevakam.activities.user.ServicePageActivity;

import java.util.ArrayList;

public class AdapterHomeCleaning extends RecyclerView.Adapter<AdapterHomeCleaning.HomeCleaningViewHolder> {

    private Context context;
    private Activity activity;
    private ArrayList<String> service_id, service_name, service_cost, service_detail;
    private ArrayList<byte[]> service_images;

    public AdapterHomeCleaning(Activity activity, Context context,
                                ArrayList<String> service_id,
                                ArrayList<String> service_name,
                                ArrayList<String> service_cost,
                                ArrayList<String> service_detail,
                                ArrayList<byte[]> service_images) {  // Add images
        this.activity = activity;
        this.context = context;
        this.service_id = service_id;
        this.service_name = service_name;
        this.service_cost = service_cost;
        this.service_detail = service_detail;
        this.service_images = service_images;
    }

    @NonNull
    @Override
    public HomeCleaningViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.cleaning_row, parent, false);
        return new HomeCleaningViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeCleaningViewHolder holder, int position) {
        holder.service_name_txt.setText(service_name.get(position));
        holder.service_cost_txt.setText(service_cost.get(position));

        // Convert byte array to Bitmap and set image
        byte[] imageBytes = service_images.get(position);
        Bitmap bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
        holder.service_img_view.setImageBitmap(bitmap);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ServicePageActivity.class);
                intent.putExtra("SERVICE_ID", service_id.get(position));
                intent.putExtra("SERVICE_NAME", service_name.get(position));
                intent.putExtra("SERVICE_COST", service_cost.get(position));
                intent.putExtra("SERVICE_DETAIL", service_detail.get(position));
                intent.putExtra("SERVICE_IMAGE", service_images.get(position));  // Pass byte array
                context.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return service_name.size();
    }

    public class HomeCleaningViewHolder extends RecyclerView.ViewHolder{

        ImageView service_img_view;
        TextView service_name_txt, service_cost_txt;

        public HomeCleaningViewHolder(@NonNull View itemView) {
            super(itemView);
            service_img_view = itemView.findViewById(R.id.service_img);
            service_name_txt = itemView.findViewById(R.id.service_name);
            service_cost_txt = itemView.findViewById(R.id.service_cost);
        }
    }


}
