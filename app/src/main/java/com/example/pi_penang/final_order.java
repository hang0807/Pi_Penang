package com.example.pi_penang;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class final_order extends AppCompatActivity {

    String timeRange = Pi_laundry.theTimeRange();
    String cookPhoneNumber = cooker_info.getPhoneNumber();
    String cookName = cooker_info.getCookerName();
    String cookAddress = cooker_info.getAddress();
    String cookOrder1 = cooker_info.getMenuOne();
    String cookOrder2 = cooker_info.getMenuTwo();
    String cookOrder3 = cooker_info.getMenuThree();
    String cookQuantity1 = cooker_info.getQuantity1();
    String cookQuantity2 = cooker_info.getQuantity2();
    String cookQuantity3 = cooker_info.getQuantity3();
    boolean deliveryDemand = Pi_laundry.isDeliveryDemand();
    TextView nameFinal;
    TextView phoneNumberFinal;
    TextView addressFinal;
    TextView timeRangeFinal;
    TextView orderFinal;
    ImageView cookImage;
    TextView deliveryInfo;
    TextView name;
    TextView phoneNumber;
    TextView nameFinal2;
    TextView phoneNumberFinal2;
    String picNeeded = cooker_info.getPic();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final_order);

        cookImage = findViewById(R.id.imageView2);
        deliveryInfo = findViewById(R.id.textView32);
        name = findViewById(R.id.textView36);
        phoneNumber = findViewById(R.id.textView34);
        nameFinal2 = findViewById(R.id.nameFinal2);
        phoneNumberFinal2 = findViewById(R.id.phoneNumberFinal2);
        nameFinal = findViewById(R.id.nameFinal);
        orderFinal = findViewById(R.id.orderFinal);
        timeRangeFinal = findViewById(R.id.timeRangeFinal);
        addressFinal = findViewById(R.id.addressFinal);
        phoneNumberFinal = findViewById(R.id.phoneNumberFinal);

        nameFinal.setText(cookName);
        timeRangeFinal.setText(timeRange);
        phoneNumberFinal.setText(cookPhoneNumber);
        addressFinal.setText(cookAddress);

        if (!cookQuantity1.equals("0") && cookQuantity2.equals("0") && cookQuantity3.equals("0")){
            orderFinal.setText(cookOrder1 + " x "+cookQuantity1);
        }
        else if(!cookQuantity2.equals("0") && cookQuantity1.equals("0") && cookQuantity3.equals("0")){
            orderFinal.setText(cookOrder2 + " x "+cookQuantity2);
        }
        else if(!cookQuantity3.equals("0") && cookQuantity2.equals("0") && cookQuantity1.equals("0")){
            orderFinal.setText(cookOrder3 + " x "+cookQuantity3);
        }
        else if(!cookQuantity3.equals("0") && !cookQuantity2.equals("0") && cookQuantity1.equals("0")){
            orderFinal.setText(cookOrder3 + " x "+cookQuantity3 + " , " + cookOrder2 + " x " + cookQuantity2);
        }
        else if(!cookQuantity3.equals("0") && !cookQuantity1.equals("0") && cookQuantity2.equals("0")){
            orderFinal.setText(cookOrder3 + " x "+cookQuantity3 + " , " + cookOrder1 + " x " + cookQuantity1);
        }
        else if(!cookQuantity2.equals("0") && !cookQuantity1.equals("0") && cookQuantity3.equals("0")){
            orderFinal.setText(cookOrder2 + " x "+cookQuantity2 + " , " + cookOrder1 + " x " + cookQuantity1);
        }
        else if(!cookQuantity2.equals("0") && !cookQuantity1.equals("0") && !cookQuantity3.equals("0")){
            orderFinal.setText(cookOrder2 + " x "+cookQuantity2 + " , " + cookOrder1 + " x " + cookQuantity1+ " , " + cookOrder3 + " x " + cookQuantity3);
        }

        if(deliveryDemand == true){

            Picasso.with(getApplicationContext()).load(picNeeded).into(cookImage);

            cookImage.setVisibility(View.VISIBLE);
            deliveryInfo.setVisibility(View.VISIBLE);
            name.setVisibility(View.VISIBLE);
            phoneNumber.setVisibility(View.VISIBLE);
            nameFinal2.setVisibility(View.VISIBLE);
            phoneNumberFinal2.setVisibility(View.VISIBLE);


        }
    }
}
