package com.example.thesis;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

/**
 * Created by Cacay on 1/14/2018.
 */

public class PopInfoDeact extends AppCompatActivity {

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

    }
}