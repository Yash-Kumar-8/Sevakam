package com.example.sevakam.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sevakam.R;
import com.example.sevakam.database.DatabaseHelperArea;

import java.util.ArrayList;

public class CustomAdapterArea extends RecyclerView.Adapter<CustomAdapterArea.AreaViewHolder> {

    private Context context;
    private Activity activity;
    private ArrayList<String> area_id, area_name, area_pin, city_name;
    private DatabaseHelperArea databaseHelperArea;

    public CustomAdapterArea(Activity activity, Context context,
                             ArrayList<String> area_id,
                             ArrayList<String> area_name,
                             ArrayList<String> area_pin,
                             ArrayList<String> city_name) {
        this.activity = activity;
        this.context = context;
        this.area_id = area_id;
        this.area_name = area_name;
        this.area_pin = area_pin;
        this.city_name = city_name;
        this.databaseHelperArea = new DatabaseHelperArea(context);
    }

    @NonNull
    @Override
    public CustomAdapterArea.AreaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.area_row, parent, false);
        return new AreaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomAdapterArea.AreaViewHolder holder, int position) {
        holder.area_name_txt.setText(area_name.get(position));
        holder.city_name_txt.setText(city_name.get(position));
        holder.pin_text.setText(area_pin.get(position));

        // Handle delete button click
        holder.delete_area_btn.setOnClickListener(v ->
                confirmDeleteDialog(position));
    }

    @Override
    public int getItemCount() {
        return area_name.size();
    }

    public class AreaViewHolder extends RecyclerView.ViewHolder {
        TextView area_name_txt, city_name_txt, pin_text;
        ImageView delete_area_btn;

        public AreaViewHolder(@NonNull View itemView) {
            super(itemView);
            area_name_txt = itemView.findViewById(R.id.area_name_txt);
            city_name_txt = itemView.findViewById(R.id.city_name_txt);
            pin_text = itemView.findViewById(R.id.pin_text);
            delete_area_btn = itemView.findViewById(R.id.delete_area_btn);
        }
    }

    private void confirmDeleteDialog(int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Delete " + area_name.get(position) + "?");
        builder.setMessage("Are you sure you want to delete " + area_name.get(position) + "?");

        builder.setPositiveButton("Yes", (dialogInterface, i) -> {
            databaseHelperArea.deleteOneRow(area_id.get(position));
            removeItem(position);
        });

        builder.setNegativeButton("No", (dialogInterface, i) -> dialogInterface.dismiss());

        builder.create().show();
    }

    private void removeItem(int position) {
        area_id.remove(position);
        area_name.remove(position);
        area_pin.remove(position);
        city_name.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, area_id.size());
    }
}
