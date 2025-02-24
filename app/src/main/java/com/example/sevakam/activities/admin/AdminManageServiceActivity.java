package com.example.sevakam.activities.admin;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sevakam.R;
import com.example.sevakam.adapters.CustomAdapterServiceCategory;
import com.example.sevakam.database.DatabaseHelperServiceCategory;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class AdminManageServiceActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    FloatingActionButton add_setvice_cat;
    DatabaseHelperServiceCategory serviceCategoryDB;
    ArrayList<String> cat_id, cat_name;
    CustomAdapterServiceCategory customAdapterServiceCategory;
    Button all_service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_admin_manage_service);

        add_setvice_cat = findViewById(R.id.service_cat_add);
        recyclerView = findViewById(R.id.recyclerview);
        all_service = findViewById(R.id.all_service);

//        Toolbar toolbar = findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//        getSupportActionBar().setDisplayShowTitleEnabled(false);

        add_setvice_cat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminManageServiceActivity.this, AddCategoryActivity.class);
                startActivity(intent);
                finish();
            }
        });

        all_service.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminManageServiceActivity.this, AdminServiceListActivity.class);
                startActivity(intent);
            }
        });

        serviceCategoryDB = new DatabaseHelperServiceCategory(AdminManageServiceActivity.this);
        cat_id = new ArrayList<>();
        cat_name = new ArrayList<>();

        storeServiceCatDataInArray();

        customAdapterServiceCategory = new CustomAdapterServiceCategory(AdminManageServiceActivity.this, this, cat_id, cat_name);
        recyclerView.setAdapter(customAdapterServiceCategory);
        recyclerView.setLayoutManager((new LinearLayoutManager(AdminManageServiceActivity.this)));

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }


    void storeServiceCatDataInArray() {
        Cursor cursor = serviceCategoryDB.readAllData();
        if(cursor.getCount() == 0){
            Toast.makeText(this, "No Data", Toast.LENGTH_SHORT).show();
        } else {
            while(cursor.moveToNext()){
                String id = cursor.getString(0);
                String name = cursor.getString(1);


                // Check if any field is blank ("" or null) and ignore invalid entries
                if (id != null && !id.trim().isEmpty() &&
                        name != null && !name.trim().isEmpty() ) {
                    cat_id.add(id);
                    cat_name.add(name);
                }
            }
        }

        if (cat_id.isEmpty() || cat_name.isEmpty()) {
            Toast.makeText(this, "Invalid Data: No valid departments available", Toast.LENGTH_SHORT).show();
        }
    }



}