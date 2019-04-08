package com.example.testproject;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.google.common.base.Strings;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.List;

public class DishActivity extends AppCompatActivity {

    TextView dishName;
    Button posting;
    RecyclerView mcommentList;
    RatingBar mRatingBar; //rating
    List<String> comment;
    DatabaseReference mdatabaseReferece;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dish);

        dishName = findViewById(R.id.DishName);
        posting = findViewById(R.id.sendFeedbackBtn);
        mRatingBar = findViewById(R.id.ratingBar);
        mcommentList = findViewById(R.id.commentList);
        //mcommentList.setLayoutManager(new LinearLayoutManager(this));

        Intent n1 = getIntent();
        final String newDish = n1.getStringExtra("DishName");
        dishName.setText(newDish);

        mdatabaseReferece = FirebaseDatabase.getInstance().getReference("Post");
        //ArrayAdapter<String> adapter =new ArrayAdapter<String>(DishActivity.this,android.R.layout.simple_list_item_1,callData());
        mdatabaseReferece.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        posting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in1 = new Intent(DishActivity.this, PostActivity.class);
                in1.putExtra("DishName",newDish);
                startActivity(in1);
            }
        });

        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation_bar);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.nav_home:
                        Intent intent1 = new Intent (DishActivity.this, StartActivity.class);
                        startActivity(intent1);
                        break;
                    case R.id.nav_account:
                        Intent intent0 = new Intent (DishActivity.this, DisplayActivity.class);
                        startActivity(intent0);
                        break;
                    case R.id.nav_menu:
                        Intent intent3 = new Intent (DishActivity.this, DisplayActivity.class);
                        startActivity(intent3);
                        break;
                    case R.id.nav_report:
                        Intent intent2 = new Intent (DishActivity.this, DisplayActivity.class);
                        startActivity(intent2);
                        break;


                }
                return false;
            }
        });
    }


}
