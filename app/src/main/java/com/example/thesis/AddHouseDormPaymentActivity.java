package com.example.thesis;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

public class AddHouseDormPaymentActivity extends AppCompatActivity {
    private LinearLayout parentLinearLayout2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_house_dorm_payment);
        parentLinearLayout2 = (LinearLayout) findViewById(R.id.linear_dorm_payment);

        Button b = (Button) findViewById(R.id.btnNextDormPay);
        b.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                startActivity(new Intent(AddHouseDormPaymentActivity.this, AddHouseDormPaymentRulesActivity.class));
            }


        });
    }

    public void onAddFieldRule(View v) {
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View rowView = inflater.inflate(R.layout.dorm_payment_field, null);
        // Add the new row before the add field button.
        parentLinearLayout2.addView(rowView, parentLinearLayout2.getChildCount() - 1);
    }

    public void onDeleteRule(View v) {
        parentLinearLayout2.removeView((View) v.getParent());
    }

}
