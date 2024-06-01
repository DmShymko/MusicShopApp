package com.example.musicshop;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toolbar;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    HashMap goodsMap;
    String goodsName;
    double goodsPrice;
    int goodsQuantity;
    Spinner spinner;
    ArrayList spinnerList;
    ArrayAdapter spinnerAdapter;
    EditText userNameEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        userNameEditText = findViewById(R.id.mainInputYourName);

        createSpinner();
        createMap();
    }

    private void createSpinner() {
        spinner = findViewById(R.id.mainSpinner);
        spinner.setOnItemSelectedListener(this);

        spinnerList = new ArrayList();
        spinnerList.add("Guitar");
        spinnerList.add("Drums");
        spinnerList.add("Keyboard");

        spinnerAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, spinnerList);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(spinnerAdapter);
    }

    private void createMap() {
        goodsMap = new HashMap();
        goodsMap.put("Guitar", 500.0);
        goodsMap.put("Drums", 1500.0);
        goodsMap.put("Keyboard", 1000.0);
    }

    public void addQuantityToOrder(View view) {
        TextView mainOrderCount = findViewById(R.id.mainOrderCount);
       goodsQuantity += 1;
        mainOrderCount.setText(String.format("%d",goodsQuantity));
        TextView mainOrderSummary = findViewById(R.id.mainOrderSummary);
        mainOrderSummary.setText("" +goodsQuantity * goodsPrice);
    }

    public void subtractQuantityToOrder(View view) {
        TextView mainOrderCount = findViewById(R.id.mainOrderCount);

        if (goodsQuantity > 0) {
           goodsQuantity -= 1;
            mainOrderCount.setText(String.format("%d",goodsQuantity));
            TextView mainOrderSummary = findViewById(R.id.mainOrderSummary);
            mainOrderSummary.setText("" +goodsQuantity * goodsPrice);
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        goodsName = spinner.getSelectedItem().toString();
        goodsPrice = (double) goodsMap.get(goodsName);
        TextView mainOrderSummary = findViewById(R.id.mainOrderSummary);
        mainOrderSummary.setText("" +goodsQuantity * goodsPrice);

        ImageView goodsImageView = findViewById(R.id.mainGoodsImageView);

        switch (goodsName) {
            case "Guitar":
                goodsImageView.setImageResource(R.drawable.guitar1);
                break;
            case "Drums":
                goodsImageView.setImageResource(R.drawable.drums);
                break;
            case "Keyboard":
                goodsImageView.setImageResource(R.drawable.keyboard);
                break;
            default:
                goodsImageView.setImageResource(R.drawable.guitar1);
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void addToCart(View view) {
        Order order = new Order();
        order.userName = userNameEditText.getText().toString();
        //Log.d("userNameEditText", order.userName);
        order.goodsName = goodsName;
        order.goodsQuantity = goodsQuantity;
        order.goodsPrice = goodsPrice;
        order.summaryOrder = goodsQuantity * goodsPrice;

        Intent orderIntent = new Intent(MainActivity.this, OrderActivity.class);

        orderIntent.putExtra("userNameForIntent", order.userName);
        orderIntent.putExtra("goodsNameForIntent", order.goodsName);
        orderIntent.putExtra("goodsQuantityForIntent", order.goodsQuantity);
        orderIntent.putExtra("goodsPriceForIntent", order.goodsPrice);
        orderIntent.putExtra("summaryOrderForIntent", order.summaryOrder);
//        orderIntent.putExtra("goodsName", order.goodsName);
//        orderIntent.putExtra("goodsQuantity", order.goodsQuantity);
//        orderIntent.putExtra("orderPrice", order.orderPrice);
        startActivity(orderIntent);

    }
}