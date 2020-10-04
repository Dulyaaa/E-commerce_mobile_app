package com.example.finery;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class forgotpw extends AppCompatActivity {
    EditText editTextcusEmail;
    Button buttonsend;
    DatabaseReference dbref1;
    CusInqury cusInqury;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgotpw);

        editTextcusEmail = findViewById(R.id.editTextcusEmail);

        buttonsend = findViewById(R.id.buttonsend);

        cusInqury = new CusInqury();

        buttonsend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dbref1 = FirebaseDatabase.getInstance().getReference().child("CusInqury");

                cusInqury.setCusEmail(editTextcusEmail.getText().toString().trim());

                dbref1.push().setValue(cusInqury);

                Toast.makeText(getApplicationContext(), "Sent successfully!",Toast.LENGTH_SHORT).show();
                openLogin();
                clearControl();
            }
        });
    }
    public void openLogin(){
        Intent intent = new Intent(this, Login.class);
        startActivity(intent);
    }
    private void clearControl(){
        editTextcusEmail.setText("");
    }
}