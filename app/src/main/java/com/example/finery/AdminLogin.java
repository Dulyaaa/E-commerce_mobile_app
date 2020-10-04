package com.example.finery;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class AdminLogin extends AppCompatActivity {

    private EditText InputUserName;
    private EditText InputUserPassword;
    private Button buttonloginadmin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);

        buttonloginadmin = (Button) findViewById(R.id.buttonloginadmin);
        InputUserName = (EditText) findViewById(R.id.admin_username_input);
        InputUserPassword = (EditText) findViewById(R.id.admin_password_input);

        buttonloginadmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                loginAdmin();
                }
        });
    }

    private void loginAdmin() {
        String realname = "admin#1";
        String pass = "admin@finery";
        String userName = InputUserName.getText().toString();
        String userPassword = InputUserPassword.getText().toString();

        if(userName.equals(realname) && userPassword.equals(pass)){
            Intent intent = new Intent(AdminLogin.this, ProductAdmin.class);
            startActivity(intent);
        }

        }

}