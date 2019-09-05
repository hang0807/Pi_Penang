package com.example.pi_penang;

import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.solver.widgets.Snapshot;
import android.support.v7.app.AppCompatActivity;
import android.util.AndroidException;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.jar.Attributes;

public class Users_info extends AppCompatActivity implements View.OnClickListener {

    EditText editTextName, editTextPhNumber,editTextAddress;
    private FirebaseAuth mAuth;
    private static final String TAG = "Users_info";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users_info);

        editTextName = findViewById(R.id.editTextName);
        editTextPhNumber = findViewById(R.id.editTextPhNumber);
        editTextAddress= findViewById(R.id.editTextAddress);

        findViewById(R.id.saveInfoButton).setOnClickListener(this);

        mAuth = FirebaseAuth.getInstance();

        final String user_id = mAuth.getCurrentUser().getUid();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference();

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.child("Users").child(user_id).exists()) {
                    String name = dataSnapshot.child("Users").child(user_id).child("name").getValue().toString();
                    String phone = dataSnapshot.child("Users").child(user_id).child("phone number").getValue().toString();
                    String address = dataSnapshot.child("Users").child(user_id).child("address").getValue().toString();
                    editTextName.setText(name);
                    editTextPhNumber.setText(phone);
                    editTextAddress.setText(address);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.saveInfoButton:
            SaveInfo();
                break;
        }
    }

    private void SaveInfo() {
        String Name = editTextName.getText().toString();
        String PhNumber = editTextPhNumber.getText().toString();
        String Address = editTextAddress.getText().toString();


        String user_id = mAuth.getCurrentUser().getUid();
        DatabaseReference current_user_db = FirebaseDatabase.getInstance().getReference().child("Users").child(user_id);

        if(Name.isEmpty()) {
            editTextName.setError("Name is required");
            editTextName.requestFocus();
            return;
        }

        if(PhNumber.isEmpty()) {
            editTextPhNumber.setError("Phone Number is required");
            editTextPhNumber.requestFocus();
            return;
        }

        if(Address.isEmpty()) {
            editTextAddress.setError("Address is required");
            editTextAddress.requestFocus();
            return;
        }

        else{
            Map newPost = new HashMap();
            newPost.put("name",Name);
            newPost.put("phone number",PhNumber);
            newPost.put("address",Address);

            current_user_db.setValue(newPost);

        }

        startActivity(new Intent(Users_info.this,Pi_laundry.class));
        finish();


    }


}
