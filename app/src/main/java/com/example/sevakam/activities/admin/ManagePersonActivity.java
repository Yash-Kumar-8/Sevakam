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
import com.example.sevakam.adapters.CustomAdapterArea;
import com.example.sevakam.adapters.CustomAdapterPerson;
import com.example.sevakam.database.DatabaseHelperArea;
import com.example.sevakam.database.DatabaseHelperPerson;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class ManagePersonActivity extends AppCompatActivity {

    FloatingActionButton add_person;
    RecyclerView recyclerView;
    DatabaseHelperPerson PersonDB;
    ArrayList<String> person_id, person_name, cat_name, person_address;
    CustomAdapterPerson customAdapterPerson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_manage_person);

        add_person = findViewById(R.id.person_add);
        recyclerView = findViewById(R.id.recyclerview_person);

        add_person.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ManagePersonActivity.this, AddPersonActivity.class);
                startActivity(intent);
                finish();
            }
        });

        PersonDB = new DatabaseHelperPerson(ManagePersonActivity.this);
        person_id = new ArrayList<>();
        person_name = new ArrayList<>();
        cat_name = new ArrayList<>();
        person_address = new ArrayList<>();

        storePersonDataInArray();

        customAdapterPerson = new CustomAdapterPerson(ManagePersonActivity.this, this, person_id, person_name, cat_name, person_address);
        recyclerView.setAdapter(customAdapterPerson);
        recyclerView.setLayoutManager((new LinearLayoutManager(ManagePersonActivity.this)));


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    void storePersonDataInArray() {
        Cursor cursor = PersonDB.readAllData();
        if(cursor.getCount() == 0){
            Toast.makeText(this, "No Data", Toast.LENGTH_SHORT).show();
        } else {
            while(cursor.moveToNext()){
                String id = cursor.getString(0);
                String name = cursor.getString(1);
                String category = cursor.getString(2);
                String address = cursor.getString(3);

                // Check if any field is blank ("" or null) and ignore invalid entries
                if (id != null && !id.trim().isEmpty() &&
                        name != null && !name.trim().isEmpty() &&
                        category != null && !category.trim().isEmpty() &&
                        address != null && !address.trim().isEmpty()) {
                    person_id.add(id);
                    person_name.add(name);
                    cat_name.add(category);
                    person_address.add(address);
                }
            }
        }

        if (person_id.isEmpty() || person_name.isEmpty() || cat_name.isEmpty() || person_address.isEmpty()) {
            Toast.makeText(this, "Invalid Data: No valid departments available", Toast.LENGTH_SHORT).show();
        }
    }

}