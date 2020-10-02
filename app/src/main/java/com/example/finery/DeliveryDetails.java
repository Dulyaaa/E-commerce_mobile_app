package com.example.finery;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DeliveryDetails extends AppCompatActivity {

    EditText editTextFname,editTextLname,editTextCity,editTextAddress,editTextConNo;
    Button buttonAdd,buttonShow;
    DatabaseReference dbRef;
    Delivery std;


    private void clearControls(){
        editTextFname.setText("");
        editTextLname.setText("");
        editTextCity.setText("");
        editTextAddress.setText("");
        editTextConNo.setText("");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery_details);


        editTextFname = findViewById(R.id.editTextFname);
        editTextLname = findViewById(R.id.editTextLname);
        editTextCity = findViewById(R.id.editTextCity);
        editTextAddress = findViewById(R.id.editTextAddress);
        editTextConNo = findViewById(R.id.editTextConNo);

        buttonAdd = findViewById(R.id.buttonAdd);
        buttonShow = findViewById(R.id.buttonShow);
        std = new Delivery();
        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbRef = FirebaseDatabase.getInstance().getReference().child("Delivery");

                try {
                    if (TextUtils.isEmpty(editTextFname.getText().toString()))
                        Toast.makeText(getApplicationContext(),"Please enter a name",Toast.LENGTH_SHORT).show();
                    else if (TextUtils.isEmpty(editTextLname.getText().toString()))
                        Toast.makeText(getApplicationContext(),"Please enter a name",Toast.LENGTH_SHORT).show();
                    else if (TextUtils.isEmpty(editTextCity.getText().toString()))
                        Toast.makeText(getApplicationContext(),"Please enter a city",Toast.LENGTH_SHORT).show();
                    else if (TextUtils.isEmpty(editTextAddress.getText().toString()))
                        Toast.makeText(getApplicationContext(),"Please enter a address",Toast.LENGTH_SHORT).show();
                    else {
                        std.setFname(editTextFname.getText().toString().trim());
                        std.setLname(editTextLname.getText().toString().trim());
                        std.setCity(editTextCity.getText().toString().trim());
                        std.setAddress(editTextAddress.getText().toString().trim());
                        std.setConNo( Integer.parseInt(editTextConNo.getText().toString().trim()));

                        dbRef.child("del2").setValue(std);

                        Toast.makeText(getApplicationContext(),"Successfully Add To The Delivery",Toast.LENGTH_LONG).show();
                        clearControls();
                    }
                }
                catch (NumberFormatException e){
                    Toast.makeText(getApplicationContext(),"Invalid Contact Number",Toast.LENGTH_SHORT).show();
                }
            }

        });

        buttonShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference readref = FirebaseDatabase.getInstance().getReference().child("Delivery").child("del2");
                readref.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.hasChildren()){
                            editTextFname.setText(dataSnapshot.child("fname").getValue().toString());
                            editTextLname.setText(dataSnapshot.child("lname").getValue().toString());
                            editTextCity.setText(dataSnapshot.child("city").getValue().toString());
                            editTextAddress.setText(dataSnapshot.child("address").getValue().toString());
                            editTextConNo.setText(dataSnapshot.child("conNo").getValue().toString());
                        }
                        else
                            Toast.makeText(getApplicationContext(),"Nothing To Display In Delivery",Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });

    }
}