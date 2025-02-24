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
import com.example.sevakam.database.DatabaseHelperArea;
import com.example.sevakam.database.DatabaseHelperPerson;

public class AddAreaActivity extends AppCompatActivity {

    EditText area_name, area_pin, city_name;
    Button add_area_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_area);

        area_name = findViewById(R.id.area_name_txt);
        area_pin = findViewById(R.id.area_pin_text);
        city_name = findViewById(R.id.city_name_txt);

        add_area_btn = findViewById(R.id.add_area_btn);

        add_area_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseHelperArea myDB = new DatabaseHelperArea(AddAreaActivity.this);
                myDB.addArea(area_name.getText().toString().trim(), area_pin.getText().toString().trim(), city_name.getText().toString().trim() );
                Toast.makeText(AddAreaActivity.this, "Added SuccessFully", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(AddAreaActivity.this, ManageAreaActivity.class);
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