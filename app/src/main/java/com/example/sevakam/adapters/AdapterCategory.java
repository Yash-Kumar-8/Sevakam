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
import com.example.sevakam.activities.user.CaregoryServiceActivity;

import java.util.ArrayList;

public class AdapterCategory extends RecyclerView.Adapter<AdapterCategory.CategoryViewHolder>{

    private Context context;
    private Activity activity;
    private ArrayList<String> cat_id, cat_name;

    public AdapterCategory(Activity activity, Context context,
                                        ArrayList<String> cat_id,
                                        ArrayList<String> cat_name) {
        this.activity = activity;
        this.context = context;
        this.cat_id = cat_id;
        this.cat_name = cat_name;
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.user_category_row, parent, false);
        return new CategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        holder.cat_name_txt.setText(cat_name.get(position));
        holder.cat_row_Layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, CaregoryServiceActivity.class);
                intent.putExtra("cat_name", cat_name.get(position));
                intent.putExtra("id", String.valueOf(cat_id.get(position)));
                activity.startActivityForResult(intent, 1);
            }
        });
    }

    @Override
    public int getItemCount() {
        return cat_name.size();
    }

    public class CategoryViewHolder extends RecyclerView.ViewHolder {
        TextView cat_name_txt;
        LinearLayout cat_row_Layout;

        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            cat_name_txt = itemView.findViewById(R.id.cat_name_txt);
            cat_row_Layout = itemView.findViewById(R.id.cat_row_Layout);
        }
    }
}
