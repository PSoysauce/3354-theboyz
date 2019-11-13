package com.theboyz.pages.pages;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.homepage.R;

public class MainActivity extends AppCompatActivity {

    private Button login;
    private Button register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Sets the button variable to the login button I created in the xml, so if they click it it will change to that given activity
        login = findViewById(R.id.login_button);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openLoginPageActivity();
            }
        });

        //Sets the button variable to the register button I created in the xml, so if they click it it will change to that given activity
        register = findViewById(R.id.create_account_button);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openRegisterPageActivity();
            }
        });
    }

    //openLoginPageActivity will move from the homepage to the LoginPage
    public void openLoginPageActivity() {
        Intent intent = new Intent(getApplicationContext(), LoginPage.class);
        startActivity(intent);
    }

    //openRegisterPageActivity will move from the homepage to the CreateAccount page
    public void openRegisterPageActivity() {
        Intent intent = new Intent(getApplicationContext(), CreateAccount.class);
        startActivity(intent);
    }
}
