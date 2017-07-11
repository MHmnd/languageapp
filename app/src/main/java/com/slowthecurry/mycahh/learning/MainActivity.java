package com.slowthecurry.mycahh.learning;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class MainActivity extends BaseActivity {
    private final String DEBUG_TAG = "MAIN ACTIVITY";
    private Context context;

    //Firebabse declarations
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener fbAuthStateListener;
    private DatabaseReference databaseReference;
    private FirebaseDatabase firebaseDatabase;

    //UI components
    Button logOutButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this.getApplicationContext();
        initializeUI();

        //Authentication logic
        firebaseAuth = FirebaseAuth.getInstance();
        fbAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser currentUser = firebaseAuth.getCurrentUser();

                if (currentUser != null) {
                    //currentUser is signed in
                    Log.d(DEBUG_TAG, currentUser.getProviderId());
                    addButton(currentUser);
                } else {
                    //Nobody home y'all!
                    Log.d(DEBUG_TAG, "NOT SIGNED IN YO!!!!");
                    //if not signed in launches LoginActivity to sign in user

                    Intent loginActivityIntent = new Intent(context, LoginActivity.class);
                    startActivity(loginActivityIntent);
                }
            }//end @Override onAuthStateChanged()

        };//end fbAuthStateListener instantiation

        logOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(new ResultCallback<Status>() {
                    @Override
                    public void onResult(@NonNull Status status) {
                        firebaseAuth.signOut();
                    }
                });
                Log.d(DEBUG_TAG, "log out pressed");
            }
        });

        //Firebase Database logic
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference(Constants.CATEGORIES);

    }//end onCreate()

    private void initializeUI() {
        logOutButton = (Button) findViewById(R.id.log_out_button);

    }//end initializeUI()

    private void addButton(FirebaseUser user){
        Button addStuffButton = (Button) findViewById(R.id.add_item_button);
        try {
            if (user.getUid().equals(Constants.ADMIN_ID)) {
                addStuffButton.setVisibility(View.VISIBLE);

                addStuffButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent addEntry = new Intent(getApplicationContext(), AddEntry.class);
                        startActivity(addEntry);
                    }
                });
            }else {
                addStuffButton.setVisibility(View.GONE);
            }
        }catch (NullPointerException e){
            Log.d(DEBUG_TAG, "UID NULL");
        }

    }
    @Override
    protected void onStart() {
        super.onStart();
        firebaseAuth.addAuthStateListener(fbAuthStateListener);
    }//end onStart()

    @Override
    protected void onPostResume() {
        super.onPostResume();
        FirebaseUser user = firebaseAuth.getCurrentUser();
        addButton(user);
    }

    @Override
    protected void onStop() {
        super.onStop();
        firebaseAuth.removeAuthStateListener(fbAuthStateListener);
    }//end onStop()


    /**
     * Pulls a list of the categories and their sub categories and builds an ArrayList to
     * be used to populate the LanguageEntry list.
     */
    private ArrayList<String> buildCategories(){
        ArrayList<String> catergoriesArraList = new ArrayList<String>();


        return catergoriesArraList;
    }
    //end MainActivity
}
