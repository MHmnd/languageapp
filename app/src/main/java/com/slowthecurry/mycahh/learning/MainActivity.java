package com.slowthecurry.mycahh.learning;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener {
    private final String DEBUG_TAG = "MAIN ACTIVITY";
    private Context context;

    //Firebabse declarations
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener fbAuthStateListener;
    private DatabaseReference databaseReference;
    private FirebaseDatabase firebaseDatabase;

    //UI components
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
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
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

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();

        //build recyclerViews
        buildSubCategoriesGroup(getString(R.string.category_basics));
        buildSubCategoriesGroup(getString(R.string.category_preaching));
        buildSubCategoriesGroup(getString(R.string.category_teaching));
        buildSubCategoriesGroup(getString(R.string.category_other));

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.main_drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.main_nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View navigationHeader = navigationView.getHeaderView(0);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        TextView navigationName = (TextView) navigationHeader.findViewById(R.id.nav_name);
        TextView navigationEmail = (TextView) navigationHeader.findViewById(R.id.nav_email);

        if (user != null) {
            for (UserInfo profile : user.getProviderData()) {
                // Id of the provider (ex: google.com)
                String providerId = profile.getProviderId();

                // UID specific to the provider
                String uid = profile.getUid();

                // Name, email address, and profile photo Url
                String name = profile.getDisplayName();
                String email = profile.getEmail();
                Uri photoUrl = profile.getPhotoUrl();
                navigationName.setText(name);
                navigationEmail.setText(email);
            };
        }


    }//end onCreate()

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.main_drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    private void initializeUI() {
        basicsRecyclerView = (RecyclerView) findViewById(R.id.basics_recycler);
        preachingRecyclerView = (RecyclerView) findViewById(R.id.preaching_recycler);
        teachingRecyclerView = (RecyclerView) findViewById(R.id.teaching_recycler);
        otherRecyclerView = (RecyclerView) findViewById(R.id.other_recycler);
    }//end initializeUI()
    //

    private void addButton(final FirebaseUser user){
        final FloatingActionButton addStuffButton = (FloatingActionButton) findViewById(R.id.main_fab);
        Query adminQuery = databaseReference.child("ADMIN");
        adminQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
               try{
                String currentUID = user.getUid();
                Boolean isUserAdmin = false;

                for(DataSnapshot adminUsers : dataSnapshot.getChildren()) {
                    if (currentUID.equals(adminUsers.getValue(String.class))) {
                        isUserAdmin = true;
                    }

                    if (isUserAdmin) {
                        addStuffButton.setVisibility(View.VISIBLE);

                        addStuffButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent addEntry = new Intent(getApplicationContext(), AddEntry.class);
                                startActivity(addEntry);
                            }
                        });
                    } else {
                        addStuffButton.setVisibility(View.GONE);
                    }
                }
                    }catch (NullPointerException e){
                        Log.d(DEBUG_TAG, "UID NULL");
                    }

                }


            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
    @Override
    protected void onStart() {
        super.onStart();
        firebaseAuth.addAuthStateListener(fbAuthStateListener);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();

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
        firebaseAuth.signOut();
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
                        basicsAdapter = new SubCategoriesAdapter(catergoriesArraList, category, MainActivity.this);
                        basicsRecyclerView.setAdapter(basicsAdapter);
                        break;
                    case Constants.PREACHING:
                        linearLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
                        preachingRecyclerView.setLayoutManager(linearLayoutManager);
                        preachingAdapter = new SubCategoriesAdapter(catergoriesArraList, category, MainActivity.this);
                        preachingRecyclerView.setAdapter(preachingAdapter);
                        break;
                    case Constants.TEACHING:
                        linearLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
                        teachingRecyclerView.setLayoutManager(linearLayoutManager);
                        teachingAdapter = new SubCategoriesAdapter(catergoriesArraList, category, MainActivity.this);
                        teachingRecyclerView.setAdapter(teachingAdapter);
                        break;
                    case Constants.OTHER:
                        linearLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
                        otherRecyclerView.setLayoutManager(linearLayoutManager);
                        otherAdapter = new SubCategoriesAdapter(catergoriesArraList, category, MainActivity.this);
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

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
          int id = item.getItemId();

           switch (id){
               case R.id.nav_home:
                   Intent goHome = new Intent(this, MainActivity.class);
                   startActivity(goHome);
                   break;
               case R.id.nav_tutorials:
                   Intent startTutorials = new Intent(this, TutorialsActivity.class);
                   startActivity(startTutorials);
                   break;
               case R.id.nav_collections:
                   Intent startCollections = new Intent(this, CollectionsActivity.class);
                   startActivity(startCollections);
                   break;
               case R.id.nav_log_out:
                   Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(new ResultCallback<Status>() {
                       @Override
                       public void onResult(@NonNull Status status) {
                           firebaseAuth.signOut();
                       }
                   });
                   break;
               case R.id.nav_share:
                   Toast.makeText(this, "Sharing coming soon", Toast.LENGTH_SHORT).show();
                   break;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.main_drawer_layout);
        drawer.closeDrawer(GravityCompat.START);

            return true;
    }
    //end MainActivity
}
