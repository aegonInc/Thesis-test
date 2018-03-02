package com.example.thesis;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;

/**
 * Created by Cacay on 1/13/2018.
 */

public class PopLI extends AppCompatActivity {
View view;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.poplogin);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int) (width * .8), (int) (height * .6));



        Button btnSignUp = (Button) findViewById(R.id.btnSignUp);
        btnSignUp.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                SignUp(v);
            }
        });

        Button logIn = (Button) findViewById(R.id.btnLogIn);
        logIn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                LogIn(v);
            }
        });


    }

    public void LogIn(View view) {
        //use intent get permission to access the another screen
        Intent i = new Intent(this, Owner_Profile_Activity.class);
        startActivity(i);

    }


        public void SignUp(View view) {
            //use intent get permission to access the another screen
            Intent i = new Intent(this, SignupActivity.class);
            startActivity(i);

        }

    }









