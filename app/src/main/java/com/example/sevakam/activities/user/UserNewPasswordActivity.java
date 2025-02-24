package com.example.sevakam.activities.user;

import android.content.Intent;
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

import com.example.sevakam.R;
import com.example.sevakam.database.DatabaseHelperRegister;

public class UserNewPasswordActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        EditText create_pass_input, confirm_pass_input;
        TextView mail;
        Button create_new_pass;
        DatabaseHelperRegister dbHelper;

        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_new_password);

        create_pass_input = findViewById(R.id.new_password_input);
        confirm_pass_input = findViewById(R.id.confirm_new_pwd);
        mail = findViewById(R.id.create_user_mail);
        create_new_pass = findViewById(R.id.create_new_pwd_btn);
        dbHelper = new DatabaseHelperRegister(this);

        Intent intent = getIntent();
        mail.setText(intent.getStringExtra("mail_input"));

        create_new_pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = mail.getText().toString();
                String new_pass = create_pass_input.getText().toString();
                String confirm_pass = confirm_pass_input.getText().toString();

                if (new_pass.isEmpty()||confirm_pass.isEmpty()){
                    Toast.makeText(UserNewPasswordActivity.this, "Please fill all the fields", Toast.LENGTH_SHORT).show();
                }
                else{
                    if (new_pass.equals(confirm_pass)){
                        boolean checkPassword = dbHelper.updatePassword(email, new_pass);
                        if (checkPassword == true){
                            Intent intent = new Intent(UserNewPasswordActivity.this, UserLoginActivity.class);
                            startActivity(intent);
                            finishAffinity();
                            Toast.makeText(UserNewPasswordActivity.this, "Password updated successfully", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            Toast.makeText(UserNewPasswordActivity.this, "Password not updated", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else{
                        Toast.makeText(UserNewPasswordActivity.this, "Password doesn't match ! Confirm again", Toast.LENGTH_SHORT).show();                    }
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