package com.example.testproject;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * DishActivity class that displays the dishname, comments, photos and average rating of each dish
 *
 * @author Genxing Zhan
 * @Date 4/20/2019
 * @version 1.0
 */

public class DishActivity extends AppCompatActivity {

    TextView dishName;
    Button posting;
    ListView mcommentView;
    RatingBar mRatingBar; //rating
    ImageView favourite;
    ArrayList<String> list;
    DatabaseReference mdatabaseReferece;
    ArrayAdapter<String> adapter;
    LinearLayout gallery;
    ArrayList<String> favouriteList;
    int index = 0;
    float numStars;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dish);

        dishName = findViewById(R.id.DishName);
        posting = findViewById(R.id.sendFeedbackBtn);
        mRatingBar = findViewById(R.id.ratingBar);
        mcommentView = findViewById(R.id.commentList);
        gallery =findViewById(R.id.gallery);
        favourite = findViewById(R.id.favouriteBtn);
        favouriteList = new ArrayList<>();

        //get the dishName of the intent
        Intent n1 = getIntent();
        final String newDish = n1.getStringExtra("DishName");
        dishName.setText(newDish);

        /**
         *Add dish to the favourite list
         */
        favourite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in1 = new Intent(DishActivity.this, AccountActivity.class);
                in1.putExtra("fav",newDish);
                startActivity(in1);
            }
        });


        /**
         * Retrieve data from Firebase
         */
        mdatabaseReferece = FirebaseDatabase.getInstance().getReference("Post");
        list = new ArrayList<>();
        mdatabaseReferece.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){

                    if (dataSnapshot1.hasChild("comment")) {
                        if (dataSnapshot1.child("dishName").getValue().toString().equals(newDish)) {
                            list.add(dataSnapshot1.child("comment").getValue().toString());
                        }
                    }
                    if (dataSnapshot1.hasChild("rating")) {
                        if (dataSnapshot1.child("dishName").getValue().toString().equals(newDish)) {
                            index++;
                            numStars += Float.parseFloat(dataSnapshot1.child("rating").getValue().toString());
                            numStars /= index;
                            mRatingBar.setRating(numStars);
                        }
                    }
                    if (dataSnapshot1.hasChild("image")) {
                        if (dataSnapshot1.child("dishName").getValue().toString().equals(newDish)) {
                            LayoutInflater inflater = LayoutInflater.from(DishActivity.this);
                            final View view = inflater.inflate(R.layout.gallery_list, gallery, false);
                            ImageView imageView = view.findViewById(R.id.gallery_photo);
                            String imageUri = dataSnapshot1.child("image").getValue().toString();
                            Picasso.get().load(imageUri).rotate(90).into(imageView);
                            gallery.addView(view);
                        }
                    }
                }
                adapter = new ArrayAdapter<String>(DishActivity.this, R.layout.simple_list,list);
                mcommentView.setAdapter(adapter);


            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        /**
         *Go to the post page when post clicked
         */
        posting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in1 = new Intent(DishActivity.this, PostActivity.class);
                in1.putExtra("DishName",newDish);
                startActivity(in1);
            }
        });

        /**
         *Navigation Bar set up
         */
        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation_bar);
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        //recognize chartwells default email address
        if (mAuth.getCurrentUser().getEmail().equals("report.tristy@gmail.com")){
            bottomNavigationView.getMenu().findItem(R.id.nav_report).setVisible(true);
        }
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.nav_home:
                        Intent intent1 = new Intent (DishActivity.this, StartActivity.class);
                        startActivity(intent1);
                        break;
                    case R.id.nav_account:
                        Intent intent0 = new Intent (DishActivity.this, AccountActivity.class);
                        startActivity(intent0);
                        break;
                    case R.id.nav_menu:
                        Intent intent3 = new Intent (DishActivity.this, MenuActivity.class);
                        startActivity(intent3);
                        break;
                    case R.id.nav_report:
                        Intent intent2 = new Intent (DishActivity.this, ReportActivity.class);
                        startActivity(intent2);
                        break;


                }
                return false;
            }
        });
    }


}
