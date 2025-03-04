package com.example.sevakam.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sevakam.R;
import com.example.sevakam.activities.admin.UpdateOrderActivity;

import java.util.ArrayList;

public class AdapterOrders extends RecyclerView.Adapter<AdapterOrders.OrderViewHolder> {

    private Context context;
    private Activity activity;
    private ArrayList<String> order_id, service_name, area_name, user_mail, landmark;

    public AdapterOrders(Activity activity, Context context,
                             ArrayList<String> order_id,
                             ArrayList<String> service_name,
                             ArrayList<String> area_name,
                             ArrayList<String> user_mail,
                             ArrayList<String> landmark) {
        this.activity = activity;
        this.context = context;
        this.order_id = order_id;
        this.service_name = service_name;
        this.area_name = area_name;
        this.user_mail = user_mail;
        this.landmark = landmark;
    }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.order_row, parent, false);
        return new OrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder holder, int position) {
        holder.order_id.setText(order_id.get(position));
        holder.service_name.setText(service_name.get(position));
        holder.area_name.setText(area_name.get(position));
        holder.user_mail.setText(user_mail.get(position));
        holder.land_mark.setText(landmark.get(position));

        holder.edit_order.setOnClickListener(v -> {
            // Open a dialog or new activity to edit the order
            Intent intent = new Intent(context, UpdateOrderActivity.class);
            intent.putExtra("ORDER_ID", order_id.get(position));
            intent.putExtra("SERVICE_NAME", service_name.get(position));
            intent.putExtra("AREA_NAME", area_name.get(position));
            intent.putExtra("USER_MAIL", user_mail.get(position));
            intent.putExtra("LANDMARK", landmark.get(position));
            activity.startActivityForResult(intent, 1);
        });
    }

    @Override
    public int getItemCount() {
        return order_id.size();
    }

    public class OrderViewHolder extends RecyclerView.ViewHolder{

        TextView order_id, service_name, area_name, user_mail, land_mark;
        ImageView edit_order;

        public OrderViewHolder(@NonNull View itemView) {
            super(itemView);
            order_id = itemView.findViewById(R.id.order_id);
            service_name = itemView.findViewById(R.id.service_name);
            area_name = itemView.findViewById(R.id.area_name);
            user_mail = itemView.findViewById(R.id.user_mail);
            land_mark = itemView.findViewById(R.id.land_mark);
            edit_order = itemView.findViewById(R.id.edt_order);
        }
    }

}
