package com.slowthecurry.mycahh.learning;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

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
    TextView basicsText;
    TextView preachingText;
    TextView teachingText;
    TextView otherText;

    RecyclerView basicsRecyclerView;
    RecyclerView preachingRecyclerView;
    RecyclerView teachingRecyclerView;
    RecyclerView otherRecyclerView;

    LinearLayoutManager linearLayoutManager;
    SubCategoriesAdapter basicsAdapter;
    SubCategoriesAdapter preachingAdapter;
    SubCategoriesAdapter teachingAdapter;
    SubCategoriesAdapter otherAdapter;


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

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();

        //build recyclerViews
        buildSubCategoriesGroup(getString(R.string.category_basics));
        buildSubCategoriesGroup(getString(R.string.category_preaching));
        buildSubCategoriesGroup(getString(R.string.category_teaching));
        buildSubCategoriesGroup(getString(R.string.category_other));
    }//end onCreate()

    private void initializeUI() {
        logOutButton = (Button) findViewById(R.id.log_out_button);
        basicsRecyclerView = (RecyclerView) findViewById(R.id.basics_recycler);
        preachingRecyclerView = (RecyclerView) findViewById(R.id.preaching_recycler);
        teachingRecyclerView = (RecyclerView) findViewById(R.id.teaching_recycler);
        otherRecyclerView = (RecyclerView) findViewById(R.id.other_recycler);
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
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();

    }//end onStart()

    @Override
    protected void onResume() {
        super.onResume();
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
    private ArrayList<String> buildSubCategoriesGroup(final String category){
        final ArrayList<String> catergoriesArraList = new ArrayList<String>();
        Query query = databaseReference.child(category);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String subCategory = snapshot.child(Constants.SUB_CATEGORY_TITLE).getValue(String.class);
                    catergoriesArraList.add(subCategory);
                }
                switch (category) {
                    case Constants.BASICS:
                        linearLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
                        basicsRecyclerView.setLayoutManager(linearLayoutManager);
                        basicsAdapter = new SubCategoriesAdapter(catergoriesArraList);
                        basicsRecyclerView.setAdapter(basicsAdapter);
                        break;
                    case Constants.PREACHING:
                        linearLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
                        preachingRecyclerView.setLayoutManager(linearLayoutManager);
                        preachingAdapter = new SubCategoriesAdapter(catergoriesArraList);
                        preachingRecyclerView.setAdapter(preachingAdapter);
                        break;
                    case Constants.TEACHING:
                        linearLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
                        teachingRecyclerView.setLayoutManager(linearLayoutManager);
                        teachingAdapter = new SubCategoriesAdapter(catergoriesArraList);
                        teachingRecyclerView.setAdapter(teachingAdapter);
                        break;
                    case Constants.OTHER:
                        linearLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
                        otherRecyclerView.setLayoutManager(linearLayoutManager);
                        otherAdapter = new SubCategoriesAdapter(catergoriesArraList);
                        otherRecyclerView.setAdapter(otherAdapter);
                        break;
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        return catergoriesArraList;
    }
    //end MainActivity
}
