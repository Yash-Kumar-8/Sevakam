package com.example.sevakam.activities.user;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.sevakam.R;
import com.example.sevakam.database.DatabaseHelperRegister;

public class ForgotPasswordActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        EditText mail_input;
        Button create_new_interface;
        DatabaseHelperRegister dbHelper;

        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_forgot_password);

        mail_input = findViewById(R.id.forgot_mail_input);
        create_new_interface = findViewById(R.id.new_pwd_activity_btn);

        dbHelper = new DatabaseHelperRegister(this);

        create_new_interface.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mail = mail_input.getText().toString();

                if (mail.isEmpty()){
                    Toast.makeText(ForgotPasswordActivity.this, "Please enter email then proceed", Toast.LENGTH_SHORT).show();
                }
                else{
                    boolean checkmail = dbHelper.checkmail(mail);
                    if (checkmail == true){
                        Intent intent = new Intent(ForgotPasswordActivity.this, UserNewPasswordActivity.class);
                        intent.putExtra("mail_input", mail);
                        startActivity(intent);
                    }
                    else{
                        Toast.makeText(ForgotPasswordActivity.this, "User doesn't exists", Toast.LENGTH_SHORT).show();
                    }
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