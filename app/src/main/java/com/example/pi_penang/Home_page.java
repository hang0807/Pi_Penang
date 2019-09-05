package com.example.pi_penang;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Home_page extends AppCompatActivity implements View.OnClickListener {

    static String triggle = "Done";
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        findViewById(R.id.viewOrder).setOnClickListener(this);
        findViewById(R.id.orderAgain).setOnClickListener(this);
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure you want to sign out?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        FirebaseAuth.getInstance().signOut();
                        Home_page.this.finish();
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
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.viewOrder:
                mAuth = FirebaseAuth.getInstance();
                final String user_id = mAuth.getCurrentUser().getUid();
                final FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef = database.getReference();

                myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if(dataSnapshot.child("Orders").child(user_id).exists()) {
                            Intent i = new Intent(Home_page.this, final_order.class);
                            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(i);
                            triggle = "Done";
                        }
                        else{
                            Toast.makeText(getApplicationContext(),"You do not have any order at this moment!",Toast.LENGTH_LONG).show();
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                    }
                });
                break;
            case R.id.orderAgain:
                mAuth = FirebaseAuth.getInstance();
                final String user_id2 = mAuth.getCurrentUser().getUid();
                final FirebaseDatabase database2 = FirebaseDatabase.getInstance();
                DatabaseReference myRef2 = database2.getReference();

                myRef2.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if(!dataSnapshot.child("Orders").child(user_id2).exists()) {
                            Intent newIntent = new Intent(Home_page.this, Users_info.class);
                            startActivity(newIntent);
                            triggle = "None";
                        }
                        else{
                            Toast.makeText(getApplicationContext(),"Your food is on its way!",Toast.LENGTH_LONG).show();
                        }
                    }
                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                        }
                    });

                break;

        }
    }

    public static String getTriggle()
    {
        return triggle;
    }
}


