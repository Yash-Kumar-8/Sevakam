package com.example.sevakam.activities.admin;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sevakam.R;
import com.example.sevakam.adapters.CustomAdapterService;
import com.example.sevakam.database.DatabaseHelperService;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class AdminServiceListActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    FloatingActionButton service_add_page;
    DatabaseHelperService serviceDB;
    ArrayList<String> service_id, service_name, service_detail, service_cost;
    ArrayList<byte[]> service_images;
    CustomAdapterService customAdapterService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_admin_service_list);

        recyclerView = findViewById(R.id.recyclerview_service);
        serviceDB = new DatabaseHelperService(AdminServiceListActivity.this);
        service_add_page = findViewById(R.id.service_add_page);
        service_add_page.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminServiceListActivity.this, AdminAddServicesActivity.class);
                startActivity(intent);
                finish();
            }
        });

        service_id = new ArrayList<>();
        service_name = new ArrayList<>();
        service_detail = new ArrayList<>();
        service_cost = new ArrayList<>();
        service_images = new ArrayList<>();


        storeServiceDataInArray();

        customAdapterService = new CustomAdapterService(AdminServiceListActivity.this, this, service_id, service_name, service_detail, service_cost, service_images);
        recyclerView.setAdapter(customAdapterService);
        recyclerView.setLayoutManager((new LinearLayoutManager(AdminServiceListActivity.this)));



        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    void storeServiceDataInArray() {
        Cursor cursor = serviceDB.readAllData();
        if (cursor.getCount() == 0) {
            Toast.makeText(this, "No Data", Toast.LENGTH_SHORT).show();
        } else {
            while (cursor.moveToNext()) {
                String id = cursor.getString(0);
                String name = cursor.getString(1);
                String detail = cursor.getString(4);
                String cost = cursor.getString(3);
                byte[] image = cursor.getBlob(5);  // Retrieve image

                if (name != null && !name.trim().isEmpty() &&
                        detail != null && !detail.trim().isEmpty() &&
                        cost != null && !cost.trim().isEmpty() &&
                        image != null) {

                    service_id.add(id);
                    service_name.add(name);
                    service_detail.add(detail);
                    service_cost.add(cost);
                    service_images.add(image); // Add image to list
                }
            }
        }

        if (service_name.isEmpty()) {
            Toast.makeText(this, "No valid services available", Toast.LENGTH_SHORT).show();
        }
    }
}