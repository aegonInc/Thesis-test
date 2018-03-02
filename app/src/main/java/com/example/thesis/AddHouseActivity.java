package com.example.thesis;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.EditText;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.AdapterView;



import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;

import java.util.ArrayList;

public class AddHouseActivity extends Fragment {

    Spinner spinner;
    String[] SPINNERVALUES = {"APARTMENT","BOARDING HOUSE","DORM"};
    String SpinnerValue;
    Button GOTO;
    Intent intent;

//
//    int PLACE_PICKER_REQUEST = 1    ;
//    TextView tvPlace;
//    TextView tvPlaces;


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("House Profile");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_add_house, container, false);
//
//        DisplayMetrics dm = new DisplayMetrics();
//        getWindowManager().getDefaultDisplay().getMetrics(dm);
//
//        int width = dm.widthPixels;
//        int height = dm.heightPixels;
//
//        getWindow().setLayout((int) (width * .8), (int) (height * .4));



        GOTO = (Button) v.findViewById(R.id.btnNextAddApart);

        spinner = (Spinner)v. findViewById(R.id.spinner);


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, SPINNERVALUES);

        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {

                SpinnerValue = (String) spinner.getSelectedItem();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub

            }
        });

        GOTO.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                switch (SpinnerValue) {

                    case "APARTMENT":
//                        intent = new Intent(AddHouseActivity.this, AddHouseApartmentInfo.class);
//                        startActivity(intent);

                        FragmentTransaction ft = getFragmentManager().beginTransaction();
                        ft.replace(R.id.content_nav_drawer, new AddHouseApartmentInfo());
                        ft.commit();

                        break;

                    case "BOARDING HOUSE":
//                        intent = new Intent(AddHouseActivity.this, AddHouseBooardingHouseInfoActivity.class);
//                        startActivity(intent);

                        FragmentTransaction ft2 = getFragmentManager().beginTransaction();
                        ft2.replace(R.id.content_nav_drawer, new AddHouseBooardingHouseInfoActivity());
                        ft2.commit();
                        break;

                    case "DORM":
//                        intent = new Intent(AddHouseActivity.this, AddHouseDormInfoActivity.class);
//                        startActivity(intent);
                        FragmentTransaction ft3 = getFragmentManager().beginTransaction();
                        ft3.replace(R.id.content_nav_drawer, new AddHouseDormInfoActivity());
                        ft3.commit();
                        break;

                }
            }
        });
        return v;
    }



}
