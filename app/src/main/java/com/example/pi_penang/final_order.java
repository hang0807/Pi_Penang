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

    String timeRange = Pi_laundry.theTimeRange();
    String userCount = cooker_info.getStringUserCount();
    String cookPhoneNumber = cooker_info.getPhoneNumber();
    String categoryNeeded = cooker_info.getCategoryNeeded();
    String sidenotes = cooker_info.getSideNotes();
    String phoneNumberUser;
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
    ImageView deliveryImage;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final_order);

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

        nameFinal.setText(cookName);
        timeRangeFinal.setText(timeRange);
        phoneNumberFinal.setText(cookPhoneNumber);
        addressFinal.setText(cookAddress);
        Picasso.with(getApplicationContext()).load(picNeeded).into(cookImage);

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
            deliveryImage.setVisibility(View.VISIBLE);
            deliveryInfo.setVisibility(View.VISIBLE);
            name.setVisibility(View.VISIBLE);
            phoneNumber.setVisibility(View.VISIBLE);
            nameFinal2.setVisibility(View.VISIBLE);
            phoneNumberFinal2.setVisibility(View.VISIBLE);

            final FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference myRef = database.getReference();

            myRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    Random newRandom = new Random();
                    long count= dataSnapshot.child("Delivery Info").getChildrenCount();
                    int newCount=(int)count;
                    int userNumber = newRandom.nextInt(newCount);
                    userNumber += 1;
                    String UserCount = String.valueOf(userNumber);
                    String image = dataSnapshot.child("Delivery Info").child("User"+UserCount).child("Photo").getValue().toString();
                    String nameDelivery = dataSnapshot.child("Delivery Info").child("User"+UserCount).child("Name").getValue().toString();
                    String phoneNumberDelivery = dataSnapshot.child("Delivery Info").child("User"+UserCount).child("Phone Number").getValue().toString();
                    Picasso.with(getApplicationContext()).load(image).into(deliveryImage);
                    nameFinal2.setText(nameDelivery);
                    phoneNumberFinal2.setText(phoneNumberDelivery);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });



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
        }
    }

    public void confirmOrder()
    {
        mAuth = FirebaseAuth.getInstance();
        final String user_id = mAuth.getCurrentUser().getUid();
        final DatabaseReference current_user_db = FirebaseDatabase.getInstance().getReference().child("Cookers Info").child(categoryNeeded).child("User"+userCount).child("Order").child(user_id);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference();

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String user_id = mAuth.getCurrentUser().getUid();
                phoneNumberUser = dataSnapshot.child("Users").child(user_id).child("phone number").getValue().toString();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        Map newPost = new HashMap();
        newPost.put("Time needed",timeRange);
        newPost.put("Order",orderFinal.getText().toString());
        newPost.put("Phone Number of the user",phoneNumberUser);
        if(sidenotes != "None"){
            newPost.put("notes",sidenotes);
            current_user_db.setValue(newPost);
        }
        Intent i = new Intent(final_order.this, Home_page.class);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
        }


    }

