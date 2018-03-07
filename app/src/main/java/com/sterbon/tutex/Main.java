package com.sterbon.tutex;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.ads.AdView;
import com.google.android.gms.common.api.GoogleApiClient;

/**
 * Created by Utsav on 7/30/2017.
 */


public class Main extends AppCompatActivity
{

    Toolbar mtoolbar;
    Toolbar navdrawer;
    AdView mAdView;
    String personName;
    ProgressDialog mProgressDialog;
    ActionBarDrawerToggle mtoggle;
    DrawerLayout mDrawer;
    BottomNavigationView navigation;
    //Authentication mAuthentication;
    Uri personPhoto;
    Button signInButton;
    Button signOutButton;
    private static final String TAG = "SignInActivity";
    private static final int RC_SIGN_IN = 9001;
    private GoogleApiClient mGoogleApiClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mtoolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(mtoolbar);

        navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        changeFragment(0);

        /*GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this , this )
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

         signInButton = (Button) findViewById(R.id.login);
         signOutButton = (Button) findViewById(R.id.logout);
    */
    }
/*
    private void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
        findViewById(R.id.logout).setVisibility(View.VISIBLE);

    }
public void signOut(){
        Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                new ResultCallback<Status>() {
                    @Override
                    public void onResult(Status status) {
                        updateUI(false);

                    }
                });
    }
*/
    BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            switch (item.getItemId()) {
                case R.id.navigation_tut:
                    changeFragment(0);
                    return true;

                case R.id.navigation_prac:
                    changeFragment(1);
                    return true;

                case R.id.navigation_notes:
                    changeFragment(2);
                    return true;

            }
            return false;
        }


    };


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater minfater = getMenuInflater();
        minfater.inflate(R.menu.nav_drawer, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected (MenuItem item) {
        switch (item.getItemId()) {
            case R.id.share:
                Intent shareintent = new Intent();
                shareintent.setAction(Intent.ACTION_SEND);
                shareintent.putExtra(Intent.EXTRA_TEXT, "https://play.google.com/store/apps/details?id=com.sterbon.tutex");
                shareintent.setType("text/plain");
                startActivity(shareintent);
                return true;

            case R.id.contact:
                Intent contactintent = new Intent();
                contactintent.setAction(Intent.ACTION_SEND);
                contactintent.putExtra(Intent.EXTRA_EMAIL, new String[]{"talwar.utsav@gmail.com"});
                contactintent.putExtra(Intent.EXTRA_SUBJECT, "");
                contactintent.putExtra(Intent.EXTRA_TEXT, "");
                contactintent.setType("message/rfc822");
                startActivity(Intent.createChooser(contactintent, "Choose an Email client :"));
                return true;

            case R.id.rate:
                startActivity(new Intent(Intent.ACTION_VIEW,Uri.parse("http://play.google.com/store/apps/details?id=com.sterbon.tutex")));
                return true;

            case R.id.dev:
                Intent intent = new Intent(Main.this,Credits.class);
                startActivity(intent);
                return true;

         //   case R.id.login:
           //     signIn();
             //   return true;

            /*case R.id.logout:
                signOut();
                return true;
        */}

        return true;
    }


    private void changeFragment(int position) {

        Fragment newFragment = null;

        if (position == 0) {
            newFragment = new T_Frag();

        } else if (position % 2 != 0) {
            newFragment = new P_Frag();
        } else {
            newFragment = new N_Frag();
        }

        getFragmentManager().beginTransaction().replace(R.id.content,newFragment).commit();


    }

    boolean doubleBackToExitPressedOnce = false;

    @Override
        public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }

 /*   @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Toast.makeText(this,"ERROR Connecting",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onStart() {
        super.onStart();

        OptionalPendingResult<GoogleSignInResult> opr = Auth.GoogleSignInApi.silentSignIn(mGoogleApiClient);
        if (opr.isDone()) {
            Log.d(TAG, "Got cached sign-in");
            GoogleSignInResult result = opr.get();
            handleSignInResult(result);
        } else {
            showProgressDialog();
            opr.setResultCallback(new ResultCallback<GoogleSignInResult>() {
                @Override
                public void onResult(GoogleSignInResult googleSignInResult) {
                    hideProgressDialog();
                    handleSignInResult(googleSignInResult);
                }
            });
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        hideProgressDialog();
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }
    }


    private void handleSignInResult(GoogleSignInResult result) {
        Log.d(TAG, "handleSignInResult:" + result.isSuccess());
        if (result.isSuccess()) {
            GoogleSignInAccount acct = result.getSignInAccount();
            personName = acct.getDisplayName();
            personPhoto = acct.getPhotoUrl();
            updateUI(true);
        } else {

            updateUI(false);
        }
    }

    private void showProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setMessage("Loading");
            mProgressDialog.setIndeterminate(true);
        }

        mProgressDialog.show();
    }

    private void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.hide();
        }
    }


    private void updateUI(boolean signedIn) {
        if (signedIn) {

            Snackbar snackbar = Snackbar.make(findViewById(R.id.myLayout), "Welcome "+personName+" !"
                    , Snackbar.LENGTH_LONG);

            snackbar.show();

        } else {
            Snackbar snackbar = Snackbar.make(findViewById(R.id.myLayout), "Logged out!"
                    , Snackbar.LENGTH_LONG);

            snackbar.show();
            findViewById(R.id.logout).setVisibility(View.GONE);
            findViewById(R.id.login).setVisibility(View.VISIBLE);


        }
    }
    */
}
