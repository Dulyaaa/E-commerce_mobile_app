package com.example.finery;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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

public class Feedback extends AppCompatActivity {

    EditText editTextName,editTextPname,editTextDes,editTextContact;
    Button buttonSave,buttonUP,buttonDEL,buttonSH;
    DatabaseReference dbRef,readref;
    Feed fed;

    private void clearControls(){
        editTextName.setText("");
        editTextPname.setText("");
        editTextDes.setText("");
        editTextContact.setText("");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);

        editTextName = findViewById(R.id.editTextName);
        editTextPname = findViewById(R.id.editTextPname);
        editTextDes = findViewById(R.id.editTextDes);
        editTextContact = findViewById(R.id.editTextContact);

        buttonSave = findViewById(R.id.buttonSave);
        buttonSH = findViewById(R.id.buttonSH);
        buttonUP = findViewById(R.id.buttonUP);
        buttonDEL = findViewById(R.id.buttonDEL);

        fed = new Feed();

        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbRef = FirebaseDatabase.getInstance().getReference().child("Feed");

                try {
                    if (TextUtils.isEmpty(editTextName.getText().toString()))
                        Toast.makeText(getApplicationContext(),"Please enter a name",Toast.LENGTH_SHORT).show();
                    else if (TextUtils.isEmpty(editTextPname.getText().toString()))
                        Toast.makeText(getApplicationContext(),"Please enter a product name",Toast.LENGTH_SHORT).show();
                    else if (TextUtils.isEmpty(editTextDes.getText().toString()))
                        Toast.makeText(getApplicationContext(),"Please enter a description",Toast.LENGTH_SHORT).show();
                    else {
                        fed.setName(editTextName.getText().toString().trim());
                        fed.setProname(editTextPname.getText().toString().trim());
                        fed.setDescription(editTextDes.getText().toString().trim());
                        fed.setContact( Integer.parseInt(editTextContact.getText().toString().trim()));

                        dbRef.child("feed4").setValue(fed);

                        Toast.makeText(getApplicationContext(),"Successfully Added Your Feedback",Toast.LENGTH_LONG).show();
                        clearControls();
                    }
                }
                catch (NumberFormatException e){
                    Toast.makeText(getApplicationContext(),"Invalid Contact Number Please Enter valid one",Toast.LENGTH_SHORT).show();
                }
            }

        });

        buttonSH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                readref = FirebaseDatabase.getInstance().getReference().child("Feed").child("feed4");
                readref.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.hasChildren()){
                            editTextName.setText(dataSnapshot.child("name").getValue().toString());
                            editTextPname.setText(dataSnapshot.child("proname").getValue().toString());
                            editTextDes.setText(dataSnapshot.child("description").getValue().toString());
                            editTextContact.setText(dataSnapshot.child("contact").getValue().toString());
                        }
                        else
                            Toast.makeText(getApplicationContext(),"Nothing To Display Any Feedback",Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });

        buttonUP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference upRef = FirebaseDatabase.getInstance().getReference().child("Feed");
                upRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.hasChild("feed4")){
                            try {
                                fed.setName(editTextName.getText().toString().trim());
                                fed.setContact(Integer.parseInt(editTextContact.getText().toString().trim()));
                                fed.setProname(editTextPname.getText().toString().trim());
                                fed.setDescription(editTextDes.getText().toString().trim());

                                dbRef = FirebaseDatabase.getInstance().getReference().child("Feed").child("feed4");
                                dbRef.setValue(fed);
                                clearControls();

                                Toast.makeText(getApplicationContext(),"Updated Successfully",Toast.LENGTH_SHORT).show();
                            } catch (NumberFormatException e) {
                                Toast.makeText(getApplicationContext(),"Please Enter a Valid Contact No",Toast.LENGTH_SHORT).show();
                            }
                        }
                        else
                            Toast.makeText(getApplicationContext(),"Nothing To Update",Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });

        buttonDEL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference delRef = FirebaseDatabase.getInstance().getReference().child("Feed");
                delRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.hasChild("feed4")){
                            dbRef = FirebaseDatabase.getInstance().getReference().child("Feed").child("feed4");
                            dbRef.removeValue();
                            clearControls();
                            Toast.makeText(getApplicationContext(),"Deleted Successfully",Toast.LENGTH_SHORT).show();
                        }
                        else
                            Toast.makeText(getApplicationContext(),"Nothing To Delete",Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });

    }
}