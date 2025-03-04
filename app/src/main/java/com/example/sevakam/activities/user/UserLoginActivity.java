package com.example.sevakam.activities.user;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.sevakam.MainActivity;
import com.example.sevakam.R;
import com.example.sevakam.activities.admin.AdminLoginActivity;
import com.example.sevakam.database.DatabaseHelperRegister;

public class UserLoginActivity extends AppCompatActivity {

    EditText user_mail, login_password;
    TextView go_signup, forgot_password, admin_login;
    Button login_button;
    DatabaseHelperRegister dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_user_login);

        user_mail = findViewById(R.id.login_mail_input);
        login_password = findViewById(R.id.login_password_input);
        go_signup = findViewById(R.id.go_to_signup);
        forgot_password = findViewById(R.id.forgot_password);
        login_button = findViewById(R.id.login_btn);
        dbHelper = new DatabaseHelperRegister(this);
        admin_login= findViewById(R.id.login_admin);

        admin_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserLoginActivity.this, AdminLoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

        forgot_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserLoginActivity.this, ForgotPasswordActivity.class);
                startActivity(intent);
            }
        });

        go_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserLoginActivity.this, UserRegisterActivity.class);
                startActivity(intent);
                finish();
            }
        });

//        login_button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                boolean islogged = dbHelper.checkUser(user_mail.getText().toString(), login_password.getText().toString());
//                if (islogged){
//                    Intent intent = new Intent(UserLoginActivity.this, HomePage.class);
//                    startActivity(intent);
//                    finish();
//                }
//                else{
//                    Toast.makeText(UserLoginActivity.this, "Login Failed !", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });

        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = user_mail.getText().toString();
                String password = login_password.getText().toString();

                if (dbHelper.checkUser(email, password)) {
                    // Store user email in SharedPreferences
                    getSharedPreferences("UserSession", MODE_PRIVATE)
                            .edit()
                            .putString("USER_EMAIL", email)
                            .apply();

                    SharedPreferences sharedPreferences = getSharedPreferences("UserSession", MODE_PRIVATE);
                    String userEmail = sharedPreferences.getString("USER_EMAIL", "");

                    Toast.makeText(UserLoginActivity.this, "Login Successful!", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(UserLoginActivity.this, HomePage.class);
                    intent.putExtra("USER_MAIL", userEmail);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(UserLoginActivity.this, "Login Failed!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}