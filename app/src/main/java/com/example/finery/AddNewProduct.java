package com.example.finery;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddNewProduct extends AppCompatActivity {

    EditText txtpID, txtpTitle, txtpPrice, txtpSize, txtpColors, txtpDescription, txtpImage, txtOffer;
    Button btnSave, btnClear;
    DatabaseReference dbRef;
    Product product;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_add_new_product);

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

        product = new Product();

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dbRef = FirebaseDatabase.getInstance().getReference().child("products").child("accessories");

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
        });

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
