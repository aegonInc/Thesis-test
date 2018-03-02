package com.example.thesis;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.example.thesis.Model.User;
import com.example.thesis.fragment.MyPostsFragment;
import com.example.thesis.fragment.MyTopPostsFragment;
import com.example.thesis.fragment.RecentPostsFragment;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.OptionalPendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class NavDrawerAct extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, GoogleApiClient.OnConnectionFailedListener {

    private GoogleApiClient googleApiClient;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference mDatabase;
    StorageReference storageReference;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    private ImageView displayImageView;
    private TextView nameTextview, emailTextview, idTextview;
    private ProgressDialog mProgressDialog;

    FirebaseUser users = FirebaseAuth.getInstance().getCurrentUser();
    String imageuri;
    Fragment fragment = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nav_drawer);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    displaySelectedScreen(R.id.content_nav_drawer);
        View navHeaderView = navigationView.getHeaderView(0);

       displayImageView = (ImageView)navHeaderView.findViewById(R.id.navImage);
         nameTextview = (TextView)navHeaderView.findViewById(R.id.navName);
         emailTextview = (TextView)navHeaderView.findViewById(R.id.navEmail);
         idTextview = (TextView)navHeaderView.findViewById(R.id.navId);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        googleApiClient = new GoogleApiClient.Builder(getApplicationContext())
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (firebaseAuth.getCurrentUser() == null) {
                    startActivity(new Intent(NavDrawerAct.this, LogInActivity .class   ));
                }
            }
        };

    }
    protected void onStart() {

        super.onStart();
        final ProgressDialog dialog = new ProgressDialog(this);
        dialog.setTitle("Loading");
        dialog.setMessage("Please wait...");
        dialog.show();
        OptionalPendingResult<GoogleSignInResult> opr = Auth.GoogleSignInApi.silentSignIn(googleApiClient);
        if (opr.isDone()) {
            dialog.dismiss();
            GoogleSignInResult result = opr.get();
            handleSignInResult(result);
        }
        else {
            dialog.dismiss();
            opr.setResultCallback(new ResultCallback<GoogleSignInResult>() {
                @Override
                public void onResult(@NonNull GoogleSignInResult googleSignInResult) {
                    handleSignInResult(googleSignInResult);
//                    mAuth.signOut();
//                    goLogInScreen();
                }
            });
        }

    }

    private void handleSignInResult(GoogleSignInResult result) {
        final ProgressDialog dialog = new ProgressDialog(this);

        if (result.isSuccess()) {
            final GoogleSignInAccount account = result.getSignInAccount();

            nameTextview.setText(account.getDisplayName());
            emailTextview.setText(account.getEmail());
            idTextview.setText(account.getId());

            Glide.with(this).load(account.getPhotoUrl()).into(displayImageView);
            dialog.dismiss();

            mDatabase= database.getReference("users");
            mDatabase.addValueEventListener(new ValueEventListener() {

                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    if (!dataSnapshot.child(users.getUid()).exists())
                    {

                        User user = new User(nameTextview.getText().toString(), emailTextview.getText().toString());
                        mDatabase.child(users.getUid()).setValue(user);

                    }
                    else {
//
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }
        else
        {
            dialog.dismiss();
            Toast.makeText(getApplicationContext(), "Error Logging in", Toast.LENGTH_LONG).show();
            mAuth.signOut();
            goLogInScreen();
        }
    }


    private void goLogInScreen() {
        Intent intent  = new Intent(this,  LogInActivity.class );
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    public void logOut (View view) {
        mAuth.signOut();
        Auth.GoogleSignInApi.signOut(googleApiClient).setResultCallback(new ResultCallback<Status>() {
            @Override
            public void onResult(@NonNull Status status) {
                if (status.isSuccess()) {
                    goLogInScreen();

                }
                else {
                    Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public void revoke (View view) {
        final ProgressDialog dialog = new ProgressDialog(this);
        dialog.setTitle("Loading");
        dialog.setMessage("Please wait...");
        dialog.show();

        mAuth.signOut();
        Auth.GoogleSignInApi.revokeAccess(googleApiClient).setResultCallback(new ResultCallback<Status>() {
            @Override
            public void onResult(@NonNull Status status) {
                if (status.isSuccess()) {
                    dialog.dismiss();
                    goLogInScreen();

                }
                else {
                    dialog.dismiss();
                    Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.nav_drawer, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_logout) {
            startActivity(new Intent(NavDrawerAct.this,PopOU.class));

        }
        return super.onOptionsItemSelected(item);
    }
private void displaySelectedScreen(int id) {

    switch (id) {
        case R.id.nav_notifications:
            fragment = new NotificationsActivity();
            break;
        case R.id.nav_house:
           fragment = new MyPostsFragment();
            break;
        case R.id.nav_addhouse:
            fragment = new AddHouseActivity();
            break;
        case R.id.nav_recent:
            fragment = new RecentPostsFragment();
            break;
        case R.id.nav_top:
            fragment = new MyTopPostsFragment();
            break;
    }
    if (fragment != null) {
      FragmentTransaction ft = getFragmentManager().beginTransaction();
       ft.replace(R.id.content_nav_drawer, fragment);
        ft.commit();
    }


    DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
    drawer.closeDrawer(GravityCompat.START);
}

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.

        int id = item.getItemId();

        displaySelectedScreen(id);
        return true;
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
