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
import androidx.recyclerview.widget.RecyclerView;

import com.example.sevakam.R;
import com.example.sevakam.activities.admin.AdminUpdServiceListActivity;

import java.util.ArrayList;

public class CustomAdapterService extends RecyclerView.Adapter<CustomAdapterService.ServiceViewHolder>{

    private Context context;
    private Activity activity;
    private ArrayList<String>  service_id, service_name, service_detail, service_cost;
    private ArrayList<byte[]> service_images;

    public CustomAdapterService(Activity activity, Context context,
                                ArrayList<String> service_id,
                                ArrayList<String> service_name,
                                ArrayList<String> service_detail,
                                ArrayList<String> service_cost,
                                ArrayList<byte[]> service_images) {  // Add images
        this.activity = activity;
        this.context = context;
        this.service_id = service_id;
        this.service_name = service_name;
        this.service_detail = service_detail;
        this.service_cost = service_cost;
        this.service_images = service_images;
    }


    @NonNull
    @Override
    public CustomAdapterService.ServiceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.service_row, parent, false);
        return new ServiceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ServiceViewHolder holder, int position) {
        holder.service_name_txt.setText(service_name.get(position));
        holder.service_detail_txt.setText(service_detail.get(position));
        holder.service_cost_txt.setText(service_cost.get(position));

        // Convert byte array to Bitmap and set image
        byte[] imageBytes = service_images.get(position);
        Bitmap bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
        holder.image_service.setImageBitmap(bitmap);

        holder.edit_service_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, AdminUpdServiceListActivity.class);
                intent.putExtra("id", String.valueOf(service_id.get(position)));
                intent.putExtra("name", String.valueOf(service_name.get(position)));
                intent.putExtra("cost", String.valueOf(service_cost.get(position)));
                intent.putExtra("detail", String.valueOf(service_detail.get(position)));
                activity.startActivityForResult(intent, 1);
                activity.finish();
            }
        });
    }

    @Override
    public int getItemCount() {
        return service_name.size();
    }

    public class ServiceViewHolder extends RecyclerView.ViewHolder {
        TextView service_name_txt, service_detail_txt, service_cost_txt;
        ImageView image_service, edit_service_btn;

        public ServiceViewHolder(@NonNull View itemView) {
            super(itemView);
            service_name_txt = itemView.findViewById(R.id.service_name_txt);
            service_detail_txt = itemView.findViewById(R.id.service_detail_txt);
            service_cost_txt = itemView.findViewById(R.id.service_cost_txt);
            image_service = itemView.findViewById(R.id.image_service);
            edit_service_btn = itemView.findViewById(R.id.edit_service_btn);
        }
    }

}
