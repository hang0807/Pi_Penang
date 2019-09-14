package com.example.pi_penang;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
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

import java.util.Random;


public class cooker_info extends AppCompatActivity implements View.OnClickListener {

    static String categoryNeeded = Pi_laundry.theCategory();
    static String stringUserCount;
    static String sideNoteFinal;
    static String deliveryImage;
    static String nameDelivery;
    static String phoneNumberDelivery;
    TextView attemptTextView;
    String finalNumber ;
    Random newRandom = new Random();
    TextView quantityOne;
    TextView quantityTwo;
    TextView quantityThree;
    TextView menu1;
    TextView menu2;
    TextView menu3;
    EditText sideNotes;
    int recorder;
    int secondRecorder;
    int counter = 2;
    static String pic;
    static String cookerName;
    static String phoneNumber;
    static String cookAddress;
    static String quantity1;
    static String quantity2;
    static String quantity3;
    static String menuOne;
    static String menuTwo;
    static String menuThree;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference();

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure you want to exit to Home Page?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        FirebaseAuth.getInstance().signOut();
                        cooker_info.this.finish();
                        Intent i = new Intent(cooker_info.this, Home_page.class);
                        startActivity(i);
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cooker_info);

        findViewById(R.id.addButton1).setOnClickListener(this);
        findViewById(R.id.addButton2).setOnClickListener(this);
        findViewById(R.id.addButton3).setOnClickListener(this);
        findViewById(R.id.minusButton1).setOnClickListener(this);
        findViewById(R.id.minusButton2).setOnClickListener(this);
        findViewById(R.id.minusButton3).setOnClickListener(this);
        findViewById(R.id.surpriseButton).setOnClickListener(this);
        findViewById(R.id.orderButton).setOnClickListener(this);
        sideNotes = findViewById(R.id.sideNotes);
        sideNoteFinal = sideNotes.getText().toString();
        menu1 = findViewById(R.id.menu1);
        menu2 = findViewById(R.id.menu2);
        menu3 = findViewById(R.id.menu3);

        final ImageView img = (ImageView)findViewById(R.id.cookerImageView);
        final TextView cookerNameEditText = (TextView)findViewById((R.id.cookerNameEditText));
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                long count= dataSnapshot.child("Cookers Info").child(categoryNeeded).getChildrenCount();
                long childrenCount = dataSnapshot.child("Delivery Info").getChildrenCount();
                int newCount=(int)count;
                int userNumber = newRandom.nextInt(newCount);
                userNumber += 1;
                stringUserCount = String.valueOf(userNumber);
                recorder = userNumber;

                Random newRandom = new Random();
                int latestNewCount = (int) childrenCount;
                int userId = newRandom.nextInt(latestNewCount);
                userId += 1;
                String UserCount = String.valueOf(userId);


                pic = dataSnapshot.child("Cookers Info").child(categoryNeeded).child("User"+stringUserCount).child("Photo").getValue().toString();
                cookerName = dataSnapshot.child("Cookers Info").child(categoryNeeded).child("User"+stringUserCount).child("Name").getValue().toString();
                phoneNumber = dataSnapshot.child("Cookers Info").child(categoryNeeded).child("User"+stringUserCount).child("Phone Number").getValue().toString();
                cookAddress = dataSnapshot.child("Cookers Info").child(categoryNeeded).child("User"+stringUserCount).child("Address").getValue().toString();
                menuOne = dataSnapshot.child("Cookers Info").child(categoryNeeded).child("User"+stringUserCount).child("Menu").child("1").getValue().toString();
                menuTwo = dataSnapshot.child("Cookers Info").child(categoryNeeded).child("User"+stringUserCount).child("Menu").child("2").getValue().toString();
                menuThree = dataSnapshot.child("Cookers Info").child(categoryNeeded).child("User"+stringUserCount).child("Menu").child("3").getValue().toString();

                deliveryImage= dataSnapshot.child("Delivery Info").child("User" + UserCount).child("Photo").getValue().toString();
                nameDelivery = dataSnapshot.child("Delivery Info").child("User" + UserCount).child("Name").getValue().toString();
                phoneNumberDelivery = dataSnapshot.child("Delivery Info").child("User" + UserCount).child("Phone Number").getValue().toString();

                menu1.setText(menuOne);
                menu2.setText(menuTwo);
                menu3.setText(menuThree);
                cookerNameEditText.setText(cookerName);
                Picasso.with(getApplicationContext()).load(pic).into(img);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
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
                orderButton();
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

    public static String getPic()
    {
        return pic;
    }

    public static String getCookerName()
    {
        return cookerName;
    }

    public static String getPhoneNumber()
    {
        return phoneNumber;
    }

    public static String getAddress()
    {
        return cookAddress;
    }

    public static String getQuantity1()
    {
        return quantity1;
    }

    public static String getQuantity2()
    {
        return quantity2;
    }

    public static String getQuantity3()
    {
        return quantity3;
    }

    public static String getMenuOne()
    {
        return menuOne;
    }

    public static String getMenuTwo()
    {
        return menuTwo;
    }

    public static String getMenuThree()
    {
        return menuThree;
    }

    public static String getCategoryNeeded(){return categoryNeeded;}

    public static String getStringUserCount(){return stringUserCount;}

    public static String getSideNotes(){return sideNoteFinal;}

    public static String getDeliveryImage(){return deliveryImage;}

    public static String getNameDelivery(){return nameDelivery;}

    public static String getPhoneNumberDelivery(){return phoneNumberDelivery;}

    public void orderButton(){
        quantityOne = findViewById(R.id.quantityOne);
        quantityTwo = findViewById(R.id.quantityTwo);
        quantityThree = findViewById(R.id.quantityThree);
        String quantity1final = quantityOne.getText().toString();
        String quantity2final = quantityTwo.getText().toString();
        String quantity3final = quantityThree.getText().toString();

        if (quantity1final.equals("0") && quantity2final.equals("0") && quantity3final.equals("0")) {
            Toast.makeText(getApplicationContext(),"Please clarify the quantity of your food!",Toast.LENGTH_LONG).show();
        }else{
            quantity1 = quantityOne.getText().toString();
            quantity2 = quantityTwo.getText().toString();
            quantity3 = quantityThree.getText().toString();

            menuOne = menu1.getText().toString();
            menuTwo = menu2.getText().toString();
            menuThree = menu3.getText().toString();

            Intent i = new Intent(cooker_info.this, final_order.class);
            startActivity(i);
            finish();
        }
    }


    public void surpriseYou()
    {
        quantityOne = findViewById(R.id.quantityOne);
        quantityTwo = findViewById(R.id.quantityTwo);
        quantityThree = findViewById(R.id.quantityThree);

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
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
                    stringUserCount = String.valueOf(userNumber);
                    attemptTextView = findViewById(R.id.attemptTextView);
                    pic = dataSnapshot.child("Cookers Info").child(categoryNeeded).child("User" + stringUserCount).child("Photo").getValue().toString();
                    String cookerName = dataSnapshot.child("Cookers Info").child(categoryNeeded).child("User" + stringUserCount).child("Name").getValue().toString();
                    phoneNumber = dataSnapshot.child("Cookers Info").child(categoryNeeded).child("User"+stringUserCount).child("Phone Number").getValue().toString();
                    cookAddress = dataSnapshot.child("Cookers Info").child(categoryNeeded).child("User"+stringUserCount).child("Address").getValue().toString();
                    menuOne = dataSnapshot.child("Cookers Info").child(categoryNeeded).child("User"+stringUserCount).child("Menu").child("1").getValue().toString();
                    menuTwo = dataSnapshot.child("Cookers Info").child(categoryNeeded).child("User"+stringUserCount).child("Menu").child("2").getValue().toString();
                    menuThree = dataSnapshot.child("Cookers Info").child(categoryNeeded).child("User"+stringUserCount).child("Menu").child("3").getValue().toString();
                    menu1.setText(menuOne);
                    menu2.setText(menuTwo);
                    menu3.setText(menuThree);
                    cookerNameEditText.setText(cookerName);
                    Picasso.with(getApplicationContext()).load(pic).into(img);
                    counter -= 1;
                    String realCounter = String.valueOf(counter);
                    attemptTextView.setText(realCounter);
                    quantityOne.setText("0");
                    quantityTwo.setText("0");
                    quantityThree.setText("0");
                    sideNotes = findViewById(R.id.sideNotes);
                    sideNotes.setText("");
                    Toast.makeText(getApplicationContext(),"We found you another cook!",Toast.LENGTH_LONG).show();

                }
                else if(userNumber != recorder && counter == 1 && userNumber!= secondRecorder){
                    secondRecorder = userNumber;
                    stringUserCount = String.valueOf(userNumber);
                    attemptTextView = findViewById(R.id.attemptTextView);
                    pic = dataSnapshot.child("Cookers Info").child(categoryNeeded).child("User" + stringUserCount).child("Photo").getValue().toString();
                    cookerName = dataSnapshot.child("Cookers Info").child(categoryNeeded).child("User" + stringUserCount).child("Name").getValue().toString();
                    phoneNumber = dataSnapshot.child("Cookers Info").child(categoryNeeded).child("User"+stringUserCount).child("Phone Number").getValue().toString();
                    cookAddress = dataSnapshot.child("Cookers Info").child(categoryNeeded).child("User"+stringUserCount).child("Address").getValue().toString();
                    menuOne = dataSnapshot.child("Cookers Info").child(categoryNeeded).child("User"+stringUserCount).child("Menu").child("1").getValue().toString();
                    menuTwo = dataSnapshot.child("Cookers Info").child(categoryNeeded).child("User"+stringUserCount).child("Menu").child("2").getValue().toString();
                    menuThree = dataSnapshot.child("Cookers Info").child(categoryNeeded).child("User"+stringUserCount).child("Menu").child("3").getValue().toString();
                    menu1.setText(menuOne);
                    menu2.setText(menuTwo);
                    menu3.setText(menuThree);
                    cookerNameEditText.setText(cookerName);
                    Picasso.with(getApplicationContext()).load(pic).into(img);
                    counter -= 1;
                    String realCounter = String.valueOf(counter);
                    attemptTextView.setText(realCounter);
                    quantityOne.setText("0");
                    quantityTwo.setText("0");
                    quantityThree.setText("0");
                    sideNotes.findViewById(R.id.sideNotes);
                    sideNotes.setText("");
                    Toast.makeText(getApplicationContext(),"We found you another cook!",Toast.LENGTH_LONG).show();
                }
                else if(counter == 0)
                {
                    Toast.makeText(getApplicationContext(),"No more surprise today!",Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }


}
