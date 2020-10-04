package com.example.finery;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.finery.Model.Rate;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class GiveRates extends AppCompatActivity {

    EditText editTextTitle,editTextCom;
    Button buttonSave,buttonShow,buttonUpdate,buttonDelete;
    DatabaseReference dbRef;
    Rate std;

    private void clearControls(){
        editTextTitle.setText("");
        editTextCom.setText("");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_give_rates);

        editTextTitle = findViewById(R.id.giveTitle1);
        editTextCom = findViewById(R.id.comment);
        buttonSave = findViewById(R.id.send_btn);
        buttonShow = findViewById(R.id.show_btn);
        buttonUpdate = findViewById(R.id.update_btn);

        std = new Rate();
        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbRef = FirebaseDatabase.getInstance().getReference().child("Rates");

                try {
                    if (TextUtils.isEmpty(editTextTitle.getText().toString()))
                        Toast.makeText(getApplicationContext(),"Please enter a Title",Toast.LENGTH_SHORT).show();
                    else if (TextUtils.isEmpty(editTextCom.getText().toString()))
                        Toast.makeText(getApplicationContext(),"Please enter a Comment",Toast.LENGTH_SHORT).show();
                    else {
                        std.setProductTitle(editTextTitle.getText().toString().trim());
                        std.setComment(editTextCom.getText().toString().trim());

                        dbRef.push().setValue(std);

                        Toast.makeText(getApplicationContext(),"Data Saved Successfully ",Toast.LENGTH_LONG).show();
                        clearControls();
                    }
                }
                catch (NumberFormatException e){
                    Toast.makeText(getApplicationContext(),"Invalid Product ID",Toast.LENGTH_SHORT).show();
                }
            }

        });

        buttonShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DatabaseReference readRef = FirebaseDatabase.getInstance().getReference().child("Rate").child("std1");
                readRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.hasChildren()){
                            editTextTitle.setText(dataSnapshot.child("Title").getValue().toString());
                            editTextCom.setText(dataSnapshot.child("Comment").getValue().toString());

                        }
                        else
                            Toast.makeText(getApplicationContext(),"No Source To Display",Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            }
        });

        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

    }
}