package com.example.sevakam.activities.user;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sevakam.R;
import com.example.sevakam.activities.admin.AdminManageServiceActivity;
import com.example.sevakam.adapters.AdapterAllService;
import com.example.sevakam.adapters.AdapterHomeCleaning;
import com.example.sevakam.database.DatabaseHelperService;
import com.example.sevakam.database.DatabaseHelperServiceCategory;

import java.util.ArrayList;

public class CaregoryServiceActivity extends AppCompatActivity {

    RecyclerView cat_service;
    TextView cat_name;
    DatabaseHelperService DBhelperService;
    ArrayList<String> service_id, service_name, service_cost, service_detail;
    ArrayList<byte[]> service_images;
    AdapterAllService adapterAllService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_caregory_service);

        cat_service = findViewById(R.id.cat_service_recycler);
        cat_name = findViewById(R.id.cat_name);
        
        String name = getIntent().getStringExtra("cat_name");

        cat_name.setText(name);

        DBhelperService = new DatabaseHelperService(CaregoryServiceActivity.this);

        service_id = new ArrayList<>();
        service_name = new ArrayList<>();
        service_cost = new ArrayList<>();
        service_detail = new ArrayList<>();
        service_images = new ArrayList<>();

        storeServiceDataInArrayList(name);

        adapterAllService = new AdapterAllService(CaregoryServiceActivity.this, this, service_id, service_name,  service_cost, service_detail ,service_images);
        cat_service.setAdapter(adapterAllService);
        cat_service.setLayoutManager((new LinearLayoutManager(CaregoryServiceActivity.this)));

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    void storeServiceDataInArrayList(String categoryName) {
        Cursor cursor = DBhelperService.getServicesByCategory(categoryName);
        if (cursor.getCount() == 0) {
            Toast.makeText(this, "No Data for this category", Toast.LENGTH_SHORT).show();
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
                    service_images.add(image);
                }
            }
        }

        if (service_name.isEmpty()) {
            Toast.makeText(this, "No valid services available for this category", Toast.LENGTH_SHORT).show();
        }
    }
}