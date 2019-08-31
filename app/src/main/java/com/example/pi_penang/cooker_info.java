package com.example.pi_penang;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.Random;


public class cooker_info extends AppCompatActivity implements View.OnClickListener {

    boolean deliveryDemand = Pi_laundry.isDeliveryDemand();
    String categoryNeeded = Pi_laundry.theCategory();
    String timeRange = Pi_laundry.theTimeRange();
    String amountRange = Pi_laundry.theAmountRange();
    TextView attemptTextView;
    String finalNumber ;
    Random newRandom = new Random();
    TextView quantityOne;
    TextView quantityTwo;
    TextView quantityThree;
    int recorder;
    int secondRecorder;
    int counter = 2;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cooker_info);

        findViewById(R.id.addButton1).setOnClickListener(this);
        findViewById(R.id.addButton2).setOnClickListener(this);
        findViewById(R.id.addButton2).setOnClickListener(this);
        findViewById(R.id.minusButton1).setOnClickListener(this);
        findViewById(R.id.minusButton2).setOnClickListener(this);
        findViewById(R.id.minusButton3).setOnClickListener(this);
        findViewById(R.id.surpriseButton).setOnClickListener(this);
        findViewById(R.id.orderButton).setOnClickListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        final ImageView img = (ImageView)findViewById(R.id.cookerImageView);
        final TextView cookerNameEditText = (TextView)findViewById((R.id.cookerNameEditText));
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                long count= dataSnapshot.child("Cookers Info").child(categoryNeeded).getChildrenCount();
                int newCount=(int)count;
                int userNumber = newRandom.nextInt(newCount);
                userNumber += 1;
                String stringUserCount = String.valueOf(userNumber);
                recorder = userNumber;
                //cookerNameEditText.setText(stringCount);
                String pic = dataSnapshot.child("Cookers Info").child(categoryNeeded).child("User"+stringUserCount).child("Photo").getValue().toString();
                String cookerName = dataSnapshot.child("Cookers Info").child(categoryNeeded).child("User"+stringUserCount).child("Name").getValue().toString();
                cookerNameEditText.setText(cookerName);
                Picasso.with(getApplicationContext()).load(pic).into(img);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.addButton1:
                quantityOne = findViewById(R.id.quantityOne);
                addNumber(quantityOne);
                quantityOne.setText(finalNumber);
                    break;
            case R.id.addButton2:
                quantityTwo = findViewById(R.id.quantityTwo);
                addNumber(quantityTwo);
                quantityTwo.setText(finalNumber);
                    break;
            case R.id.addButton3:
                quantityThree= findViewById(R.id.quantityThree);
                addNumber(quantityThree);
                quantityThree.setText(finalNumber);
                    break;
            case R.id.minusButton1:
                quantityOne = findViewById(R.id.quantityOne);
                minusNumber(quantityOne);
                quantityOne.setText(finalNumber);
                    break;
            case R.id.minusButton2:
                quantityTwo = findViewById(R.id.quantityTwo);
                minusNumber(quantityTwo);
                quantityTwo.setText(finalNumber);
                    break;
            case R.id.minusButton3:
                quantityThree = findViewById(R.id.quantityThree);
                minusNumber(quantityThree);
                quantityThree.setText(finalNumber);
                    break;
            case R.id.surpriseButton:
                surpriseYou();
                break;
            case R.id.orderButton:
                Intent i = new Intent(cooker_info.this, final_order.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
                break;
        }

    }

    public void addNumber(TextView x)
    {
        String newNumber = x.getText().toString();
        int number = Integer.parseInt(newNumber);
        number = number + 1;
        finalNumber = String.valueOf(number);
    }

    public void minusNumber(TextView x)
    {
        String newNumber = x.getText().toString();
        if(newNumber.equals("0")){
            finalNumber = "0";
        }
        else{
            int number = Integer.parseInt(newNumber);
            number = number - 1;
            finalNumber = String.valueOf(number);
        }
    }

    public void surpriseYou()
    {
        quantityOne = findViewById(R.id.quantityOne);
        quantityTwo = findViewById(R.id.quantityTwo);
        quantityThree = findViewById(R.id.quantityThree);

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                long count= dataSnapshot.child("Cookers Info").child(categoryNeeded).getChildrenCount();
                final ImageView img = (ImageView)findViewById(R.id.cookerImageView);
                final TextView cookerNameEditText = (TextView)findViewById((R.id.cookerNameEditText));
                int newCount=(int)count;
                int userNumber = newRandom.nextInt(newCount);
                userNumber += 1;
                if(recorder == userNumber)
                {
                   userNumber = newRandom.nextInt(newCount);

                }
                else if(userNumber != recorder && counter == 2) {

                    secondRecorder = userNumber;
                    String stringUserCount = String.valueOf(userNumber);
                    attemptTextView = findViewById(R.id.attemptTextView);
                    attemptTextView.setText(stringUserCount);
                    String pic = dataSnapshot.child("Cookers Info").child(categoryNeeded).child("User" + stringUserCount).child("Photo").getValue().toString();
                    String cookerName = dataSnapshot.child("Cookers Info").child(categoryNeeded).child("User" + stringUserCount).child("Name").getValue().toString();
                    cookerNameEditText.setText(cookerName);
                    Picasso.with(getApplicationContext()).load(pic).into(img);
                    counter -= 1;
                    String realCounter = String.valueOf(counter);
                    attemptTextView.setText(realCounter);
                    quantityOne.setText("0");
                    quantityTwo.setText("0");
                    quantityThree.setText("0");
                }
                else if(userNumber != recorder && counter == 1 && userNumber!= secondRecorder){
                    secondRecorder = userNumber;
                    String stringUserCount = String.valueOf(userNumber);
                    attemptTextView = findViewById(R.id.attemptTextView);
                    attemptTextView.setText(stringUserCount);
                    String pic = dataSnapshot.child("Cookers Info").child(categoryNeeded).child("User" + stringUserCount).child("Photo").getValue().toString();
                    String cookerName = dataSnapshot.child("Cookers Info").child(categoryNeeded).child("User" + stringUserCount).child("Name").getValue().toString();
                    cookerNameEditText.setText(cookerName);
                    Picasso.with(getApplicationContext()).load(pic).into(img);
                    counter -= 1;
                    String realCounter = String.valueOf(counter);
                    attemptTextView.setText(realCounter);
                    quantityOne.setText("0");
                    quantityTwo.setText("0");
                    quantityThree.setText("0");
                }
                else if(counter == 0)
                {
                    Toast.makeText(getApplicationContext(),"Not more surprise today!",Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}
