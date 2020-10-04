package com.example.finery;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.finery.Model.Product;
import com.example.finery.Prevalent.Prevalent;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import static com.example.finery.ProductCustomerView.pcolor;
import static com.example.finery.ProductCustomerView.pdescription;
import static com.example.finery.ProductCustomerView.pid;
import static com.example.finery.ProductCustomerView.psize;
import static com.example.finery.ProductCustomerView.ptitle;

public class ProductCustomerMoreDetails extends AppCompatActivity {

    TextView txtTitle, txtDescription, txtSize, txtColor, txtPrice;
    ImageView pimage;
    Button btnAddToCart;
    DatabaseReference databaseReference;
    Product product;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Product Details");
        getSupportActionBar().setHomeButtonEnabled(true);
        setContentView(R.layout.activity_product_customer_more_details);

        //Getting data
        Intent intent = getIntent();
        final String id = intent.getStringExtra(pid);
        String title = intent.getStringExtra(ptitle);
        String description = intent.getStringExtra(pdescription);
        String size = intent.getStringExtra(psize);
        String color = intent.getStringExtra(pcolor);
        final String image = intent.getStringExtra(ProductCustomerView.pimage);
        String price = intent.getStringExtra(ProductCustomerView.pprice);
        String offer = intent.getStringExtra(ProductCustomerView.poffer);

        txtTitle = (TextView) findViewById(R.id.cusProductTitle);
        txtDescription = (TextView) findViewById(R.id.cusProductDescription);
        txtSize = (TextView) findViewById(R.id.cusProductSize);
        txtColor = (TextView) findViewById(R.id.cusProductColors);
        txtPrice = (TextView) findViewById(R.id.cusProductPrice);
        pimage = (ImageView) findViewById(R.id.cusProductImage);
        btnAddToCart = (Button) findViewById(R.id.Addtocart);

        //Converting string to integer to do the calculations
        int productPrice = Integer.parseInt(price);
        int productOffer = Integer.parseInt(offer);

        final int calc = calculateOffer(productPrice, productOffer); //Calling method to calculate offer

        //Setting texts
        txtTitle.setText(title);
        txtDescription.setText(description);
        txtSize.setText(size);
        txtColor.setText(color);
        txtTitle.setText(title);
        txtPrice.setText("$ " + calc);
        Picasso.get().load(image).into(pimage);

        product = new Product();

        btnAddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String currentUser = Prevalent.currentOnlineCustomers.getContactNo();
                databaseReference = FirebaseDatabase.getInstance().getReference().child("cart").child(currentUser);

                product.setTitle(txtTitle.getText().toString().trim());
                product.setPrice(calc);
                product.setImage(image.toString().trim());
                databaseReference.child(id).setValue(product);

                Toast.makeText(getApplicationContext(), "Added to Cart", Toast.LENGTH_SHORT).show();

            }
        });

    }

    //Method to calculate the due price
    public int calculateOffer(int price, int offer){
        if(offer != 0){ //Offer shouldn't be a zero

            int calc = price - ((price * offer)/100);//Doing the calculation

            String calculation = String.valueOf(calc);//Convert calculated value to string
            return Integer.parseInt(calculation);
        }
        else
            return price;
    }
}
