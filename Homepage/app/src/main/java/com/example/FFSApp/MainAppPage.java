package com.theboyz.pages;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

<<<<<<< HEAD:Homepage/app/src/main/java/com/example/FFSApp/MainAppPage.java
import com.theboyz.utils.*;

import android.view.View;
=======
import com.example.homepage.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.theboyz.utils.*;
>>>>>>> c9617f4f38bff764ce220a783e83b3504d7dd6ce:Homepage/app/src/main/java/com/theboyz/pages/MainAppPage.java

public class MainAppPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_app_page2);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }
}
