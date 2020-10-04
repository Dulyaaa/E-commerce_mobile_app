package com.example.finery;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.finery.Model.Product;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddNewProduct extends AppCompatActivity {

    //Declaring a objects
    EditText txtpID, txtpTitle, txtpPrice, txtpSize, txtpColors, txtpDescription, txtpImage, txtOffer;
    Button btnSave, btnClear;
    DatabaseReference dbRef;
    Product product;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();  //To hide the action bar
        setContentView(R.layout.activity_add_new_product);

        //Getting the ID's from layout
        txtpID = findViewById(R.id.pID);
        txtpTitle = findViewById(R.id.pTitle);
        txtpPrice = findViewById(R.id.pprice);
        txtpSize = findViewById(R.id.psize);
        txtpColors = findViewById(R.id.pcolors);
        txtpDescription = findViewById(R.id.pdescription);
        txtpImage = findViewById(R.id.pimage);
        txtOffer = findViewById(R.id.poffer);

        btnSave = findViewById(R.id.psave);
        btnClear = findViewById(R.id.pclear);

        //Reference
        product = new Product();

        //Button OnClick to insert
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dbRef = FirebaseDatabase.getInstance().getReference().child("products").child("accessories");

                //Validations
                try {
                    if (TextUtils.isEmpty(txtpID.getText().toString()))
                        Toast.makeText(getApplicationContext(), "Please enter a product ID", Toast.LENGTH_SHORT).show();
                    else if (TextUtils.isEmpty(txtpTitle.getText().toString()))
                        Toast.makeText(getApplicationContext(), "Please enter a product Name", Toast.LENGTH_SHORT).show();

                    else if (TextUtils.isEmpty(txtpSize.getText().toString()))
                        Toast.makeText(getApplicationContext(), "Please enter a product size ", Toast.LENGTH_SHORT).show();
                    else if (TextUtils.isEmpty(txtpColors.getText().toString()))
                        Toast.makeText(getApplicationContext(), "Please enter a product colours", Toast.LENGTH_SHORT).show();
                    else if (TextUtils.isEmpty(txtpDescription.getText().toString()))
                        Toast.makeText(getApplicationContext(), "Please enter product description", Toast.LENGTH_SHORT).show();
                    else {

                        //Insert data to database if there's no errors
                        product.setId(txtpID.getText().toString().trim());
                        product.setTitle(txtpTitle.getText().toString().trim());
                        product.setPrice(Integer.parseInt(txtpPrice.getText().toString().trim()));
                        product.setOffer(Integer.parseInt(txtOffer.getText().toString().trim()));
                        product.setSize(txtpSize.getText().toString().trim());
                        product.setColor(txtpColors.getText().toString().trim());
                        product.setDescription(txtpDescription.getText().toString().trim());
                        product.setImage(txtpImage.getText().toString().trim());

                        dbRef.child(txtpID.getText().toString()).setValue(product);
                        Toast.makeText(getApplicationContext(), "Data entered", Toast.LENGTH_SHORT).show();

                    }
                } catch (NumberFormatException e){
                    Toast.makeText(getApplicationContext(), "Please enter numbers", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //Button to clear controls
        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtpID.setText("");
                txtpTitle.setText("");
                txtpDescription.setText("");
                txtpPrice.setText("");
                txtpSize.setText("");
                txtpColors.setText("");
                txtpImage.setText("");
                txtOffer.setText("");
            }
        });
    }
}
