package com.example.testproject;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

public class StartActivity extends AppCompatActivity {

    private Button matherB;
    private Button caveB;
    private Button bistroB;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        mAuth = FirebaseAuth.getInstance();

        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation_bar);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.nav_home:
                        break;
                    case R.id.nav_account:
                        Intent intent0 = new Intent (StartActivity.this, AccountActivity.class);
                        startActivity(intent0);
                        break;
                    case R.id.nav_menu:
                        Intent intent1 = new Intent (StartActivity.this, MenuActivity.class);
                        startActivity(intent1);
                        break;
                    case R.id.nav_report:
                        Intent intent2 = new Intent (StartActivity.this, MenuActivity.class);
                        startActivity(intent2);
                        break;
                }
                return false;
            }
        });


        matherB = (Button) findViewById(R.id.mather);
        caveB = (Button) findViewById(R.id.cave);
        bistroB = (Button) findViewById(R.id.bistro);
        matherB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMatherMenu();
            }
        });
    }

    public void openMatherMenu(){
        Intent intent = new Intent(this, DisplayActivity.class);
        startActivity(intent);
    }
}
