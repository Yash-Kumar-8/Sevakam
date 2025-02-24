package com.example.sevakam.activities.admin;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.sevakam.R;
import com.example.sevakam.database.DatabaseHelperService;

public class AdminUpdServiceListActivity extends AppCompatActivity {

    EditText service_name, service_cost, service_detail;
    Button update_service, delete_service;
    String id, name, cost, details;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_admin_upd_service_list);

        service_name = findViewById(R.id.service_name_txt_upd);
        service_cost = findViewById(R.id.service_cost_txt_upd);
        service_detail = findViewById(R.id.service_detail_txt_upd);

        update_service = findViewById(R.id.update_service_btn);
        delete_service = findViewById(R.id.delete_service_btn);

        getAndSetData();

        update_service.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//
                DatabaseHelperService myDB = new DatabaseHelperService(AdminUpdServiceListActivity.this);
                myDB.updateData(id, service_name.getText().toString().trim(), service_cost.getText().toString().trim(), service_detail.getText().toString().trim());
                Intent intent = new Intent(AdminUpdServiceListActivity.this, AdminServiceListActivity.class);
//                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
//                setResult(RESULT_OK);
                startActivity(intent);
                finish();
            }
        });

        delete_service.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirm();
            }
        });



        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    void getAndSetData(){
        if (getIntent().hasExtra("id") &&getIntent().hasExtra("name") && getIntent().hasExtra("cost") && getIntent().hasExtra("detail")){
            // getting data from intent

            id  = getIntent().getStringExtra("id");
            name  = getIntent().getStringExtra("name");
            cost = getIntent().getStringExtra("cost");
            details = getIntent().getStringExtra("detail");
            // setting Intent Data
            service_name.setText(name);
            service_cost.setText(cost);
            service_detail.setText(details);
        }else {
            Toast.makeText(this, "No data", Toast.LENGTH_SHORT).show();
        }
    }

    void confirm() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete " + name + " ?");
        builder.setMessage("Are you sure you want to delete " + name + " ?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                DatabaseHelperService myDB = new DatabaseHelperService(AdminUpdServiceListActivity.this);
                myDB.deleteOneRow(id);
                Intent intent = new Intent(AdminUpdServiceListActivity.this, AdminServiceListActivity.class);
                startActivity(intent);
                setResult(RESULT_OK);
                finish();
            }
        });

        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.create().show();
    }
}