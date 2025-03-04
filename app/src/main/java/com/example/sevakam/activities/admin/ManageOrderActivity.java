package com.example.sevakam.activities.admin;

import android.content.Intent;
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
import com.example.sevakam.adapters.AdapterOrders;
import com.example.sevakam.adapters.CustomAdapterArea;
import com.example.sevakam.adapters.CustomAdapterPerson;
import com.example.sevakam.database.DatabaseHelperArea;
import com.example.sevakam.database.DatabaseHelperPerson;
import com.example.sevakam.database.DatabaseHelperServiceOrderRequest;

import java.util.ArrayList;

public class ManageOrderActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    DatabaseHelperServiceOrderRequest orderDB;
    ArrayList<String> order_id, service_name, area_name, user_mail, landmark;
    AdapterOrders adapterOrders;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_manage_order);

        recyclerView = findViewById(R.id.recyclerview_order);

        orderDB = new DatabaseHelperServiceOrderRequest(ManageOrderActivity.this);

        order_id = new ArrayList<>();
        service_name = new ArrayList<>();
        area_name = new ArrayList<>();
        user_mail = new ArrayList<>();
        landmark = new ArrayList<>();

        storeOrderInArray();

        adapterOrders = new AdapterOrders(ManageOrderActivity.this, this, order_id, service_name, area_name, user_mail, landmark);
        recyclerView.setAdapter(adapterOrders);
        recyclerView.setLayoutManager((new LinearLayoutManager(ManageOrderActivity.this)));


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            // Refresh the data
            order_id.clear();
            service_name.clear();
            area_name.clear();
            user_mail.clear();
            landmark.clear();
            storeOrderInArray();
            adapterOrders.notifyDataSetChanged();
        }
    }


    void storeOrderInArray() {
        Cursor cursor = orderDB.readAllData();
        if(cursor.getCount() == 0){
            Toast.makeText(this, "No Data", Toast.LENGTH_SHORT).show();
        } else {
            while(cursor.moveToNext()){
                String id = cursor.getString(0);
                String serviceName = cursor.getString(2);
                String areaName = cursor.getString(3);
                String email = cursor.getString(5);
                String landMark = cursor.getString(4);

                // Check if any field is blank ("" or null) and ignore invalid entries
                if (id != null && !id.trim().isEmpty() &&
                        serviceName != null && !serviceName.trim().isEmpty() &&
                        areaName != null && !areaName.trim().isEmpty() &&
                        email != null && !email.trim().isEmpty() &&
                        landMark != null && !landMark.trim().isEmpty() ) {
                    order_id.add(id);
                    service_name.add(serviceName);
                    area_name.add(areaName);
                    user_mail.add(email);
                    landmark.add(landMark);
                }
            }
        }
    }
}