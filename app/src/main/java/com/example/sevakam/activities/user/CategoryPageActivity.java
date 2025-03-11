package com.example.sevakam.activities.user;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.Button;
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
import com.example.sevakam.adapters.AdapterCategory;
import com.example.sevakam.adapters.CustomAdapterServiceCategory;
import com.example.sevakam.database.DatabaseHelperServiceCategory;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class CategoryPageActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    DatabaseHelperServiceCategory dbHelperCategory;
    ArrayList<String> cat_id, cat_name;
    AdapterCategory adapterCategory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_category_page);

        recyclerView = findViewById(R.id.caregory_recycler);

        dbHelperCategory = new DatabaseHelperServiceCategory(CategoryPageActivity.this);
        cat_id = new ArrayList<>();
        cat_name = new ArrayList<>();

        storeCategoryDataInArray();

        adapterCategory = new AdapterCategory(CategoryPageActivity.this, this, cat_id, cat_name);
        recyclerView.setAdapter(adapterCategory);
        recyclerView.setLayoutManager((new LinearLayoutManager(CategoryPageActivity.this)));

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    void storeCategoryDataInArray() {
        Cursor cursor = dbHelperCategory.readAllData();
        if(cursor.getCount() == 0){
            Toast.makeText(this, "No Data", Toast.LENGTH_SHORT).show();
        } else {
            while(cursor.moveToNext()){
                String id = cursor.getString(0);
                String name = cursor.getString(1);

                if (id != null && !id.trim().isEmpty() &&
                        name != null && !name.trim().isEmpty() ) {
                    cat_id.add(id);
                    cat_name.add(name);
                }
            }
        }
    }
}