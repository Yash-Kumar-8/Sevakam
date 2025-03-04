package com.example.sevakam.activities.user;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sevakam.R;
import com.example.sevakam.activities.admin.AdminServiceListActivity;
import com.example.sevakam.adapters.AdapterAllService;
import com.example.sevakam.adapters.AdapterHomeCleaning;
import com.example.sevakam.adapters.CustomAdapterService;
import com.example.sevakam.database.DatabaseHelperService;

import java.util.ArrayList;

public class HomePage extends AppCompatActivity {

    RecyclerView cleaning_view, all_service;
    DatabaseHelperService serviceDB;
    ArrayList<String> service_id, service_name, service_cost, service_detail;
    ArrayList<byte[]> service_images;
    AdapterHomeCleaning adapterHomeCleaning;
    AdapterAllService adapterAllService;
    ImageView mycart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home_page);

        cleaning_view = findViewById(R.id.cleaning_recycler);
        all_service = findViewById(R.id.all_service);
        mycart = findViewById(R.id.my_cart);

        String email = getIntent().getStringExtra("USER_MAIL");

        mycart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomePage.this, MyCartActivity.class);
                intent.putExtra("USER_MAIL", email);
                startActivity(intent);
            }
        });



        serviceDB = new DatabaseHelperService(HomePage.this);

        service_id = new ArrayList<>();
        service_name = new ArrayList<>();
        service_cost = new ArrayList<>();
        service_detail = new ArrayList<>();
        service_images = new ArrayList<>();

        storeServiceDataInArrayList();

        adapterHomeCleaning = new AdapterHomeCleaning(HomePage.this, this, service_id, service_name,  service_cost, service_detail, service_images);
        cleaning_view.setAdapter(adapterHomeCleaning);
        cleaning_view.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        adapterAllService = new AdapterAllService(HomePage.this, this, service_id, service_name,  service_cost, service_detail ,service_images);
        all_service.setAdapter(adapterAllService);
        all_service.setLayoutManager((new LinearLayoutManager(HomePage.this)));

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    void storeServiceDataInArrayList() {
        Cursor cursor = serviceDB.readAllData();
        if (cursor.getCount() == 0) {
            Toast.makeText(this, "No Data", Toast.LENGTH_SHORT).show();
        } else {
            while (cursor.moveToNext()) {
                String id = cursor.getString(0);
                String name = cursor.getString(1);
                String cost = cursor.getString(3);
                String detail = cursor.getString(4);
                byte[] image = cursor.getBlob(5);  // Retrieve image

                if (name != null && !name.trim().isEmpty() &&
                        cost != null && !cost.trim().isEmpty() &&
                        detail != null && !detail.trim().isEmpty() &&
                        image != null) {

                    service_id.add(id);
                    service_name.add(name);
                    service_cost.add(cost);
                    service_detail.add(detail);
                    service_images.add(image); // Add image to list
                }
            }
        }

        if (service_name.isEmpty()) {
            Toast.makeText(this, "No valid services available", Toast.LENGTH_SHORT).show();
        }
    }
}
