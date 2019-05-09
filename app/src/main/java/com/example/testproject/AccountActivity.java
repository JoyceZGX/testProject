package com.example.testproject;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

/**
 * AccountActivity that display user email address, favourite dishes, and sign out option
 *
 * @author Genxing Zhan
 * @version 1.0
 */
public class AccountActivity extends AppCompatActivity {

    private Button mLogOut;
    private GoogleApiClient mGoogleSignInClient;
    private FirebaseAuth mAuth;
    private TextView text;
    ArrayAdapter<String> adapter;
    ListView favView;
    static ArrayList<String> favouriteList;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        text = findViewById(R.id.username);
        favView = findViewById(R.id.list_favourite);
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null){
            if (user.getEmail()!= null) {
                String name = user.getDisplayName();
                text.setText("Hello "+name);
            }
        }

        //log out
        mLogOut = (Button) findViewById(R.id.logOutBtn);
        mLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signOut();
                startActivity(new Intent(AccountActivity.this, AuthActivity.class));
            }
        });


        /**
         * Display user's favourite dishes
         */
        favouriteList = new ArrayList<>();
        String fav = getIntent().getStringExtra("fav");
        favouriteList.add(fav);
        adapter = new ArrayAdapter<String>(AccountActivity.this, R.layout.simple_list,favouriteList);
        favView.setAdapter(adapter);
        adapter.notifyDataSetChanged();


        /**
         * Bottom Navigation Bar set up
         */
        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation_bar);
        //recognize Chartwells email address
        if (mAuth.getCurrentUser().getEmail().equals("report.tristy@gmail.com")){
            bottomNavigationView.getMenu().findItem(R.id.nav_report).setVisible(true);
        }
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
                        Intent intent2 = new Intent (AccountActivity.this, ReportActivity.class);
                        startActivity(intent2);
                        break;


                }
                return false;
            }
        });


    }

    public void signOut() {
        // Firebase sign out
        mAuth.signOut();

        // Google sign out
        Auth.GoogleSignInApi.signOut(mGoogleSignInClient);
    }

}
