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
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * ReportActivity that shows the report for Chartwells staff members
 *
 * @author Genxing Zhan
 * @version 1.0
 */

public class ReportActivity extends AppCompatActivity {

    DatabaseReference mdatabaseReferece;
    ArrayList<String> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);

        /**
         * Retrieve data from firebase
         */
        mdatabaseReferece = FirebaseDatabase.getInstance().getReference("Post");
        list = new ArrayList<>();
        int index = 0;
        final HashMap<String, Float> reportList = new HashMap<>();
        mdatabaseReferece.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                    reportList.put(dataSnapshot1.child("dishName").getValue().toString(),
                            Float.parseFloat(dataSnapshot1.child("rating").getValue().toString()));
                    if (reportList.containsKey(dataSnapshot1.child("dishName").getValue().toString())){
                        //index++;
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


         /**
          * Navigation Bar set up
          */
        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation_bar);
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        if (mAuth.getCurrentUser().getEmail().equals("report.tristy@gmail.com")){
            bottomNavigationView.getMenu().findItem(R.id.nav_report).setVisible(true);
        }
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.nav_home:
                        Intent intent1 = new Intent (ReportActivity.this, StartActivity.class);
                        startActivity(intent1);
                        break;
                    case R.id.nav_account:
                        Intent intent0 = new Intent (ReportActivity.this, AccountActivity.class);
                        startActivity(intent0);
                        break;
                    case R.id.nav_menu:
                        Intent intent3 = new Intent (ReportActivity.this, MenuActivity.class);
                        startActivity(intent3);
                        break;
                    case R.id.nav_report:
                        Intent intent2 = new Intent (ReportActivity.this, MenuActivity.class);
                        startActivity(intent2);
                        break;


                }
                return false;
            }
        });
    }
}


