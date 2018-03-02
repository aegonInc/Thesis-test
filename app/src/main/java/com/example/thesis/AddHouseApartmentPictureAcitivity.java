package com.example.thesis;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class AddHouseApartmentPictureAcitivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_house_apartment_picture_acitivity);

        Button b = (Button) findViewById(R.id.btnNextPicApart);
        b.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                startActivity(new Intent(AddHouseApartmentPictureAcitivity.this,addHouseApartmentRulesActivity.class));
            }


        });
    }
}
