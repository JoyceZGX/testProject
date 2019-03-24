package com.example.testproject;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import org.w3c.dom.Text;

public class DishActivity extends AppCompatActivity {

    TextView dishName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dish);

        dishName = findViewById(R.id.DishName);

        Intent n1 = getIntent();
        String newDish = n1.getStringExtra("DishName");
        dishName.setText(newDish);

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
                        Intent intent0 = new Intent (DishActivity.this, MenuActivity.class);
                        startActivity(intent0);
                        break;
                    case R.id.nav_menu:
                        Intent intent3 = new Intent (DishActivity.this, MenuActivity.class);
                        startActivity(intent3);
                        break;
                    case R.id.nav_report:
                        Intent intent2 = new Intent (DishActivity.this, MenuActivity.class);
                        startActivity(intent2);
                        break;


                }
                return false;
            }
        });
    }
}
