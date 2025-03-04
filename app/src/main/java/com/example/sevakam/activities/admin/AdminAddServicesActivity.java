package com.example.sevakam.activities.admin;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.sevakam.R;
import com.example.sevakam.database.DatabaseHelperService;
import com.example.sevakam.database.DatabaseHelperServiceCategory;

import java.io.IOException;
import java.util.List;

public class AdminAddServicesActivity extends AppCompatActivity {

    EditText service_name, service_cost, about_service;
    Button add_service, select_image;
    ImageView service_image;
    Bitmap selectedImageBitmap;

    private static final int PICK_IMAGE_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_admin_add_services);

        service_name = findViewById(R.id.service_name_txt);
        service_cost = findViewById(R.id.service_cost_txt);
        about_service = findViewById(R.id.service_detail_txt);
        add_service = findViewById(R.id.add_service_btn);
        select_image = findViewById(R.id.select_image);
        service_image = findViewById(R.id.imageView);

        AutoCompleteTextView categoryText = findViewById(R.id.service_cat_txt);
        DatabaseHelperServiceCategory dbHelper = new DatabaseHelperServiceCategory(this);
        List<String> deptList = dbHelper.getAllCategory();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, deptList);
        categoryText.setAdapter(adapter);

        select_image.setOnClickListener(view -> openImageChooser());

        add_service.setOnClickListener(view -> {
            if (selectedImageBitmap != null) {
                DatabaseHelperService myDB = new DatabaseHelperService(AdminAddServicesActivity.this);
                myDB.addService(service_name.getText().toString().trim(),
                        service_cost.getText().toString().trim(),
                        categoryText.getText().toString().trim(),
                        about_service.getText().toString().trim(),
                        selectedImageBitmap);
                Intent intent = new Intent(AdminAddServicesActivity.this, AdminServiceListActivity.class);
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(AdminAddServicesActivity.this, "Please select an image!", Toast.LENGTH_SHORT).show();
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void openImageChooser() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            try {
                selectedImageBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), data.getData());
                service_image.setImageBitmap(selectedImageBitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}