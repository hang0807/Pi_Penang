package com.example.pi_penang;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Pi_laundry extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener{

    static String  selectedItem1;
    static String selectedItem2;
    static String selectedItem3;
    static boolean deliveryDemand;
    CheckBox noCheckBox;
    CheckBox yesCheckBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pi_laundry);

        Spinner timeSpinner = (Spinner) findViewById(R.id.timeSpinner);
        Spinner amountSpinner = (Spinner)findViewById(R.id.amountSpinner);
        Spinner cateSpinner = (Spinner)findViewById(R.id.catoSpinner);
        Button ConfirmButton = (Button)findViewById(R.id.confirmButton);
        CheckBox yesCheckBox = (CheckBox)findViewById(R.id.yesCheckbox);
        CheckBox noCheckBox = (CheckBox)findViewById(R.id.noCheckbox);

        ConfirmButton.setOnClickListener(this);
        timeSpinner.setOnItemSelectedListener(this);
        amountSpinner.setOnItemSelectedListener(this);
        cateSpinner.setOnItemSelectedListener(this);
        yesCheckBox.setOnClickListener(this);
        noCheckBox.setOnClickListener(this);


        List<String> timeRange = new ArrayList<String>();
        timeRange.add("Time range");
        timeRange.add("8.00 - 10.00");
        timeRange.add("10.00 - 12.00");
        timeRange.add("12.00 - 14.00");
        timeRange.add("14.00 - 16.00");
        timeRange.add("16.00 - 18.00");
        timeRange.add("16.00 - 18.00");
        timeRange.add("18.00 - 20.00");

        List<String> foodAmount = new ArrayList<String>();
        foodAmount.add("Amount of unit");
        foodAmount.add("1 - 5");
        foodAmount.add("5 - 10");
        foodAmount.add("15 - 20");
        foodAmount.add("25 - 30");

        List<String> category = new ArrayList<String>();
        category.add("Category of food");
        category.add("Halal");
        category.add("Non-Halal");
        category.add("Vegetarian");
        category.add("Diet");

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, timeRange);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        timeSpinner.setAdapter(dataAdapter);

        ArrayAdapter<String> dataAdapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,foodAmount);
        dataAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        amountSpinner.setAdapter(dataAdapter2);

        ArrayAdapter<String> dataAdapter3 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,category);
        dataAdapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        cateSpinner.setAdapter(dataAdapter3);

        }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Spinner timeSpinner = (Spinner)findViewById(R.id.timeSpinner);
        Spinner cateSpinner = (Spinner)findViewById(R.id.catoSpinner);
        Spinner amountSpinner = (Spinner)findViewById(R.id.amountSpinner);

        selectedItem1 = timeSpinner.getSelectedItem().toString();
        selectedItem1 = selectedItem1.equals("All Types")?selectedItem1.replaceAll(" ", "").toLowerCase():selectedItem1;

        selectedItem2 = cateSpinner.getSelectedItem().toString();
        selectedItem2 = selectedItem2.equals("All Types")?selectedItem2.replaceAll(" ", "").toLowerCase():selectedItem2;

        selectedItem3 = amountSpinner.getSelectedItem().toString();
        selectedItem3 = selectedItem3.equals("All Types")?selectedItem3.replaceAll(" ", "").toLowerCase():selectedItem3;

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }

    public void uploadToCooker()
    {
        Spinner timeSpinner = (Spinner) findViewById(R.id.timeSpinner);
        Spinner amountSpinner = (Spinner)findViewById(R.id.amountSpinner);
        Spinner cateSpinner = (Spinner)findViewById(R.id.catoSpinner);
        CheckBox yesCheckBox = (CheckBox)findViewById(R.id.yesCheckbox);
        CheckBox noCheckBox = (CheckBox)findViewById(R.id.noCheckbox);

        String utime = timeSpinner.getSelectedItem().toString();
        String uamount = amountSpinner.getSelectedItem().toString();
        String ucategory = cateSpinner.getSelectedItem().toString();

        if(!utime.equals("Time range") || !uamount.equals("Amount of unit") || !ucategory.equals("Category of food") ){
                if(yesCheckBox.isChecked() || noCheckBox.isChecked()) {
                    if(yesCheckBox.isChecked()){
                        deliveryDemand = true;
                        finish();
                        Intent i = new Intent(Pi_laundry.this, cooker_info.class);
                        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(i);
                    }
                    else{
                        deliveryDemand = false;
                        Intent i = new Intent(Pi_laundry.this, cooker_info.class);
                        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(i);
                    }
                }
                else{
                    Toast.makeText(getApplicationContext(), "Do you need delivery service?", Toast.LENGTH_LONG).show();
                }
        }
        else{
            Toast.makeText(getApplicationContext(), "Please make all your choices!", Toast.LENGTH_LONG).show();
        }

    }


    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.confirmButton:
                uploadToCooker();
                break;

            case R.id.yesCheckbox:
                CheckBox noCheckBox = (CheckBox)findViewById(R.id.noCheckbox);
                noCheckBox.setChecked(false);
                break;

            case R.id.noCheckbox:
                CheckBox yesCheckBox = (CheckBox)findViewById(R.id.yesCheckbox);
                yesCheckBox.setChecked(false);
                break;
        }
    }

    public static boolean isDeliveryDemand()
    {

        return deliveryDemand;
    }

    public static String theTimeRange()
    {

        return selectedItem1;
    }

    public static String theCategory()
    {
        return selectedItem2;
    }

    public static String theAmountRange()
    {
        return selectedItem3;
    }


}






