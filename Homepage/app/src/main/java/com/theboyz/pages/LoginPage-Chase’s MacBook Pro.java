package com.theboyz.pages;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.theboyz.utils.*;

import androidx.appcompat.app.AppCompatActivity;

import com.example.homepage.R;

public class LoginPage extends AppCompatActivity {

    private Button login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);

        login = findViewById(R.id.login_button);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText email = findViewById(R.id.email);
                EditText password = findViewById(R.id.pss);

                if(login(email, password)) {
                    Log.d("boi_work", "login worked");
//                    openMainAppPage();
                }
                else {
                    Log.d("boi_fail", "did not login");
                }
//                openMainAppPage();
//                //el  se {
//                    //print login error
//                //}
            }
        });

    }

    //function login will take the username and password and will check with the databse to see if it is valid
    protected boolean login(EditText email, EditText password) {
        userDB db = new userDB();
        System.out.println("Bout to login");
        if(db.authenticate("nflfan101@email.com","cowboyssuck666")) {
            Log.d("boi__", "yay");
            System.out.println("THis shit work");
        }
        else {
            System.out.println("nah fam");
            Log.d("boi__", "aww");
        }

//        Log.d("boi", email.getText().toString() + password.getText().toString());
//        if(db.authenticate(email.getText().toString(), password.getText().toString())) {
//            return true;
//        }
        return false;
    }

    //openLoginPageActivity will move from the register page to the LoginPage
    public void openMainAppPage() {
        Intent intent = new Intent(getApplicationContext(), MainAppPage.class);
        startActivity(intent);
    }
}
