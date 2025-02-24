package com.example.sevakam.adapters;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sevakam.R;
import com.example.sevakam.database.DatabaseHelperPerson;

import java.util.ArrayList;

public class CustomAdapterPerson extends RecyclerView.Adapter<CustomAdapterPerson.PersonViewHolder> {

    private Context context;
    private Activity activity;
    private ArrayList<String> person_id, person_name, cat_name, person_address;
    private DatabaseHelperPerson databaseHelperPerson;

    public CustomAdapterPerson(Activity activity, Context context,
                               ArrayList<String> person_id,
                               ArrayList<String> person_name,
                               ArrayList<String> cat_name,
                               ArrayList<String> person_address) {
        this.activity = activity;
        this.context = context;
        this.person_id = person_id;
        this.person_name = person_name;
        this.cat_name = cat_name;
        this.person_address = person_address;
        this.databaseHelperPerson = new DatabaseHelperPerson(context);
    }

    @NonNull
    @Override
    public CustomAdapterPerson.PersonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.person_row, parent, false);
        return new PersonViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomAdapterPerson.PersonViewHolder holder, int position) {
        holder.person_id_txt.setText(person_id.get(position));
        holder.person_name_txt.setText(person_name.get(position));
        holder.service_category.setText(cat_name.get(position));
        holder.person_address.setText(person_address.get(position));

        // Handle delete button click
        holder.delete_person_btn.setOnClickListener(v -> {
            confirmDelete(position);
        });
    }

    @Override
    public int getItemCount() {
        return person_id.size();
    }

    public class PersonViewHolder extends RecyclerView.ViewHolder {

        TextView person_id_txt, person_name_txt, service_category, person_address;
        ImageView delete_person_btn;

        public PersonViewHolder(@NonNull View itemView) {
            super(itemView);
            person_id_txt = itemView.findViewById(R.id.person_id_txt);
            person_name_txt = itemView.findViewById(R.id.person_name_txt);
            service_category = itemView.findViewById(R.id.service_category);
            person_address = itemView.findViewById(R.id.person_address);
            delete_person_btn = itemView.findViewById(R.id.delete_person_btn);
        }
    }

    private void confirmDelete(int position) {
        String id = person_id.get(position);
        String name = person_name.get(position);

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Delete " + name + " ?");
        builder.setMessage("Are you sure you want to delete " + name + " ?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                databaseHelperPerson.deleteOneRow(id);
                removeItem(position);
                Toast.makeText(context, name + " deleted successfully", Toast.LENGTH_SHORT).show();
            }
        });

        builder.setNegativeButton("No", (dialogInterface, i) -> {
            // Do nothing if the user clicks "No"
        });

        builder.create().show();
    }

    private void removeItem(int position) {
        person_id.remove(position);
        person_name.remove(position);
        cat_name.remove(position);
        person_address.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, person_id.size());
    }
}

