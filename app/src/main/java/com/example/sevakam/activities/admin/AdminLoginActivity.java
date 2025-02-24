package com.example.sevakam.activities.admin;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.sevakam.R;
import com.example.sevakam.activities.user.UserLoginActivity;

public class AdminLoginActivity extends AppCompatActivity {

    EditText admin_id, admin_password;
    Button admin_login_button;
    TextView user_login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_admin_login);

        admin_id = findViewById(R.id.admin_id);
        admin_password = findViewById(R.id.admin_password);
        admin_login_button = findViewById(R.id.admin_login_btn);
        user_login = findViewById(R.id.login_user);

        user_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminLoginActivity.this, UserLoginActivity.class);
                startActivity(intent);
            }
        });

        admin_login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = admin_id.getText().toString();
                String password = admin_password.getText().toString();

//                if (id.isEmpty()||password.isEmpty()){
//                    Toast.makeText(AdminLoginActivity.this, "Please fill all the fields", Toast.LENGTH_SHORT).show();
//                }
//                else{
//                    if (id.equals("admin_sevakam") && password.equals("sevak")){
                        Intent intent = new Intent(AdminLoginActivity.this, AdminHomeActivity.class);
                        startActivity(intent);
                        finish();
//                    }else{
//                        Toast.makeText(AdminLoginActivity.this, "Wrong ID Passwors", Toast.LENGTH_SHORT).show();
//                    }
//                }
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}