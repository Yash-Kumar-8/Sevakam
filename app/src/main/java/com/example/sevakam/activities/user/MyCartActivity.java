package com.example.sevakam.activities.user;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sevakam.R;
import com.example.sevakam.activities.admin.AdminServiceListActivity;
import com.example.sevakam.adapters.AdapterCart;
import com.example.sevakam.adapters.CustomAdapterService;
import com.example.sevakam.database.DatabaseHelperCart;
import com.example.sevakam.database.DatabaseHelperService;

import java.util.ArrayList;

public class MyCartActivity extends AppCompatActivity {

    ArrayList<String> cart_id, service_name, service_cost, service_detail;
    RecyclerView recyclerView;
    ArrayList<byte[]> service_images;
    DatabaseHelperCart dbHelperCart;
    AdapterCart adapterCart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_my_cart);

        recyclerView = findViewById(R.id.recycler_cart);
        dbHelperCart = new DatabaseHelperCart(MyCartActivity.this);
        String userEmail = getIntent().getStringExtra("USER_MAIL");

        cart_id = new ArrayList<>();
        service_name = new ArrayList<>();
        service_detail = new ArrayList<>();
        service_cost = new ArrayList<>();
        service_images = new ArrayList<>();

        storeCartDataInArray(userEmail);

        adapterCart = new AdapterCart(MyCartActivity.this, this, cart_id, service_name, service_cost, service_detail, service_images);
        recyclerView.setAdapter(adapterCart);
        recyclerView.setLayoutManager((new LinearLayoutManager(MyCartActivity.this)));

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    void storeCartDataInArray(String userEmail) {
        Cursor cursor = dbHelperCart.readSomeData(userEmail);
        if (cursor.getCount() == 0) {
            Toast.makeText(this, "No Data", Toast.LENGTH_SHORT).show();
        } else {
            while (cursor.moveToNext()) {
                String id = cursor.getString(0);
                String name = cursor.getString(2);
                String detail = cursor.getString(3);
                String cost = cursor.getString(4);
                byte[] image = cursor.getBlob(5);  // Retrieve image

                if (id != null && !id.trim().isEmpty() &&
                        name != null && !name.trim().isEmpty() &&
                        detail != null && !detail.trim().isEmpty() &&
                        cost != null && !cost.trim().isEmpty() &&
                        image != null) {

                    cart_id.add(id);
                    service_name.add(name);
                    service_detail.add(detail);
                    service_cost.add(cost);
                    service_images.add(image); // Add image to list
                }
            }
        }
    }
}