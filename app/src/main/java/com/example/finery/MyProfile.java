package com.example.finery;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.finery.Prevalent.Prevalent;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class MyProfile extends AppCompatActivity {
    private EditText nameEdit, emailEdit, contactNoEdit;
    private Button buttonupdate, buttondelete;
    DatabaseReference dbref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);

        nameEdit = (EditText) findViewById(R.id.profile_username_input);
        emailEdit = (EditText) findViewById(R.id.profile_email_input);
        contactNoEdit = (EditText) findViewById(R.id.profile_contact_number_input);
        buttonupdate = (Button) findViewById(R.id.buttonupdate);
        buttondelete = (Button) findViewById(R.id.buttondelete);

        customerInfoDispaly(nameEdit,emailEdit,contactNoEdit);

        buttonupdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateCusInfo();
            }
        });
    }

    private void updateCusInfo() {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Customers");

        HashMap<String, Object> userMap = new HashMap<>();
        userMap.put("name", nameEdit.getText().toString());
        userMap.put("email", emailEdit.getText().toString());
        userMap.put("contactNo", contactNoEdit.getText().toString());
        ref.child(Prevalent.currentOnlineCustomers.getContactNo()).updateChildren(userMap);

        startActivity(new Intent(MyProfile.this, MainActivity.class));
        Toast.makeText(MyProfile.this, "Update Successfully!", Toast.LENGTH_SHORT).show();
        finish();
    }

    private void customerInfoDispaly(final EditText nameEdit, final EditText emailEdit, final EditText contactNoEdit) {
        String currentUser = Prevalent.currentOnlineCustomers.getContactNo();
        final DatabaseReference cusRef = FirebaseDatabase.getInstance().getReference().child("Customers").child(currentUser);
        cusRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    String name = dataSnapshot.child("name").getValue().toString();
                    String email = dataSnapshot.child("email").getValue().toString();
                    String contactNo = dataSnapshot.child("contactNo").getValue().toString();

                    nameEdit.setText(name);
                    emailEdit.setText(email);
                    contactNoEdit.setText(contactNo);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        buttondelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String currentUser = Prevalent.currentOnlineCustomers.getContactNo();
                DatabaseReference delRef = FirebaseDatabase.getInstance().getReference().child("Customers");
                delRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if(dataSnapshot.exists()){
                            dbref = FirebaseDatabase.getInstance().getReference().child("Customers").child(currentUser);
                            dbref.removeValue();
                            clearControls();
                            Toast.makeText(getApplicationContext(), "Account deleted successfully!", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(MyProfile.this, Login.class));
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });

    }

    private void clearControls() {
        nameEdit.setText("");
        emailEdit.setText("");
        contactNoEdit.setText("");
    }
}