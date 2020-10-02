package com.example.finery;


import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CartActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private Button NextProcessBtn;
    private TextView txtTotalAmount;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        recyclerView = findViewById(R.id.cart_list);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        NextProcessBtn = (Button) findViewById(R.id.next_process_btn);
        txtTotalAmount = (TextView) findViewById(R.id.total_price);

    }
    public void onClick(View view) {
        Intent i;
        i=new Intent(this,DeliveryDetails.class);startActivity(i);



    }

    //@Override
    //protected void onStart() {
      //  super.onStart();

        //final DatabaseReference cartListRef = FirebaseDatabase.getInstance().getReference().child("Cart List");

        //FirebaseRecyclerOptions<Cart> options =
                //new FirebaseRecyclerOptions.Builder<Cart>()
               // .setQuery(cartListRef.child("User View")
                        //.child().child("Products"),Cart.class).build();

        //FirebaseRecyclerAdapter<Cart,CartViewHolder> adapter = new FirebaseRecyclerAdapter<Cart, CartViewHolder>(options) {
           // @Override
            //protected void onBindViewHolder(@NonNull CartViewHolder cartViewHolder, int i, @NonNull Cart cart) {

            }

            //@NonNull
           // @Override
           // public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
              //  View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_items_layout,parent,false);
               // CartViewHolder holder = new CartViewHolder(view);
               // return holder;
            //}
        //}

   // }
//}