package com.example.finery;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class ProductCustomerView extends AppCompatActivity {

    public static final String ptitle = "Product Title";
    public static final String pid = "Product ID";
    public static final String pdescription = "Product Description";
    public static final String psize = "Product Size";
    public static final String pcolor = "Product Color";
    public static final String pimage = "Product Image";
    public static final String pprice = "Product Price";
    public static final String poffer = "Product Offer";

    Button btnMore;
    RecyclerView recyclerView;
    Query query1;
    private DatabaseReference mdatabasereference;
    private ProgressDialog progressDialog;
    FirebaseRecyclerAdapter<Product, BlogViewHolder> firebaseRecyclerAdapter;
    LinearLayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_product_customer_view);


        progressDialog = new ProgressDialog(ProductCustomerView.this);
        progressDialog.setMessage("Loading Products Please Wait...");
        progressDialog.show();

        mdatabasereference = FirebaseDatabase.getInstance().getReference("products").child("women");

        recyclerView = (RecyclerView) findViewById(R.id.recyclerViewGridView);
    }

    @Override
    protected void onStart() {
        super.onStart();
        query1 = FirebaseDatabase.getInstance().getReference().child("products").child("accessories");
        FirebaseRecyclerOptions<Product> options =
                new FirebaseRecyclerOptions.Builder<Product>()
                        .setQuery(query1, Product.class)
                        .build();

        firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Product, BlogViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull BlogViewHolder blogViewHolder, final int i, @NonNull Product product_get_set_v) {

                blogViewHolder.settitle(product_get_set_v.getTitle());
                blogViewHolder.setprice(product_get_set_v.getPrice());
                blogViewHolder.setoffer(product_get_set_v.getOffer());
                String image_url =blogViewHolder.setimage(product_get_set_v.getImage());

                blogViewHolder.btnmore.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final String productid = getRef(i).getKey();


                        assert productid != null;

                        mdatabasereference.child(productid).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                String id = dataSnapshot.child("id").getValue(String.class);
                                String title = dataSnapshot.child("title").getValue(String.class);
                                String description = dataSnapshot.child("description").getValue(String.class);
                                String size = dataSnapshot.child("size").getValue(String.class);
                                String color = dataSnapshot.child("color").getValue(String.class);
                                String image = dataSnapshot.child("image").getValue(String.class);
                                String price = dataSnapshot.child("price").getValue(Integer.class).toString();
                                String offer = dataSnapshot.child("offer").getValue(Integer.class).toString();

                                Intent intent = new Intent(ProductCustomerView.this, ProductCustomerMoreDetails.class);
                                intent.putExtra(pid, id);
                                intent.putExtra(ptitle, title);
                                intent.putExtra(pdescription, description);
                                intent.putExtra(psize, size);
                                intent.putExtra(pcolor, color);
                                intent.putExtra(pimage, image);
                                intent.putExtra(pprice, price);
                                intent.putExtra(poffer, offer);
                                startActivity(intent);
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
                            }
                        });
                    }
                });


                blogViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final String productid = getRef(i).getKey();

                        mdatabasereference.child(productid).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
                            }
                        });
                    }
                });

            }

            @NonNull
            @Override
            public BlogViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.cus_product_grid, parent, false);
                progressDialog.dismiss();
                return new BlogViewHolder(view);
            }
        };

        firebaseRecyclerAdapter.startListening();
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(),2);
        recyclerView.setLayoutManager(gridLayoutManager);

        recyclerView.setAdapter(firebaseRecyclerAdapter);
    }

    public static class BlogViewHolder extends RecyclerView.ViewHolder {
        View mView;
        Button btnmore;

        public BlogViewHolder(View itemView) {
            super(itemView);
            mView=itemView;

            btnmore = (Button) itemView.findViewById(R.id.moreBtn);
        }

        public void settitle(String title)
        {
            TextView ptitle=(TextView)mView.findViewById(R.id.productTitle);
            ptitle.setText(title);

        }

        public void setprice(int price)
        {
            TextView pprice=(TextView)mView.findViewById(R.id.productPrice);
            pprice.setText("$" + String.valueOf(price));

        }

        public String setimage(String url)
        {
            ImageView image = (ImageView)mView.findViewById(R.id.productimage);
            Picasso.get().load(url).into(image);
            return url;
        }

        public void setoffer(int offer){
            TextView poffer = (TextView)mView.findViewById(R.id.productOffer);
            poffer.setText(String.valueOf(offer)+"%");
        }
    }

}
