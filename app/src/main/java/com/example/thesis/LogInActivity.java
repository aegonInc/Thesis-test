package com.example.thesis;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.thesis.Model.House;
import com.example.thesis.viewholder.PostViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import org.w3c.dom.Text;

public class LogInActivity extends BaseActivity {
  //Declare view to access the view


     private EditText mSearchField;
     private Button mSearchBtn;
     private RecyclerView mResultList;

     private DatabaseReference mHouseDatabase;

    private SignInButton signInButton;
    ProgressDialog ProgressDialog;
    private static final String TAG = "MAIN_ACTIVITY";
    private static final int RC_SIGN_IN = 9001; //Request code for signing in

    private static final int SIGN_IN_CODE = 9001;
    private GoogleApiClient googleApiClient;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

  View view;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        mHouseDatabase = FirebaseDatabase.getInstance().getReference("House");
//
//        mSearchField = (EditText)findViewById(R.id.search_field);
//        mSearchBtn = (Button)findViewById(R.id.btnSearch);
//        mResultList = (RecyclerView)findViewById(R.id.result_list);
//        mResultList.setHasFixedSize(true);
//        mResultList.setLayoutManager(new LinearLayoutManager(this));
//
//        mSearchBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                String searchText = mSearchField.getText().toString();
//               firebaseHouseSearch(searchText);
//            }
//        });
//
//

        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (firebaseAuth.getCurrentUser() != null) {

                    startActivity(new Intent(LogInActivity.this, NavDrawerAct.class));
                }
            }
        };

        signInButton = (SignInButton) findViewById(R.id.btnGoogleNew);
        TextView textView = (TextView) signInButton.getChildAt(0);
        textView.setText("Sign in with Google");
        signInButton.setSize(SignInButton.SIZE_STANDARD);
        signInButton.setColorScheme(SignInButton.COLOR_DARK);
        // Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        googleApiClient = new GoogleApiClient.Builder(getApplicationContext())
                .enableAutoManage(this, new GoogleApiClient.OnConnectionFailedListener() {
                    @Override
                    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
                        Toast.makeText(LogInActivity.this, "Error", Toast.LENGTH_LONG).show();
                    }
                })
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();


        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signIn();
            }
        });

//
//        Button c = (Button) findViewById(R.id.btnSearchFilter);
//        c.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(LogInActivity.this, PopSearchFilter.class));
//            }
//
//
//        });


        //Declare and Initialize button and access it via findViewById


        Button btnSearch = (Button) findViewById(R.id.btnSearch);
        btnSearch.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Search(v);
            }
        });

    }
//
//    private void firebaseHouseSearch(String searchText) {
//
//        Toast.makeText(LogInActivity.this, "Started Search", Toast.LENGTH_LONG).show();
//
//        Query firebaseSearchQuery = mHouseDatabase.orderByChild("addressBarangay").startAt(searchText).endAt(searchText + "\uf8ff");
//
//        FirebaseRecyclerAdapter<House, HouseViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<House, HouseViewHolder>(
//                House.class,
//                R.layout.list_layout_search,
//                HouseViewHolder.class,
//                firebaseSearchQuery
//
//        ) {
//            @Override
//            protected void onBindViewHolder(final HouseViewHolder holder, int position, House model) {
//
//
//                holder.setDetails(model.getHousename(), model.getPayment(), model.getAddressBarangay());
//
//            }
//
//
//        };
//
//        mResultList.setAdapter(firebaseRecyclerAdapter);
//
//
//    }
//
//
//    public class HouseViewHolder extends RecyclerView.ViewHolder{
//
//        View mView;
//        public  HouseViewHolder(View itemView) {
//            super   (itemView);
//            mView = itemView;
//        }
//
//        public  void setDetails(String uHouse, String uPayment, String uAddress){
//
//            TextView u_house = (TextView) mView.findViewById(R.id.house_name);
//            TextView u_price = (TextView) mView.findViewById(R.id.house_price);
//            TextView u_address = (TextView) mView.findViewById(R.id.house_address);
//
//            u_house.setText(uHouse);
//            u_price.setText(uPayment);
//            u_address.setText(uAddress);
//        }
//    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        showProgressDialog();
        if (requestCode == SIGN_IN_CODE) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if (result.isSuccess()) {
                GoogleSignInAccount account =  result.getSignInAccount();
                firebaseAuthWithGoogle(account);
            }
            else {

                hideProgressDialog();
            }
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount account) {
        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "signInWithCredential:onComplete" + task.isSuccessful());

                        if (!task.isSuccessful()) {
                            hideProgressDialog();
                            Log.w(TAG, "signInWithCredential" + task.getException());
                            Toast.makeText(LogInActivity .this, "Authentication Failed", Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }


    public void Search(View view) {
        //use intent get permission to access the another screen
        Intent i = new Intent(this, SearchResultActivity.class);
        startActivity(i);

    }

    private void signIn() {
        signInButton.setEnabled(false);
        Intent intent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
        startActivityForResult(intent, SIGN_IN_CODE);
    }

    public void SignUp(View view) {
        //use intent get permission to access the another screen
        Intent i = new Intent(this, SignupActivity.class);
        startActivity(i);

    }
}
