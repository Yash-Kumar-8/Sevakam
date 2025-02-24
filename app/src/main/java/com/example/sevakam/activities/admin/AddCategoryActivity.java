package com.example.sevakam.activities.admin;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.sevakam.R;
import com.example.sevakam.database.DatabaseHelperServiceCategory;

public class AddCategoryActivity extends AppCompatActivity {

    EditText category_name;
    Button add_category;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_category);

        category_name = findViewById(R.id.cat_input);
        add_category = findViewById(R.id.add_cat);


        add_category.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseHelperServiceCategory myDB = new DatabaseHelperServiceCategory(AddCategoryActivity.this);
                myDB.addCategory(category_name.getText().toString().trim());
                Toast.makeText(AddCategoryActivity.this, "Added SuccessFully", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(AddCategoryActivity.this, AdminManageServiceActivity.class);
                startActivity(intent);
                finish();
            }
        });


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}