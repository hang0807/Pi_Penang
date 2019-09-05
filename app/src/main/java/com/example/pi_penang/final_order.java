package com.example.pi_penang;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class final_order extends AppCompatActivity implements View.OnClickListener{

    final String timeRange = Pi_laundry.theTimeRange();
    final String trigle = Home_page.getTriggle();
    static int totalPrice;
    final String userCount = cooker_info.getStringUserCount();
    final String cookPhoneNumber = cooker_info.getPhoneNumber();
    final String categoryNeeded = Pi_laundry.theCategory();
    final String sidenotes = cooker_info.getSideNotes();
    final String nameDelivery = cooker_info.getNameDelivery();
    final String imageDelivery = cooker_info.getDeliveryImage();
    final String phoneNumberDelivery = cooker_info.getPhoneNumberDelivery();
    String phoneNumberUser;
    final String cookName = cooker_info.getCookerName();
    final String cookAddress = cooker_info.getAddress();
    final String cookOrder1 = cooker_info.getMenuOne();
    final String cookOrder2 = cooker_info.getMenuTwo();
    final String cookOrder3 = cooker_info.getMenuThree();
    final String cookQuantity1 = cooker_info.getQuantity1();
    final String cookQuantity2 = cooker_info.getQuantity2();
    final String cookQuantity3 = cooker_info.getQuantity3();
    String historyImageDelivery;
    String historyNameDelivery;
    String historyPhoneNumberDelivery;
    String deliveryNeed;
    String historyCookName;
    String historyTimeRange;
    String historyCookPhoneNumber;
    String historyCookAddress;
    String historyCookImage;
    String historyOrder;
    String historyPrice;

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
    TextView totalPriceTextView;
    final String picNeeded = cooker_info.getPic();
    int i = 0;
    ImageView deliveryImage;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final_order);

        totalPrice = 0;

        findViewById(R.id.confirmButton).setOnClickListener(this);
        findViewById(R.id.cancelButton).setOnClickListener(this);

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
        deliveryImage = findViewById(R.id.deliveryImage);
        totalPriceTextView = findViewById(R.id.totalPrice);
        findViewById(R.id.backButton).setOnClickListener(this);

        if(trigle.equals("Done")) {
            Button confirmButton = findViewById(R.id.confirmButton);
            Button cancelButton = findViewById(R.id.cancelButton);
            confirmButton.setVisibility(View.GONE);
            cancelButton.setVisibility(View.GONE);
            Button backButton = findViewById(R.id.backButton);
            backButton.setVisibility(View.VISIBLE);

            mAuth = FirebaseAuth.getInstance();
            final String user_id = mAuth.getCurrentUser().getUid();
            final FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference myRef = database.getReference();
            myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if(dataSnapshot.child("Orders").child(user_id).exists()) {
                        historyCookImage = dataSnapshot.child("Orders").child(user_id).child("Cook Image").getValue().toString();
                        historyCookName = dataSnapshot.child("Orders").child(user_id).child("Cook Name").getValue().toString();
                        historyCookPhoneNumber = dataSnapshot.child("Orders").child(user_id).child("Cook Phone Number").getValue().toString();
                        historyCookAddress = dataSnapshot.child("Orders").child(user_id).child("Cook Address").getValue().toString();
                        historyOrder = dataSnapshot.child("Orders").child(user_id).child("Order").getValue().toString();
                        historyTimeRange = dataSnapshot.child("Orders").child(user_id).child("Time needed").getValue().toString();
                        historyImageDelivery = dataSnapshot.child("Orders").child(user_id).child("Delivery Image").getValue().toString();
                        historyNameDelivery = dataSnapshot.child("Orders").child(user_id).child("Delivery Name").getValue().toString();
                        historyPhoneNumberDelivery = dataSnapshot.child("Orders").child(user_id).child("Delivery Phone").getValue().toString();
                        historyPrice = dataSnapshot.child("Orders").child(user_id).child("Total Price").getValue().toString();
                        deliveryNeed = dataSnapshot.child("Orders").child(user_id).child("Delivery Demand").getValue().toString();

                        if (deliveryNeed.equals("true")) {
                            deliveryImage.setVisibility(View.VISIBLE);
                            deliveryInfo.setVisibility(View.VISIBLE);
                            name.setVisibility(View.VISIBLE);
                            phoneNumber.setVisibility(View.VISIBLE);
                            nameFinal2.setVisibility(View.VISIBLE);
                            phoneNumberFinal2.setVisibility(View.VISIBLE);
                        }

                        Picasso.with(getApplicationContext()).load(historyImageDelivery).into(deliveryImage);
                        nameFinal2.setText(historyNameDelivery);
                        phoneNumberFinal2.setText(historyPhoneNumberDelivery);

                        nameFinal.setText(historyCookName);
                        timeRangeFinal.setText(historyTimeRange);
                        phoneNumberFinal.setText(historyCookPhoneNumber);
                        addressFinal.setText(historyCookAddress);
                        orderFinal.setText(historyOrder);
                        Picasso.with(getApplicationContext()).load(historyCookImage).into(cookImage);
                        totalPriceTextView.setText("RM" + historyPrice);
                    }
                    else{
                        Toast.makeText(getApplicationContext(),"You do not have any order yet!",Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });


        }

        else if (trigle.equals("None")) {
            Button backButton = findViewById(R.id.backButton);
            backButton.setVisibility(View.GONE);

            Picasso.with(getApplicationContext()).load(imageDelivery).into(deliveryImage);
            nameFinal2.setText(nameDelivery);
            phoneNumberFinal2.setText(phoneNumberDelivery);

            historyCookName = cookName;
            historyTimeRange = timeRange;
            historyCookPhoneNumber = cookPhoneNumber;
            historyCookAddress = cookAddress;
            historyCookImage = picNeeded;
            historyImageDelivery = imageDelivery;
            historyNameDelivery = nameDelivery;
            historyPhoneNumberDelivery = phoneNumberDelivery;

            nameFinal.setText(cookName);
            timeRangeFinal.setText(timeRange);
            phoneNumberFinal.setText(cookPhoneNumber);
            addressFinal.setText(cookAddress);
            Picasso.with(getApplicationContext()).load(picNeeded).into(cookImage);

            if (!cookQuantity1.equals("0") && cookQuantity2.equals("0") && cookQuantity3.equals("0")) {
                historyOrder = cookOrder1 + " x " + cookQuantity1;
                orderFinal.setText(historyOrder);
                int total1 = Integer.parseInt(cookQuantity1);
                totalPrice += total1;
                totalPrice = totalPrice * 10;
                String convertPrice = String.valueOf(totalPrice);
                historyPrice = convertPrice;
                totalPriceTextView.setText("RM" + convertPrice);
            } else if (!cookQuantity2.equals("0") && cookQuantity1.equals("0") && cookQuantity3.equals("0")) {
                historyOrder = cookOrder2 + " x " + cookQuantity2;
                orderFinal.setText(historyOrder);
                int total2 = Integer.parseInt(cookQuantity2);
                totalPrice += total2;
                totalPrice = totalPrice * 10;
                String convertPrice = String.valueOf(totalPrice);
                historyPrice = convertPrice;
                totalPriceTextView.setText("RM" + convertPrice);
            } else if (!cookQuantity3.equals("0") && cookQuantity2.equals("0") && cookQuantity1.equals("0")) {
                historyOrder = cookOrder3 + " x " + cookQuantity3;
                orderFinal.setText(historyOrder);
                int total3 = Integer.parseInt(cookQuantity3);
                totalPrice += total3;
                totalPrice = totalPrice * 10;
                String convertPrice = String.valueOf(totalPrice);
                historyPrice = convertPrice;
                totalPriceTextView.setText("RM" + convertPrice);
            } else if (!cookQuantity3.equals("0") && !cookQuantity2.equals("0") && cookQuantity1.equals("0")) {
                historyOrder = cookOrder3 + " x " + cookQuantity3 + " , " + cookOrder2 + " x " + cookQuantity2;
                orderFinal.setText(historyOrder);
                int total3 = Integer.parseInt(cookQuantity3);
                totalPrice += total3;
                int total2 = Integer.parseInt(cookQuantity2);
                totalPrice += total2;
                totalPrice = totalPrice * 10;
                String convertPrice = String.valueOf(totalPrice);
                historyPrice = convertPrice;
                totalPriceTextView.setText("RM" + convertPrice);
            } else if (!cookQuantity3.equals("0") && !cookQuantity1.equals("0") && cookQuantity2.equals("0")) {
                historyOrder = cookOrder3 + " x " + cookQuantity3 + " , " + cookOrder1 + " x " + cookQuantity1;
                orderFinal.setText(historyOrder);
                int total3 = Integer.parseInt(cookQuantity3);
                totalPrice += total3;
                int total1 = Integer.parseInt(cookQuantity1);
                totalPrice += total1;
                totalPrice = totalPrice * 10;
                String convertPrice = String.valueOf(totalPrice);
                historyPrice = convertPrice;
                totalPriceTextView.setText("RM" + convertPrice);

            } else if (!cookQuantity2.equals("0") && !cookQuantity1.equals("0") && cookQuantity3.equals("0")) {
                historyOrder = cookOrder2 + " x " + cookQuantity2 + " , " + cookOrder1 + " x " + cookQuantity1;
                orderFinal.setText(historyOrder);
                int total2 = Integer.parseInt(cookQuantity2);
                totalPrice += total2;
                int total1 = Integer.parseInt(cookQuantity1);
                totalPrice += +total1;
                totalPrice = totalPrice * 10;
                String convertPrice = String.valueOf(totalPrice);
                historyPrice = convertPrice;
                totalPriceTextView.setText("RM" + convertPrice);
            } else if (!cookQuantity2.equals("0") && !cookQuantity1.equals("0") && !cookQuantity3.equals("0")) {
                historyOrder = cookOrder2 + " x " + cookQuantity2 + " , " + cookOrder1 + " x " + cookQuantity1 + " , " + cookOrder3 + " x " + cookQuantity3;
                orderFinal.setText(historyOrder);
                int total1 = Integer.parseInt(cookQuantity1);
                totalPrice += total1;
                int total2 = Integer.parseInt(cookQuantity2);
                totalPrice += total2;
                int total3 = Integer.parseInt(cookQuantity3);
                totalPrice += total3;
                totalPrice = totalPrice * 10;
                String convertPrice = String.valueOf(totalPrice);
                historyPrice = convertPrice;
                totalPriceTextView.setText("RM" + convertPrice);

            }

            if(deliveryDemand == true)
            {
                deliveryImage.setVisibility(View.VISIBLE);
                deliveryInfo.setVisibility(View.VISIBLE);
                name.setVisibility(View.VISIBLE);
                phoneNumber.setVisibility(View.VISIBLE);
                nameFinal2.setVisibility(View.VISIBLE);
                phoneNumberFinal2.setVisibility(View.VISIBLE);
            }

        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.cancelButton:
                finish();
                break;
            case R.id.confirmButton:
                confirmOrder();
                break;
            case R.id.backButton:
                Intent i = new Intent(final_order.this, Home_page.class);
                startActivity(i);
                finish();
                break;
        }
    }

    public void confirmOrder()
    {
        mAuth = FirebaseAuth.getInstance();
        final String user_id = mAuth.getCurrentUser().getUid();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference();

        final DatabaseReference current_user_db = FirebaseDatabase.getInstance().getReference().child("Orders").child(user_id);

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                phoneNumberUser = dataSnapshot.child("Users").child(user_id).child("phone number").getValue().toString();
                deliveryNeed = String.valueOf(deliveryDemand);
                Map newPost = new HashMap();
                newPost.put("Food Category",categoryNeeded);
                newPost.put("Cook User ID","User"+userCount);
                newPost.put("Cook Image",historyCookImage);
                newPost.put("Cook Name",historyCookName);
                newPost.put("Cook Phone Number",historyCookPhoneNumber);
                newPost.put("Cook Address",historyCookAddress);
                newPost.put("Delivery Image",historyImageDelivery);
                newPost.put("Delivery Phone",historyPhoneNumberDelivery);
                newPost.put("Delivery Name",historyNameDelivery);
                newPost.put("Time needed",timeRange);
                newPost.put("Order",orderFinal.getText().toString());
                newPost.put("Phone number user",phoneNumberUser);
                newPost.put("Total Price",historyPrice);
                newPost.put("Delivery Demand",deliveryNeed);
                if(sidenotes != "None"){
                    newPost.put("notes",sidenotes);
                    current_user_db.setValue(newPost);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        Toast.makeText(getApplicationContext(),"You have successfully placed your order!",Toast.LENGTH_LONG).show();
        Intent i = new Intent(final_order.this, Home_page.class);
        startActivity(i);
        }

    }

