package com.example.sevakam.activities.admin;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.sevakam.R;
import com.example.sevakam.database.DatabaseHelperPerson;
import com.example.sevakam.database.DatabaseHelperService;
import com.example.sevakam.database.DatabaseHelperServiceOrderRequest;

import java.util.List;

public class UpdateOrderActivity extends AppCompatActivity {

    EditText orderStatusEditText;
    Spinner personSpinner;
    Button updateButton;
    DatabaseHelperPerson dbHelperPerson;
    DatabaseHelperService dbHelperService;
    DatabaseHelperServiceOrderRequest dbHelperOrder;
    String orderId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_update_order);

        orderStatusEditText = findViewById(R.id.order_status);
        personSpinner = findViewById(R.id.person_spinner);
        updateButton = findViewById(R.id.update_order_btn);

        dbHelperPerson = new DatabaseHelperPerson(this);
        dbHelperOrder = new DatabaseHelperServiceOrderRequest(this);
        dbHelperService = new DatabaseHelperService(this);

        orderId = getIntent().getStringExtra("ORDER_ID");
        String serviceName = getIntent().getStringExtra("SERVICE_NAME");

        String categoryName = dbHelperService.getCategoryNameByServiceName(serviceName);

        List<String> persons = dbHelperPerson.getPersonsByCategory(categoryName);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, persons);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        personSpinner.setAdapter(adapter);

        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String status = orderStatusEditText.getText().toString().trim();
                String person = personSpinner.getSelectedItem().toString();

                dbHelperOrder.updateOrder(orderId, status, person);
                Toast.makeText(UpdateOrderActivity.this, "Order Updated", Toast.LENGTH_SHORT).show();
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