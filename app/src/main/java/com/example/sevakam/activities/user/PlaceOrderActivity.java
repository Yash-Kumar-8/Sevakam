package com.example.sevakam.activities.user;

import static android.content.ContentValues.TAG;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.sevakam.R;
import com.example.sevakam.database.DatabaseHelperArea;
import com.example.sevakam.database.DatabaseHelperRegister;
import com.example.sevakam.database.DatabaseHelperOrder;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONObject;

import java.util.List;

public class PlaceOrderActivity extends AppCompatActivity implements PaymentResultListener {

    TextView service_name, service_detail, service_cost;
    Button place_order;
    AutoCompleteTextView select_area;
    ImageView service_image;
    EditText landmark;
    Bitmap bitmap;
    DatabaseHelperOrder dbOrder;
    DatabaseHelperArea dbArea;
    String id, name, email, cost, phoneNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_place_order);

        service_name = findViewById(R.id.service_name);
        service_detail = findViewById(R.id.service_detail);
        service_cost = findViewById(R.id.service_cost);
        place_order = findViewById(R.id.place_order);
        select_area = findViewById(R.id.select_area);
        service_image = findViewById(R.id.service_img);
        landmark = findViewById(R.id.land_mark);

        DatabaseHelperRegister dbHelper = new DatabaseHelperRegister(this);

        dbArea = new DatabaseHelperArea(this);
        dbOrder = new DatabaseHelperOrder(this);

        List<String> AreaList = dbArea.getAllArea();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, AreaList);
        select_area.setAdapter(adapter);

        id = getIntent().getStringExtra("SERVICE_ID");
        name = getIntent().getStringExtra("SERVICE_NAME");
        cost = getIntent().getStringExtra("SERVICE_COST");
        String detail = getIntent().getStringExtra("SERVICE_DETAIL");
        email = getIntent().getStringExtra("USER_MAIL");
        byte[] imageBytes = getIntent().getByteArrayExtra("SERVICE_IMAGE");

        phoneNumber = dbHelper.getPhoneNumber(email);

        service_name.setText(name);
        service_cost.setText(cost);
        service_detail.setText(detail);
        if (imageBytes != null) {
            bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
            service_image.setImageBitmap(bitmap);
        }

        Checkout.preload(getApplicationContext());

//        place_order.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                dbOrder.placeOrder(id, name, select_area.getText().toString().trim() , landmark.getText().toString().trim(), email);
//                Toast.makeText(PlaceOrderActivity.this, "Order placed", Toast.LENGTH_SHORT).show();
//            }
//        });

        place_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startPayment();
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }


    public void startPayment(){

        Checkout checkout = new Checkout();
        checkout.setKeyID("rzp_test_LB4mv2jC3Dbte8");

        checkout.setImage(R.drawable.icon);

        final Activity activity = this;

        int amount = (int) (Double.parseDouble(cost) * 100);

        try {
            JSONObject options = new JSONObject();

            options.put("name", "Sevakam");
            options.put("description", "Service: " + name);
            options.put("image", "http://example.com/image/rzp.jpg");
//            options.put("order_id", "order_DBJOWzybf0sJbb");//from response of step 3.
            options.put("theme.color", "#3399cc");
            options.put("currency", "INR");
            options.put("amount", amount);
            options.put("prefill.email", email);
            options.put("prefill.contact",phoneNumber);
            JSONObject retryObj = new JSONObject();
            retryObj.put("enabled", true);
            retryObj.put("max_count", 4);
            options.put("retry", retryObj);

            checkout.open(activity, options);

        } catch(Exception e) {
            Log.e(TAG, "Error in starting Razorpay Checkout", e);
        }
    }

    @Override
    public void onPaymentSuccess(String s) {
        dbOrder.placeOrder(id, name, select_area.getText().toString().trim() , landmark.getText().toString().trim(), email);
        Toast.makeText(PlaceOrderActivity.this, "Payment Success Order placed", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPaymentError(int i, String s) {
        Toast.makeText(this, "Payment Failed", Toast.LENGTH_SHORT).show();
    }

}