package com.example.finery;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
//import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class ProductAdmin extends AppCompatActivity {

    RecyclerView recyclerView;
    TextView txtID;
    Query query1;
    private DatabaseReference mdatabasereference;
    Product product;
    private ProgressDialog progressDialog;
    FirebaseRecyclerAdapter<Product, ProductViewHolder> firebaseRecyclerAdapter;
    //LinearLayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        Toolbar toolbar = findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
        setContentView(R.layout.activity_product_admin);
        //getSupportActionBar().hide();

        progressDialog = new ProgressDialog(ProductAdmin.this);
        progressDialog.setMessage("Loading Products Please Wait...");
        progressDialog.show();

        mdatabasereference = FirebaseDatabase.getInstance().getReference("products").child("accessories");

        recyclerView = (RecyclerView) findViewById(R.id.adminRecyclerViewGridView);

        txtID = (TextView) findViewById(R.id.adminpID);

        product = new Product();

        //String pID = txtID.getText().toString();

//        btnDelete.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                DatabaseReference delRef = FirebaseDatabase.getInstance().getReference().child("products").child("accessories");
//                delRef.addListenerForSingleValueEvent(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                        if(dataSnapshot.hasChild(txtID.getText().toString())){
//                            mdatabasereference = FirebaseDatabase.getInstance().getReference().child("products").child("accessories").child(txtID.getText().toString());
//                            mdatabasereference.removeValue();
//
//                            Toast.makeText(getApplicationContext(), "Deleted Successfully!!", Toast.LENGTH_SHORT).show();
//                        }
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                    }
//                });
//            }
//        });

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProductAdmin.this, AddNewProduct.class);
                startActivity(intent);

//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();
        query1 = FirebaseDatabase.getInstance().getReference().child("products").child("accessories");
        FirebaseRecyclerOptions<Product> options =
                new FirebaseRecyclerOptions.Builder<Product>()
                        .setQuery(query1, Product.class)
                        .build();

        //Log.d("Options"," data : "+options);

        firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Product, ProductViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull ProductViewHolder productViewHolder, final int i, @NonNull Product product_get_set_v) {

                //blogViewHolder.setname(product_get_set_v.getName());
                productViewHolder.setid(product_get_set_v.getId());
                productViewHolder.settitle(product_get_set_v.getTitle());
                productViewHolder.setprice(product_get_set_v.getPrice());
                productViewHolder.setoffer(product_get_set_v.getOffer());
                productViewHolder.setsize(product_get_set_v.getSize());
                productViewHolder.setcolor(product_get_set_v.getColor());
                productViewHolder.setdescription(product_get_set_v.getDescription());
                String image_url = productViewHolder.setimage(product_get_set_v.getImage());

                //String link= product_get_set_v.getLink();
                //Log.d("LINKDATA"," data : "+link);


                productViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final String productid=getRef(i).getKey();
                        //Log.d("productid"," data : "+productid);


                        assert productid != null;
                        mdatabasereference.child(productid).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                //String finallink = dataSnapshot.child("link").getValue(String.class);
                                //Log.d("productLink"," data : "+finallink);

//                                if(finallink!=null)
//                                {
//                                    Uri uriUrl = Uri.parse(finallink);
//                                    Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);
//                                    startActivity(launchBrowser);
//                                }
                            }
                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
                            }
                        });
                    }
                });

                productViewHolder.btnUpdate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final String productid = getRef(i).getKey();

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


                                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(ProductAdmin.this);
                                LayoutInflater inflater = getLayoutInflater();
                                final View dialogView = inflater.inflate(R.layout.activity_product_update, null);
                                dialogBuilder.setView(dialogView);

                                final EditText editId = (EditText) dialogView.findViewById(R.id.uProductID);
                                final EditText editTitle = (EditText) dialogView.findViewById(R.id.uProductTitle);
                                final EditText editDescription = (EditText) dialogView.findViewById(R.id.uProductDescription);
                                final EditText editColor = (EditText) dialogView.findViewById(R.id.uProductColor);
                                final EditText editSize = (EditText) dialogView.findViewById(R.id.uProductSize);
                                final EditText editPrice = (EditText) dialogView.findViewById(R.id.uProductPrice);
                                final EditText editOffer = (EditText) dialogView.findViewById(R.id.uProductOffer);
                                final EditText editImage = (EditText) dialogView.findViewById(R.id.uProductImage);
                                final Button buttonUpdate = (Button) dialogView.findViewById(R.id.updateBtn);

                                editId.setText(id);
                                editTitle.setText(title);
                                editDescription.setText(description);
                                editColor.setText(color);
                                editSize.setText(size);
                                editPrice.setText(price);
                                editOffer.setText(offer);
                                editImage.setText(image);


                                dialogBuilder.setTitle("Update Product");
                                final AlertDialog alertDialog = dialogBuilder.create();
                                alertDialog.show();


                                buttonUpdate.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        DatabaseReference updateRef = FirebaseDatabase.getInstance().getReference().child("products").child("accessories");
                                        updateRef.addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                product.setId(editId.getText().toString().trim());
                                                product.setTitle(editTitle.getText().toString().trim());
                                                product.setDescription(editDescription.toString().trim());
                                                product.setColor(editColor.getText().toString().trim());
                                                product.setSize(editSize.getText().toString().trim());
                                                product.setPrice(Integer.parseInt(editPrice.getText().toString().trim()));
                                                product.setOffer(Integer.parseInt(editOffer.getText().toString().trim()));
                                                product.setImage(editImage.getText().toString().trim());

                                                mdatabasereference = FirebaseDatabase.getInstance().getReference().child("products").child("accessories").child(productid);
                                                mdatabasereference.setValue(product);

                                                Toast.makeText(getApplicationContext(), "Updated Product", Toast.LENGTH_SHORT).show();
                                                alertDialog.dismiss();
                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError databaseError) {
                                            }
                                        });
                                    }
                                });

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
                            }
                        });

                    }
                });

                productViewHolder.btnDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(final View v) {
                        final String productid = getRef(i).getKey();
                        DatabaseReference delRef = FirebaseDatabase.getInstance().getReference().child("products").child("accessories");
                        delRef.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                assert productid != null;
                                if(dataSnapshot.hasChild(productid)){
                                    mdatabasereference = FirebaseDatabase.getInstance().getReference().child("products").child("accessories").child(productid);
                                    mdatabasereference.removeValue();

                                    Snackbar.make(v, "Successfully Deleted the Product", Snackbar.LENGTH_LONG).setAction("Action", null).show();

                                    //Toast.makeText(getApplicationContext(), "Deleted", Toast.LENGTH_SHORT).show();
                                }
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
            public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.activity_product_admin_card, parent, false);
                progressDialog.dismiss();
                return new ProductViewHolder(view);
            }
        };

        firebaseRecyclerAdapter.startListening();
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(),1);
        recyclerView.setLayoutManager(gridLayoutManager);

        recyclerView.setAdapter(firebaseRecyclerAdapter);
    }

    public static class ProductViewHolder extends RecyclerView.ViewHolder {
        public ImageButton btnDelete;
        public Button btnUpdate;
        View mView;

        public ProductViewHolder(View itemView) {
            super(itemView);
            mView = itemView;

            btnDelete = (ImageButton) mView.findViewById(R.id.adminpDelete);
            btnUpdate = (Button) mView.findViewById(R.id.adminpUpdate);
        }

//        public void setname(String name)
//        {
//            TextView ename=(TextView)mView.findViewById(R.id.admintext1);
//            ename.setText(name);
//
//        }

        public void setid(String id)
        {
            TextView pid=(TextView)mView.findViewById(R.id.adminpID);
            pid.setText(id);

        }

        public void settitle(String title)
        {
            TextView ptitle=(TextView)mView.findViewById(R.id.adminpTitle);
            ptitle.setText(title);

        }

        public void setprice(int price)
        {
            TextView pprice=(TextView)mView.findViewById(R.id.adminpPrice);
            pprice.setText(String.valueOf(price));

        }

        public void setsize(String size)
        {
            TextView psize=(TextView)mView.findViewById(R.id.adminpSize);
            psize.setText(size);

        }

        public void setcolor(String color)
        {
            TextView pcolor=(TextView)mView.findViewById(R.id.adminpColors);
            pcolor.setText(color);

        }

        public void setdescription(String description)
        {
            TextView pdescription=(TextView)mView.findViewById(R.id.adminpDescription);
            pdescription.setText(description);

        }

        public String setimage(String url)
        {
            ImageView image = (ImageView)mView.findViewById(R.id.adminpimage);
            Picasso.get().load(url).into(image);
            return url;
        }

        @SuppressLint("SetTextI18n")
        public void setoffer(int offer) {
            TextView poffer = (TextView) mView.findViewById(R.id.adminpOffer);
            poffer.setText(offer +"%");
        }
    }

//    private void ShowUpdateDialog(){
//        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
//        LayoutInflater inflater = getLayoutInflater();
//        final View dialogView = inflater.inflate(R.layout.activity_udate_product, null);
//        dialog.setView(dialogView);
//
//        TextView txtTitle = (TextView) findViewById(R.id.uProductTitle);
//        EditText txtSize = (EditText) findViewById(R.id.uProductSize);
//        EditText txtColor = (EditText) findViewById(R.id.uProductColor);
//        EditText txtPrice = (EditText) findViewById(R.id.uProductPrice);
//        EditText txtOffer = (EditText) findViewById(R.id.uProductOffer);
//        Button btnUpdate = (Button) findViewById(R.id.updateBtn);
//
//        dialog.setTitle("Edit Product");
//        final AlertDialog alertDialog = dialog.create();
//        alertDialog.show();
//
//        btnUpdate.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });
//
//    }

//    private boolean updatePrdouct (String id, String size, String color, String offer, String price){
//
//        DatabaseReference updb = FirebaseDatabase.getInstance().getReference().child("products").child("accessories").child(product)
//    }
}
