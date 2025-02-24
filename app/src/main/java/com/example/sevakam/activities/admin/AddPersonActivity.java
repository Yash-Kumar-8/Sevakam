package com.example.sevakam.activities.admin;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.sevakam.R;
import com.example.sevakam.database.DatabaseHelperPerson;
import com.example.sevakam.database.DatabaseHelperServiceCategory;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class AddPersonActivity extends AppCompatActivity {

    EditText person_name, person_address;
    Button add_person_btn;
    AutoCompleteTextView service_category;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_person);

        person_name = findViewById(R.id.person_name_txt);
        person_address = findViewById(R.id.person_address_txt);
        service_category = findViewById(R.id.service_cat_txt);
        add_person_btn = findViewById(R.id.add_person_btn);

        AutoCompleteTextView catogeryText = findViewById(R.id.service_cat_txt);
        DatabaseHelperServiceCategory deptDB = new DatabaseHelperServiceCategory(this);
        List<String> deptList = deptDB.getAllCategory();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, deptList);
        catogeryText.setAdapter(adapter);

        add_person_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseHelperPerson myDB = new DatabaseHelperPerson(AddPersonActivity.this);
                myDB.addPerson(person_name.getText().toString().trim(), service_category.getText().toString().trim(), person_address.getText().toString().trim() );
                Toast.makeText(AddPersonActivity.this, "Added SuccessFully", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(AddPersonActivity.this, ManagePersonActivity.class);
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