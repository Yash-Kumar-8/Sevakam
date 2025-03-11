package com.example.sevakam.activities.user;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.sevakam.R;
import com.example.sevakam.database.DatabaseHelperRegister;

public class ProfileActivity extends AppCompatActivity {

    TextView user_name, user_email, phone_no;
    Button logout_btn;
    DatabaseHelperRegister dbUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_profile);

        user_name = findViewById(R.id.user_name);
        user_email = findViewById(R.id.user_email);
        phone_no = findViewById(R.id.ph_no);
        logout_btn = findViewById(R.id.logout_btn);

        dbUser = new DatabaseHelperRegister(this);

        String email = getIntent().getStringExtra("USER_MAIL");

        Cursor cursor = dbUser.getUserDetails(email);

        if (cursor != null && cursor.moveToFirst()) {
            String name = cursor.getString(0);  // Column index 0 for name
            String phone = cursor.getString(1); // Column index 1 for phone

            user_name.setText(name);
            user_email.setText(email);
            phone_no.setText(phone);
            cursor.close();
        }

        logout_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileActivity.this, UserLoginActivity.class);
                startActivity(intent);
                finishAffinity();
                Toast.makeText(ProfileActivity.this, "Logged Out Successfully", Toast.LENGTH_SHORT).show();
            }
        });



        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}