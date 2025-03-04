package com.example.sevakam.activities.user;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.sevakam.R;
import com.example.sevakam.database.DatabaseHelperArea;
import com.example.sevakam.database.DatabaseHelperServiceOrderRequest;

import java.util.List;

public class PlaceOrderActivity extends AppCompatActivity {

    TextView service_name, service_detail, service_cost;
    Button place_order;
    AutoCompleteTextView select_area;
    ImageView service_image;
    EditText landmark;
    Bitmap bitmap;
    DatabaseHelperServiceOrderRequest dbHelper;
    DatabaseHelperArea dbArea;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_place_order);

        service_name = findViewById(R.id.service_name);
        service_detail = findViewById(R.id.service_detail);
        service_cost = findViewById(R.id.service_cost);
        place_order = findViewById(R.id.place_order);
        select_area = findViewById(R.id.select_area);
        service_image = findViewById(R.id.service_img);
        landmark = findViewById(R.id.land_mark);

        dbArea = new DatabaseHelperArea(this);
        dbHelper = new DatabaseHelperServiceOrderRequest(this);

        List<String> AreaList = dbArea.getAllArea();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, AreaList);
        select_area.setAdapter(adapter);

        String id = getIntent().getStringExtra("SERVICE_ID");
        String name = getIntent().getStringExtra("SERVICE_NAME");
        String cost = getIntent().getStringExtra("SERVICE_COST");
        String detail = getIntent().getStringExtra("SERVICE_DETAIL");
        String email = getIntent().getStringExtra("USER_MAIL");
        byte[] imageBytes = getIntent().getByteArrayExtra("SERVICE_IMAGE");

        service_name.setText(name);
        service_cost.setText(cost);
        service_detail.setText(detail);
        if (imageBytes != null) {
            bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
            service_image.setImageBitmap(bitmap);
        }

        place_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbHelper.placeOrder(id, name, select_area.getText().toString().trim() , landmark.getText().toString().trim(), email);
                Toast.makeText(PlaceOrderActivity.this, "Order placed", Toast.LENGTH_SHORT).show();
            }
        });




        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}