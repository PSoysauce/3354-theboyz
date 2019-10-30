package com.example.homepage;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class CreateAccount extends AppCompatActivity {
    private Button register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        //Sets the button variable to the register button I created in the xml, so if they click it it will change to that given activity
        register = findViewById(R.id.register_button);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText passwordA = findViewById(R.id.passwordA);
                EditText passwordB = findViewById(R.id.passwordB);
                EditText fName = findViewById(R.id.first_name);
                EditText lName = findViewById(R.id.last_name);
                EditText email = findViewById(R.id.email);
                EditText usrName = findViewById(R.id.user_name);

                //Check if the passwords match before registering
                if(passwordMatch(passwordA, passwordB)) {
                    if(register(fName, lName, email, usrName, passwordA)) {
                        openLoginPageActivity();
                    }
                    else {
                        //Display error that registration failed
                    }
                }
                else {
                    //Display Error somehow
                }
            }
        });


    }

    //function passwordMatch will take the two different passwords entered and will
    protected boolean passwordMatch(EditText a, EditText b) {
        return (a.getText().toString().equals(b.getText().toString()) && !(a.getText().toString().equals("")));
    }

    //openLoginPageActivity will move from the register page to the LoginPage
    public void openLoginPageActivity() {
        Intent intent = new Intent(getApplicationContext(), LoginPage.class);
        startActivity(intent);
    }

    //function register will take all the values the user entered and will register to database, if any errors occur will return
    //false which will make the form print out an error
    protected boolean register(EditText fName, EditText lName, EditText email, EditText usrName, EditText passwordA) {
        //Logic for database go here?
        return true;
    }
}
