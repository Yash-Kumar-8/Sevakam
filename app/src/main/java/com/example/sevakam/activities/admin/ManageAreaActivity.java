package com.example.sevakam.activities.admin;

import static android.text.TextUtils.isEmpty;

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
import com.example.sevakam.adapters.CustomAdapterArea;
import com.example.sevakam.adapters.CustomAdapterServiceCategory;
import com.example.sevakam.database.DatabaseHelperArea;
import com.example.sevakam.database.DatabaseHelperServiceCategory;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class ManageAreaActivity extends AppCompatActivity {

    FloatingActionButton add_area;
    RecyclerView recyclerView;
    DatabaseHelperArea AreaDB;
    ArrayList<String> area_id, area_name, city_name, pincode;
    CustomAdapterArea customAdapterArea;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_manage_area);

        add_area = findViewById(R.id.area_add);
        recyclerView = findViewById(R.id.recyclerview_area);

        add_area.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ManageAreaActivity.this, AddAreaActivity.class);
                startActivity(intent);
                finish();
            }
        });

        AreaDB = new DatabaseHelperArea(ManageAreaActivity.this);
        area_id = new ArrayList<>();
        area_name = new ArrayList<>();
        city_name = new ArrayList<>();
        pincode = new ArrayList<>();

        storeAreaDataInArray();

        customAdapterArea = new CustomAdapterArea(ManageAreaActivity.this, this, area_id, area_name, pincode, city_name);
        recyclerView.setAdapter(customAdapterArea);
        recyclerView.setLayoutManager((new LinearLayoutManager(ManageAreaActivity.this)));


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    void storeAreaDataInArray() {
        Cursor cursor = AreaDB.readAllData();
        if(cursor.getCount() == 0){
            Toast.makeText(this, "No Data", Toast.LENGTH_SHORT).show();
        } else {
            while(cursor.moveToNext()){
                String id = cursor.getString(0);
                String name = cursor.getString(1);
                String pin = cursor.getString(2);
                String city = cursor.getString(3);

                // Check if any field is blank ("" or null) and ignore invalid entries
                if (id != null && !id.trim().isEmpty() &&
                        name != null && !name.trim().isEmpty() &&
                        pin != null && !pin.trim().isEmpty() &&
                        city != null && !city.trim().isEmpty() ) {
                    area_id.add(id);
                    area_name.add(name);
                    pincode.add(pin);
                    city_name.add(city);
                }
            }
        }

        if (area_id.isEmpty() || area_name.isEmpty() || pincode.isEmpty() || city_name.isEmpty()) {
            Toast.makeText(this, "Invalid Data: No valid departments available", Toast.LENGTH_SHORT).show();
        }
    }
}