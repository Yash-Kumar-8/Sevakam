package com.example.sevakam;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class OTPverificationActivity extends AppCompatActivity {


    private final TextWatcher textwatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            if (s.length()>0){
                if (selectedPosition == 0){
                    selectedPosition = 1;
                    showKeyboard(otpd2);
                } else if (selectedPosition == 1) {
                    selectedPosition = 2;
                    showKeyboard(otpd3);
                } else if (selectedPosition == 2) {
                    selectedPosition = 3;
                    showKeyboard(otpd4);
                }
            }

        }
    };

    EditText otpd1, otpd2, otpd3, otpd4;
    TextView otp_mail, otp_phone, resend_btn;
    Button verify_btn;

    boolean resendEnabled = false;

    int resendTime = 60;
    int selectedPosition = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_otpverification);

        otp_mail = findViewById(R.id.otp_mail);
        otp_phone = findViewById(R.id.otp_phone);
        resend_btn = findViewById(R.id.resend_otp_btn);
        otpd1 = findViewById(R.id.otpd1);
        otpd2 = findViewById(R.id.otpd2);
        otpd3 = findViewById(R.id.otpd3);
        otpd4 = findViewById(R.id.otpd4);
        verify_btn = findViewById(R.id.verify_otp_btn);


        String getEmail = getIntent().getStringExtra("mail");
        String getPhone = getIntent().getStringExtra("phone");

        otp_mail.setText(getEmail);
        otp_phone.setText(getPhone);

        otpd1.addTextChangedListener(textwatcher);
        otpd2.addTextChangedListener(textwatcher);
        otpd3.addTextChangedListener(textwatcher);
        otpd4.addTextChangedListener(textwatcher);

        showKeyboard(otpd1);
        startCountDownTimer();

        resend_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (resendEnabled){
                    startCountDownTimer();
                }
            }
        });

        verify_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String generateOTP = otpd1.getText().toString() + otpd2.getText().toString() + otpd3.getText().toString() + otpd4.getText().toString();
                if (generateOTP.length() == 4){
                    // verification process


                }
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void showKeyboard(EditText otpd){
        otpd.requestFocus();
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.showSoftInput(otpd, InputMethodManager.SHOW_IMPLICIT);
    }

    private void startCountDownTimer(){
        resendEnabled = false;
        resend_btn.setTextColor(Color.parseColor("#99000000"));

        new CountDownTimer(resendTime * 1000, 1000){

            @Override
            public void onTick(long millisUntilFinished) {
                resend_btn.setText("Resend Code ("+ millisUntilFinished /1000 +")");
            }

            @Override
            public void onFinish() {
                resendEnabled = true;
                resend_btn.setText("Resend Code");
                resend_btn.setTextColor(getResources().getColor(R.color.black));
            }
        }.start();
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_DEL){
            if (selectedPosition == 3){
                selectedPosition = 2;
                showKeyboard(otpd3);
            } else if (selectedPosition == 2) {
                selectedPosition = 1;
                showKeyboard(otpd2);
            } else if (selectedPosition == 1) {
                selectedPosition = 0;
                showKeyboard(otpd1);
            }

            return true;
        }else {
            return super.onKeyUp(keyCode, event);
        }
    }
}