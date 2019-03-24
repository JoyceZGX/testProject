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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class AccountActivity extends AppCompatActivity {

    private Button mLogOut;
    private FirebaseAuth mAuth;
    private TextView text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        text = findViewById(R.id.username);
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null){
            if (user.getEmail()!= null) {
                String name = user.getEmail();
                text.setText("Hello "+name);
            }
        }




        mLogOut = (Button) findViewById(R.id.logOutBtn);

        mLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
            }
        });

        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation_bar);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.nav_home:
                        Intent intent1 = new Intent (AccountActivity.this, StartActivity.class);
                        startActivity(intent1);
                        break;
                    case R.id.nav_account:
                        break;
                    case R.id.nav_menu:
                        Intent intent0 = new Intent (AccountActivity.this, MenuActivity.class);
                        startActivity(intent0);
                        break;
                    case R.id.nav_report:
                        Intent intent2 = new Intent (AccountActivity.this, MenuActivity.class);
                        startActivity(intent2);
                        break;


                }
                return false;
            }
        });


    }
}
