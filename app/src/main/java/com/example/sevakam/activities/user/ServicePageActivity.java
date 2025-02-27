package com.example.sevakam.activities.user;

import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.sevakam.R;
import com.example.sevakam.database.DatabaseHelperCart;

public class ServicePageActivity extends AppCompatActivity {

    ImageView service_image;
    TextView service_name, service_detail, service_cost;
    Button add_cart_btn;
    DatabaseHelperCart dbHelper;
    Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_service_page);

        service_image = findViewById(R.id.service_img);
        service_name = findViewById(R.id.service_name);
        service_detail = findViewById(R.id.service_detail);
        service_cost = findViewById(R.id.service_cost);
        add_cart_btn = findViewById(R.id.add_cart);
        dbHelper = new DatabaseHelperCart(this);

        String id = getIntent().getStringExtra("SERVICE_ID");
        String name = getIntent().getStringExtra("SERVICE_NAME");
        String cost = getIntent().getStringExtra("SERVICE_COST");
        String detail = getIntent().getStringExtra("SERVICE_DETAIL");
        byte[] imageBytes = getIntent().getByteArrayExtra("SERVICE_IMAGE");

        service_name.setText(name);
        service_cost.setText(cost);
        service_detail.setText(detail); // Display service detail

        if (imageBytes != null) {
            bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
            service_image.setImageBitmap(bitmap);
        }

        SharedPreferences sharedPreferences = getSharedPreferences("UserSession", MODE_PRIVATE);
        String userEmail = sharedPreferences.getString("USER_EMAIL", ""); // Retrieve user email

        add_cart_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbHelper.addCart(id, name, cost, detail, bitmap, userEmail);
                Toast.makeText(ServicePageActivity.this, "Service Added to cart", Toast.LENGTH_SHORT).show();
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}