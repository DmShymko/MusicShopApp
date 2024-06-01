package com.example.musicshop;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;


public class OrderActivity extends AppCompatActivity {

    String[] addresses = {"dmshimka@gmail.com"};
    String subject = "Order from Music Shop";
    String emailText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_order);

        Intent recivedOrderIntent = getIntent();
        String userName = recivedOrderIntent.getStringExtra("userNameForIntent");
        String goodsName = recivedOrderIntent.getStringExtra("goodsNameForIntent");
        int goodsQuantity = recivedOrderIntent.getIntExtra("goodsQuantityForIntent", 0);
        double goodsPrice = recivedOrderIntent.getDoubleExtra("goodsPriceForIntent", 0);
        double summaryOrder = recivedOrderIntent.getDoubleExtra("summaryOrderForIntent", 0);

        emailText = "Customer name: " + userName + "\n" + "Goods name: " + goodsName + "\n" +
                "Quantity: " + goodsQuantity + "\n" + "Price: " + goodsPrice + "\n" +
                "Ordier Price: " + summaryOrder;

        TextView orderTextView = findViewById(R.id.orderTextView);

        orderTextView.setText(emailText);
    }

    @SuppressLint("QueryPermissionsNeeded")
    public void submitOrder(View view) {
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // Only email apps handle this.
        intent.putExtra(Intent.EXTRA_EMAIL, addresses);
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.putExtra(Intent.EXTRA_TEXT, emailText);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }
}