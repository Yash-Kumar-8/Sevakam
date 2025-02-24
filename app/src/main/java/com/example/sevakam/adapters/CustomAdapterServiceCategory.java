package com.example.sevakam.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sevakam.R;
import com.example.sevakam.activities.admin.AdminServiceListActivity;

import java.util.ArrayList;

public class CustomAdapterServiceCategory extends RecyclerView.Adapter<CustomAdapterServiceCategory.ServiceCategoryViewHolder> {

    private Context context;
    private Activity activity;
    private ArrayList<String> cat_id, cat_name;

    public CustomAdapterServiceCategory(Activity activity, Context context,
                             ArrayList<String> cat_id,
                             ArrayList<String> cat_name) {
        this.activity = activity;
        this.context = context;
        this.cat_id = cat_id;
        this.cat_name = cat_name;
    }

    @NonNull
    @Override
    public ServiceCategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.category_row, parent, false);
        return new ServiceCategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ServiceCategoryViewHolder holder, int position) {
        holder.cat_name_txt.setText(cat_name.get(position)); // Set category name in TextView

        holder.cat_row_Layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, AdminServiceListActivity.class);
                intent.putExtra("cat_name", cat_name.get(position));
//                intent.putExtra("id", String.valueOf(dept_id.get(position)));
//                intent.putExtra("name", String.valueOf(dept_name.get(position)));
//                intent.putExtra("location", String.valueOf(dept_location.get(position)));
                activity.startActivityForResult(intent, 1);
            }
        });
    }

    @Override
    public int getItemCount() {
        return cat_id.size(); // Return actual number of items
    }

    public class ServiceCategoryViewHolder extends RecyclerView.ViewHolder {
        TextView cat_name_txt;
        LinearLayout cat_row_Layout;

        public ServiceCategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            cat_name_txt = itemView.findViewById(R.id.cat_name_txt);// Initialize TextView
            cat_row_Layout = itemView.findViewById(R.id.cat_row_Layout);

        }
    }


}
