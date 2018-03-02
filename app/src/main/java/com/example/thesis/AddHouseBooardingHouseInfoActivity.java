package com.example.thesis;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.example.thesis.Model.House;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static android.app.Activity.RESULT_OK;

public class AddHouseBooardingHouseInfoActivity extends Fragment {


    Button goPlace, btnAdd;
    int PLACE_PICKER_REQUEST =1    ;
    TextView tvPlace;
    TextView tvPlaces;
    EditText editText;

    TextView get_place_longitude;
    TextView get_place_latitude;
    TextView longitude, latitude;
    String choose = "None";

    String paymentType = "";
    EditText apartmentHouse, apartmentDesc, bhouseKitchen, bedNumber, apartmentPermit, apartmentRoom, apartmentBathroom, apartmentRules, apartmentPayment, apartmentRulePayment ;
    AutoCompleteTextView bhouseBarangay;
    Spinner apartmentPaymentSpinner;
    ImageView image, image2, image3, image4;

    CheckBox airCondition, bathroom, dangerous, weapons, kitchen, wifi, genderNeutral, boysOnly, girlsOnly, coveredParking, bike, dishwasher, camera, gate, doorman, noParking, pet, laundry, washer, security, trash, collector, noise;

    private final int PICK_IMAGE_REQUEST = 71;
    StorageReference storageReference;
    Uri imageUri1, imageUri2, imageUri3, imageUri4;
    public String mGroupId = "";
    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    final DatabaseReference table_house = database.getReference("house");

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    ArrayList<String> selection = new ArrayList<String>();


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Boarding House Info");
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_add_house_booarding_house_info, container, false);

        storageReference = FirebaseStorage.getInstance().getReference();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();

        tvPlace = (TextView) v.findViewById(R.id.tvPlaceBhouse);
        tvPlaces = (TextView)v. findViewById(R.id.tvPlacesBhouse);

        editText = (EditText) v.findViewById(R.id.txtBhouseDesc);
       editText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_MULTI_LINE);

        get_place_longitude = (TextView) v.findViewById(R.id.get_place_longitudeBhouse);
        get_place_latitude = (TextView)v. findViewById(R.id.get_place_latitudeBhouse);
        goPlace = (Button) v.findViewById(R.id.btnGoPlaceBhouse);

        goPlace.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                goPlacePicker(v);
            }


        });


        apartmentHouse = (EditText) v.findViewById(R.id.txtBhouseName);
        apartmentDesc = (EditText) v. findViewById(R.id.txtBhouseDesc);
        longitude = (TextView) v.findViewById(R.id.get_place_longitudeBhouse);
        latitude = (TextView) v.findViewById(R.id.get_place_latitudeBhouse);
        bhouseBarangay = (AutoCompleteTextView) v.findViewById(R.id.txtBhouseBarangay) ;
        apartmentPermit = (EditText) v. findViewById(R.id.txtBhousePermit);
        apartmentRoom = (EditText) v. findViewById(R.id.txtRoomBhouse);
        apartmentBathroom = (EditText) v. findViewById(R.id.txtBathroomBhouse);
        apartmentRules = (EditText) v. findViewById(R.id.idbhRulesBhouse);
        apartmentPayment = (EditText) v. findViewById(R.id.number_edit_text_Bhouse);
        apartmentRulePayment = (EditText) v. findViewById(R.id.idapartPaymentRulesBhouse);
        bhouseKitchen = (EditText) v. findViewById(R.id.txtKitchenBhouse);
        btnAdd = (Button) v.findViewById(R.id.btnSaveBhouse);

        image = (ImageView)v.findViewById(R.id.imgBhouseFirst);
        image2 = (ImageView)v.findViewById(R.id.imgBhouseSec);
        image3 = (ImageView)v.findViewById(R.id.imgBhouseThird);
        image4 = (ImageView)v.findViewById(R.id.imgBhouseFourth);

        apartmentPaymentSpinner = (Spinner) v.findViewById(R.id.type_spinner_Bhouse_pers);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(), R.array.apartment_per_payment, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        apartmentPaymentSpinner.setAdapter(adapter);
        apartmentPaymentSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {


            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long l) {
                paymentType = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                choose = "Image1";
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);

            }
        });
        image2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                choose = "Image2";
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);

            }
        });
        image3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                choose = "Image3";
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);

            }
        });
        image4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                choose = "Image4";
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);

            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveHouse(view);

            }
        });


        airCondition = (CheckBox) v.findViewById(R.id.chkAirConditioningBhouse);
        coveredParking = (CheckBox) v.findViewById(R.id.chkCoveredParkingBhouse);
        bike = (CheckBox) v.findViewById(R.id.chkBikeStorageLockersBhouse);
        dishwasher = (CheckBox) v.findViewById(R.id.chkDishwasherUnitBhouse);
        camera = (CheckBox) v.findViewById(R.id.chkSecurityCameraBhouse);
        gate = (CheckBox) v.findViewById(R.id.chkGatedAccessBhouse);
        doorman = (CheckBox) v.findViewById(R.id.chkDoormanBhouse);
        noParking = (CheckBox) v.findViewById(R.id.chkNoParkRuleBhouse);
        pet = (CheckBox) v.findViewById(R.id.chkPetFriendlyBhouse);
        laundry = (CheckBox) v.findViewById(R.id.chkSharedLaundryRoomBhouse);
        washer = (CheckBox) v.findViewById(R.id.chkWasherAndDryersUnitBhouse);
        security = (CheckBox) v.findViewById(R.id.chkSecurityGuardBhouse);
        trash = (CheckBox) v.findViewById(R.id.chkValetTrashBhouse);
        collector = (CheckBox) v.findViewById(R.id.chkDoorStepRecyclingCollectionBhouse);
        noise = (CheckBox) v.findViewById(R.id.chkPotenRuleApartRuleBhouse);

        wifi = (CheckBox) v.findViewById(R.id.chkWifiBhouse);
        genderNeutral = (CheckBox) v.findViewById(R.id.chkGenderNeutralBhouse);
        weapons = (CheckBox) v.findViewById(R.id.chkWeaponsApartRuleBhouse);
        dangerous = (CheckBox) v.findViewById(R.id.chkDangerousApartRuleBhouse);
        bathroom = (CheckBox) v.findViewById(R.id.chkSharedBathroomBhouse);
        boysOnly = (CheckBox) v.findViewById(R.id.chkBoysOnlyBhouse);
        girlsOnly = (CheckBox) v.findViewById(R.id.chkGirlsOnlyBhouse);

        bathroom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean checked = ((CheckBox) view).isChecked();
                if (checked) {
                    selection.add("Shared Bathroom");
                } else {
                    selection.remove("Shared Bathroom");
                }
            }
        });

        boysOnly.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean checked = ((CheckBox) view).isChecked();
                if (checked) {
                    selection.add("Male Boarders");
                } else {
                    selection.remove("Male Boarders");
                }
            }
        });
        girlsOnly.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean checked = ((CheckBox) view).isChecked();
                if (checked) {
                    selection.add("Female Boarders");
                } else {
                    selection.remove("Female Boarders");
                }
            }
        });

        weapons.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean checked = ((CheckBox) view).isChecked();
                if (checked) {
                    selection.add("Weapons on property");
                } else {
                    selection.remove("Weapons on property");
                }
            }
        });

        dangerous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean checked = ((CheckBox) view).isChecked();
                if (checked) {
                    selection.add("Dangerous Animals on property");
                } else {
                    selection.remove("Dangerous Animals on property");
                }
            }
        });

        wifi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean checked = ((CheckBox) view).isChecked();
                if (checked) {
                    selection.add("WIFI Connection");
                } else {
                    selection.remove("WIFI Connection");
                }
            }
        });

        genderNeutral.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean checked = ((CheckBox) view).isChecked();
                if (checked) {
                    selection.add("Male & Female Boarders");
                } else {
                    selection.remove("Male & Female Boarders");
                }
            }
        });

        airCondition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean checked = ((CheckBox) view).isChecked();
                if (checked) {
                    selection.add("Air Conditioning");
                } else {
                    selection.remove("Air Conditioning");
                }
            }
        });

        coveredParking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean checked = ((CheckBox) view).isChecked();
                if (checked) {
                    selection.add("Covered Parking");
                } else {
                    selection.remove("Covered Parking");
                }
            }
        });

        bike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean checked = ((CheckBox) view).isChecked();
                if (checked) {
                    selection.add("Bike Storage");
                } else {
                    selection.remove("Bike Storage");
                }
            }
        });

        dishwasher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean checked = ((CheckBox) view).isChecked();
                if (checked) {
                    selection.add("Dishwasher Unit");
                } else {
                    selection.remove("Dishwasher Unit");
                }
            }
        });
        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean checked = ((CheckBox) view).isChecked();
                if (checked) {
                    selection.add("Security Camera");
                } else {
                    selection.remove("Security Camera");
                }
            }
        });
        gate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean checked = ((CheckBox) view).isChecked();
                if (checked) {
                    selection.add("Gated Access");
                } else {
                    selection.remove("Gated Access");
                }
            }
        });
        doorman.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean checked = ((CheckBox) view).isChecked();
                if (checked) {
                    selection.add("Doorman");
                } else {
                    selection.remove("Doorman");
                }
            }
        });
        noParking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean checked = ((CheckBox) view).isChecked();
                if (checked) {
                    selection.add("No parking on property");
                } else {
                    selection.remove("No parking on property");
                }
            }
        });

        pet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean checked = ((CheckBox) view).isChecked();
                if (checked) {
                    selection.add("Pet Friendly");
                } else {
                    selection.remove("Pet Friendly");
                }
            }
        });
        laundry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean checked = ((CheckBox) view).isChecked();
                if (checked) {
                    selection.add("Shared Laundry Room");
                } else {
                    selection.remove("Shared Laundry Room");
                }
            }
        });
        washer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean checked = ((CheckBox) view).isChecked();
                if (checked) {
                    selection.add("Washer & Dryers in Unit");
                } else {
                    selection.remove("Washer & Dryers in Unit");
                }
            }
        });
        security.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean checked = ((CheckBox) view).isChecked();
                if (checked) {
                    selection.add("Security Guard");
                } else {
                    selection.remove("Security Guard");
                }
            }
        });
        trash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean checked = ((CheckBox) view).isChecked();
                if (checked) {
                    selection.add("Valet Trash");
                } else {
                    selection.remove("Valet Trash");
                }
            }
        });
        collector.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean checked = ((CheckBox) view).isChecked();
                if (checked) {
                    selection.add("Door Step Recycling Collection");
                } else {
                    selection.remove("Door Step Recycling Collection");
                }
            }
        });

        noise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean checked = ((CheckBox) view).isChecked();
                if (checked) {
                    selection.add("Potential for noise");
                } else {
                    selection.remove("Potential for noise");
                }
            }
        });

        return v;
    }

    public void saveHouse(View view) {

        btnAdd.setEnabled(false);
        if (imageUri1 == null) {
            imageUri1 =  Uri.parse("android.resource://com.example.thesis/" + R.drawable.uploadpic);
        }
        if (imageUri2 == null) {
            imageUri2 = Uri.parse("android.resource://com.example.thesis/" + R.drawable.uploadpic);
        }
        if (imageUri3 == null) {
            imageUri3 = Uri.parse("android.resource://com.example.thesis/" + R.drawable.uploadpic);
        }
        if (imageUri4 == null) {
            imageUri4 = Uri.parse("android.resource://com.example.thesis/" + R.drawable.uploadpic);
        }

        final ProgressDialog dialog = new ProgressDialog(getActivity());
        dialog.setTitle("Uploading image");
        dialog.show();

        String final_ameneties = "";
        for (String Selections : selection) {
            final_ameneties = final_ameneties + Selections + System.getProperty("line.separator");
        }

        mDatabase = FirebaseDatabase.getInstance().getReference();

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        mGroupId = table_house.push().getKey();
        House house = new House(user.getUid(), user.getEmail(), apartmentHouse.getText().toString(), "Boarding House", apartmentDesc.getText().toString(), latitude.getText().toString(), longitude.getText().toString(), bhouseBarangay.getText().toString(), apartmentPermit.getText().toString(), apartmentRoom.getText().toString(), apartmentBathroom.getText().toString(), final_ameneties, apartmentRules.getText().toString(), apartmentPayment.getText().toString(), paymentType, apartmentRulePayment.getText().toString(), bhouseKitchen.getText().toString(), "0" );
        Map<String, Object> postValues = house.toMap();
        Map<String, Object> childUpdates= new HashMap<>();
        childUpdates.put("/house/" + mGroupId, postValues);
        childUpdates.put("/user-house/" + user.getUid() + "/" + mGroupId, postValues);
        mDatabase.updateChildren(childUpdates);

       // table_house.child(user.getUid()).child(mGroupId).setValue(house);

        storageReference = FirebaseStorage.getInstance().getReference();
        //FIRST IMAGE
        StorageReference ref_child1 = storageReference.child( mGroupId + "/" +"image1.jpg");
        ref_child1.putFile(imageUri1)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                        dialog.dismiss();

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                        dialog.dismiss();
                        //Display err toast msg
                        Toast.makeText(getActivity().getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {

                    @Override
                    public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {

                        //Show upload progress
                        double progress = (100 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                        dialog.setMessage("Uploading first image" + (int) progress + "%");
                    }
                });

        storageReference = FirebaseStorage.getInstance().getReference();
        //SECOND IMAGE
        StorageReference ref_child2 = storageReference.child( mGroupId + "/" +"image2.jpg");
        ref_child2.putFile(imageUri2)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                        dialog.dismiss();

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                        dialog.dismiss();
                        //Display err toast msg
                        Toast.makeText(getActivity().getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {

                    @Override
                    public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {

                        //Show upload progress
                        double progress = (100 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                        dialog.setMessage("Uploading second image" + (int) progress + "%");
                    }
                });

        storageReference = FirebaseStorage.getInstance().getReference();
        //THIRD IMAGE
        StorageReference ref_child3 = storageReference.child( mGroupId + "/" +"image3.jpg");
        ref_child3.putFile(imageUri3)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                        dialog.dismiss();

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                        dialog.dismiss();
                        //Display err toast msg
                        Toast.makeText(getActivity().getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {

                    @Override
                    public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {

                        //Show upload progress
                        double progress = (100 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                        dialog.setMessage("Uploading third image" + (int) progress + "%");
                    }
                });

        storageReference = FirebaseStorage.getInstance().getReference();
        //FOURTH IMAGE
        StorageReference ref_child4 = storageReference.child( mGroupId + "/" +"image4.jpg");
        ref_child4.putFile(imageUri4)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                        dialog.dismiss();

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                        dialog.dismiss();
                        //Display err toast msg
                        Toast.makeText(getActivity().getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {

                    @Override
                    public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {

                        //Show upload progress
                        double progress = (100 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                        dialog.setMessage("Uploading image" + (int) progress + "%");
                        Toast.makeText(getActivity(), "Successfuly added!", Toast.LENGTH_LONG).show();
                    }
                });

    }

    public void goPlacePicker(View view){

        PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();

        try{
            choose = "Picker";
            startActivityForResult(builder.build(getActivity()),PLACE_PICKER_REQUEST);

        }catch (GooglePlayServicesRepairableException e){
            e.printStackTrace();

        } catch (GooglePlayServicesNotAvailableException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if (choose == "Picker") {

            if(requestCode == PLACE_PICKER_REQUEST){
                if(resultCode == RESULT_OK){
                    Place place = PlacePicker.getPlace(getActivity(), data);
                    Place places = PlacePicker.getPlace(data, getActivity());
                    String toastMsg = String.format("Place: %s", place.getName());

                    Toast.makeText(getActivity(), toastMsg, Toast.LENGTH_LONG).show();


                    Double latitude = place.getLatLng().latitude;
                    Double longitude = place.getLatLng().longitude;

                    String lat = String.valueOf(latitude);
                    String longi = String.valueOf(longitude);


                    tvPlace.setText(places.getAddress());
                    tvPlaces.setText(place.getName());

                    get_place_latitude.setText(lat);
                    get_place_longitude.setText(longi);


                }
            }
        }
        else if (choose == "Image1") {
            if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null &&data.getData() != null) {
                imageUri1 = data.getData();
                try {
                    InputStream is = getActivity().getContentResolver().openInputStream(imageUri1);
                    Bitmap bitmap = BitmapFactory.decodeStream(is);
                    image.setImageBitmap(bitmap);
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        else if (choose == "Image2") {
            if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null &&data.getData() != null) {
                imageUri2 = data.getData();
                try {
                    InputStream is = getActivity().getContentResolver().openInputStream(imageUri2);
                    Bitmap selected_image = BitmapFactory.decodeStream(is);
                    image2.setImageBitmap(selected_image);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        else if (choose == "Image3") {
            if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null &&data.getData() != null) {
                imageUri3 = data.getData();
                try {
                    InputStream is = getActivity().getContentResolver().openInputStream(imageUri3);
                    Bitmap selected_image = BitmapFactory.decodeStream(is);
                    image3.setImageBitmap(selected_image);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        else if (choose == "Image4") {
            if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null &&data.getData() != null) {
                imageUri4 = data.getData();
                try {
                    InputStream is = getActivity().getContentResolver().openInputStream(imageUri4);
                    Bitmap selected_image = BitmapFactory.decodeStream(is);
                    image4.setImageBitmap(selected_image);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }
    public String getUid() { return FirebaseAuth.getInstance().getCurrentUser().getUid(); }
}
