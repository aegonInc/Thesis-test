package com.example.thesis;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class AddHouseBoardingHousePictureActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_house_boarding_house_picture);


        Button b = (Button) findViewById(R.id.btnNextPicBH);
        b.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                startActivity(new Intent(AddHouseBoardingHousePictureActivity.this,AddHouseBoardingHouseRuleActivity.class));
            }


        });
    }
}
