package com.example.thesis;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

/**
 * Created by Cacay on 2/22/2018.
 */

public class PopSearchFilter extends AppCompatActivity {
    View view;
    Spinner spinner;
    String[] SPINNERVALUES = {"APARTMENT","BOARDING HOUSE","DORM"};
    String[] SPINNERVALUEPAYMENTAPART  = {"MONTH", "WEEK", "DAY"};
    String[] SPINNERVALUEPAYMENTBHDORM  = {"MONTH", "WEEK", "DAY", "MONTH PER ROOM", "WEEK PER ROOM", "DAY PER ROOM","MONTH PER HEAD", "WEEK PER HEAD", "DAY PER HEAD", "MONTH PER BED", "WEEK PER BED", "DAY PER BED"};
    String SpinnerValue;
    Button GOTO;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pop_search_filter);



        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int) (width * .8), (int) (height * .6));



        Button c = (Button) findViewById(R.id.btnSearchPopFilter);

        c.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                startActivity(new Intent(PopSearchFilter.this,SearchResultActivity.class));
            }


        });



        spinner =(Spinner)findViewById(R.id.poppaytype);



        ArrayAdapter<String> adapteras = new ArrayAdapter<String>(PopSearchFilter.this, android.R.layout.simple_list_item_1, SPINNERVALUEPAYMENTBHDORM);

        spinner.setAdapter(adapteras);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {

                SpinnerValue = (String)spinner.getSelectedItem();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub

            }
        });



        spinner =(Spinner)findViewById(R.id.popproptype);



        ArrayAdapter<String> adaptera = new ArrayAdapter<String>(PopSearchFilter.this, android.R.layout.simple_list_item_1, SPINNERVALUES);

        spinner.setAdapter(adaptera);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {

                SpinnerValue = (String)spinner.getSelectedItem();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub

            }
        });

//        GOTO.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                // TODO Auto-generated method stub
//
//                switch(SpinnerValue){
//
//                    case "APARTMENT":
//                        spinner =(Spinner)findViewById(R.id.poppaytype);
//                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(PopSearchFilter.this, android.R.layout.simple_list_item_1, SPINNERVALUEPAYMENTAPART);
//                        spinner.setAdapter(adapter);
//
//
//                        break;
//
//                    case "BOARDING HOUSE":
//                        spinner =(Spinner)findViewById(R.id.poppaytype);
//
//
//
//                        ArrayAdapter<String> adapters = new ArrayAdapter<String>(PopSearchFilter.this, android.R.layout.simple_list_item_1, SPINNERVALUEPAYMENTBHDORM);
//
//                        spinner.setAdapter(adapters);
//
//                        break;
//
//                    case "DORM":
//                        spinner =(Spinner)findViewById(R.id.poppaytype);
//
//
//
//                        ArrayAdapter<String> adapterss = new ArrayAdapter<String>(PopSearchFilter.this, android.R.layout.simple_list_item_1, SPINNERVALUEPAYMENTBHDORM);
//
//                        spinner.setAdapter(adapterss);
//
//
//                        break;
//
//
//                }
//            }
//        });
    }


}
