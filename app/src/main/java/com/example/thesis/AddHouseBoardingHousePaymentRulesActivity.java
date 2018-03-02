package com.example.thesis;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

public class AddHouseBoardingHousePaymentRulesActivity extends AppCompatActivity {

    private LinearLayout parentLinearLayout2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_house_boarding_house_payment_rules);
        parentLinearLayout2 = (LinearLayout) findViewById(R.id.linear_boardinghouse_payment_rules);


        Button b = (Button) findViewById(R.id.btnNextPayRuleBH);
        b.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                startActivity(new Intent(AddHouseBoardingHousePaymentRulesActivity.this,AddHouseBoardingHouseSaveActivity.class));
            }


        });

    }

    public void onAddFieldRule(View v) {
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View rowView = inflater.inflate(R.layout.boarding_house_payment_rule_field, null);
        // Add the new row before the add field button.
        parentLinearLayout2.addView(rowView, parentLinearLayout2.getChildCount() - 1);
    }

    public void onDeleteRule(View v) {
        parentLinearLayout2.removeView((View) v.getParent());
    }

}
