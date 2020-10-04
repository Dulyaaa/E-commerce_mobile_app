package com.example.finery;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.finery.Model.Customers;
import com.example.finery.Prevalent.Prevalent;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Login extends AppCompatActivity {

    private EditText InputContactNumber, InputPassword;
    private Button buttonlogin, buttonsignup, buttonforgotpw, buttonadminlogin;
    private ProgressDialog loadingBar;
    private String parentDbName = "Customers";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        buttonlogin = (Button) findViewById(R.id.buttonlogin);
        InputContactNumber = (EditText) findViewById(R.id.login_contact_number_input);
        InputPassword = (EditText) findViewById(R.id.login_password_input);

        loadingBar = new ProgressDialog(this);
        buttonsignup = findViewById(R.id.buttonsignup);
        buttonforgotpw = findViewById(R.id.buttonforgotpw);
        buttonadminlogin = findViewById(R.id.buttonadminlogin);

        buttonlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginUser();
            }
        });

        buttonsignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openSignup();
            }
        });

        buttonforgotpw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openforgotpw();
            }
        });
        
        buttonadminlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openAdminLogin();
            }
        });

    }

    private void openAdminLogin() {
        Intent intent = new Intent(this, AdminLogin.class);
        startActivity(intent);
    }

    private void openMyProfile() {
        Intent intent1 = new Intent(this, MyProfile.class);
        startActivity(intent1);
    }

    private void loginUser() {
        String contactNo = InputContactNumber.getText().toString();
        String password = InputPassword.getText().toString();

        if (TextUtils.isEmpty(contactNo)) {
            Toast.makeText(this, "Please enter your contactnumber", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Please enter password", Toast.LENGTH_SHORT).show();
        }
        else{
            loadingBar.setTitle("Login to Your Account");
            loadingBar.setMessage("Please wait, while we are checking the credentials.");
            loadingBar.setCanceledOnTouchOutside(false);
            loadingBar.show();

            AllowAccessToAccount(contactNo, password);
        }
    }

    private void AllowAccessToAccount(final String contactNo, final String password) {
        final DatabaseReference RootRef;
        RootRef = FirebaseDatabase.getInstance().getReference();

        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.child(parentDbName).child(contactNo).exists()){
                    Customers customersData = dataSnapshot.child(parentDbName).child(contactNo).getValue(Customers.class);


                    assert customersData != null;
                    if(customersData.getContactNo() != null  && customersData.getContactNo().equals(contactNo)){
                        if(customersData.getPassword() != null && customersData.getPassword().equals(password)){
                            Toast.makeText(Login.this, "Logged in Successfully", Toast.LENGTH_SHORT).show();
                            loadingBar.dismiss();

                            Intent intent3 = new Intent(Login.this, MainActivity.class);
                            Prevalent.currentOnlineCustomers = customersData;
                            startActivity(intent3);
                        }
                        else{
                            loadingBar.dismiss();
                            Toast.makeText(Login.this, "password is Incorrect", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
                else{
                    Toast.makeText(Login.this, "Please Create an Account", Toast.LENGTH_SHORT).show();
                    loadingBar.dismiss();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void openSignup() {
        Intent intent1 = new Intent(this, SignUp.class);
        startActivity(intent1);
    }

    public void openforgotpw() {
        Intent intent2 = new Intent(this, forgotpw.class);
        startActivity(intent2);
    }

    }
