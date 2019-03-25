package com.example.testproject;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

public class DishActivity extends AppCompatActivity {

    TextView dishName;
    Button posting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dish);

        dishName = findViewById(R.id.DishName);
        posting = findViewById(R.id.sendFeedback);

        Intent n1 = getIntent();
        String newDish = n1.getStringExtra("DishName");
        dishName.setText(newDish);

        Intent in1 = new Intent(DishActivity.this, PostActivity.class);
        in1.putExtra("Dish",newDish);
        startActivity(in1);

        posting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (DishActivity.this, PostActivity.class);
                startActivity(intent);
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
