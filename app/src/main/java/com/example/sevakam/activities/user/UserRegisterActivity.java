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

public class UserRegisterActivity extends AppCompatActivity {

    EditText user_name, user_phone, sign_user_email, sign_user_password, confirm_password;
    TextView go_login;
    Button signup_btn;

    DatabaseHelperRegister dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_user_register);

        user_name = findViewById(R.id.sign_username_input);
        user_phone = findViewById(R.id.sign_phone_input);
        sign_user_email = findViewById(R.id.sign_mail_input);
        sign_user_password = findViewById(R.id.sign_password_input);
        confirm_password = findViewById(R.id.sign_password_confirm);
        go_login = findViewById(R.id.go_to_login);
        signup_btn = findViewById(R.id.signup_btn);
        dbHelper = new DatabaseHelperRegister(this);

        go_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserRegisterActivity.this, UserLoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

        signup_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name, phone, mail, password, confirm;
                name = user_name.getText().toString();
                phone = user_phone.getText().toString();
                mail = sign_user_email.getText().toString();
                password = sign_user_password.getText().toString();
                confirm = confirm_password.getText().toString();

                if (name.isEmpty() || phone.isEmpty() || mail.isEmpty() || password.isEmpty() || confirm.isEmpty()){
                    Toast.makeText(UserRegisterActivity.this, "Please fill all the fields.", Toast.LENGTH_SHORT).show();
                }
                else{
                    if (password.equals(confirm)){
                        if (dbHelper.checkmail(mail)){
                            Toast.makeText(UserRegisterActivity.this, "User already exists", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        // proceed with Sign up
                        boolean registeredSuccess = dbHelper.insertData(name, phone, mail, password);
                        if (registeredSuccess){
                            Intent intent = new Intent(UserRegisterActivity.this, UserLoginActivity.class);
                            intent.putExtra("phone", phone);
                            intent.putExtra("mail", mail);
                            startActivity(intent);
                            finish();
//                            Toast.makeText(RegisterActivity.this, "Registered Successfully", Toast.LENGTH_SHORT).show();
//                            if (TextUtils.isEmpty(phone)){
//                                Toast.makeText(RegisterActivity.this, "Enter a valid phone number", Toast.LENGTH_SHORT).show();
//                            }else{
//                                sendOTP(phone);
//                            }

                        }
                        else {
                            Toast.makeText(UserRegisterActivity.this, "Registration failed", Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        Toast.makeText(UserRegisterActivity.this, "Password doesn't match ! Confirm again", Toast.LENGTH_SHORT).show();
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

//    private void sendOTP(String phoneNumber){
//        PhoneAuthOptions options =
//                PhoneAuthOptions.newBuilder(mAuth)
//                        .setPhoneNumber("+91"+phoneNumber)       // Phone number to verify
//                        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
//                        .setActivity(this)                 // (optional) Activity for callback binding
//                        // If no activity is passed, reCAPTCHA verification can not be used.
//                        .setCallbacks(mCallbacks)          // OnVerificationStateChangedCallbacks
//                        .build();
//        PhoneAuthProvider.verifyPhoneNumber(options);
//    }

//    private PhoneAuthProvider.OnVerificationStateChangedCallbacks
//    mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
//
//        @Override
//        public void onVerificationCompleted(@NonNull PhoneAuthCredential credential) {
//            final String code = credential.getSmsCode();
//            if (code!= null){
//
//            }
//        }
//
//        @Override
//        public void onVerificationFailed(@NonNull FirebaseException e) {
//
//
//            if (e instanceof FirebaseAuthInvalidCredentialsException) {
//                // Invalid request
//            } else if (e instanceof FirebaseTooManyRequestsException) {
//                // The SMS quota for the project has been exceeded
//            } else if (e instanceof FirebaseAuthMissingActivityForRecaptchaException) {
//                // reCAPTCHA verification attempted with null Activity
//            }
//
//            // Show a message and update the UI
//        }
//
//        @Override
//        public void onCodeSent(@NonNull String verificationId,
//                @NonNull PhoneAuthProvider.ForceResendingToken token) {
//            // The SMS verification code has been sent to the provided phone number, we
//            // now need to ask the user to enter the code and then construct a credential
//            // by combining the code with a verification ID.
//            Log.d(TAG, "onCodeSent:" + verificationId);
//
//            // Save verification ID and resending token so we can use them later
//            mVerificationId = verificationId;
//            mResendToken = token;
//        }
//    };

}