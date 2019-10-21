package com.example.ffs_ui;


import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    Button buttonLogin, buttonSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonLogin = (Button) findViewById(R.id.buttonLogin); // get id of login button
        buttonSignUp = (Button) findViewById(R.id.buttonSignUp); // get id of sign up button

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                Toast.makeText(getApplicationContext(), "wowza", Toast.LENGTH_LONG).show();
                // ^ this will just show which button we press. will need to figure out how to make it do something
                //startActivity(new Intent(MainActivity.this, LoginPage.class));
            }
        });
        buttonSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                Toast.makeText(getApplicationContext(), "Ya pressed the signup,  parteeey", Toast.LENGTH_LONG).show();
                // ^ this will just show which button we press. will need to figure out how to make it do something
            }
        });

    }
}
