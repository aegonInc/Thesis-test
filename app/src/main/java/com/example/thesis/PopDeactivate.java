package com.example.thesis;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.TextView;

/**
 * Created by Cacay on 1/13/2018.
 */

public class PopDeactivate extends AppCompatActivity{

    View view;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.pop_deactivate);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int) (width * .8), (int) (height * .6));


        TextView deact = (TextView) findViewById(R.id.deactivate);
        deact.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Deact(v);
            }
        });
    }

    public void Deact(View view) {
        //use intent get permission to access the another screen
        Intent i = new Intent(this, PopInfoDeact.class);
        startActivity(i);


    }
}
