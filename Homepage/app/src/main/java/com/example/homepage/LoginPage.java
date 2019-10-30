package com.example.homepage;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

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
                EditText username = findViewById(R.id.usr);
                EditText password = findViewById(R.id.pss);
                if(login(username, password)) {
                    //do login stuff
                }
                else {
                    //print login error
                }
            }
        });

    }

    //function login will take the username and password and will check with the databse to see if it is valid
    protected boolean login(EditText usr, EditText pass) {
        //database call (usr.getText().toString(), pass.getText().toString());
        return true;
    }

}
