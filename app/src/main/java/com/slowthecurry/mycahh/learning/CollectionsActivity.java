package com.slowthecurry.mycahh.learning;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

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

public class CollectionsActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    FirebaseAuth firebaseAuth;
    FirebaseDatabase collectionsDatabase;
    DatabaseReference collectionsReference;

    RecyclerView.LayoutManager layoutManager;
    RecyclerView titlesRecycler;
    CollectionAdapter collectionAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collections);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        titlesRecycler = (RecyclerView) findViewById(R.id.collections_title_recycler);

        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser user = firebaseAuth.getCurrentUser();
        try {
            final String userID = user.getUid();
            collectionsDatabase = FirebaseDatabase.getInstance();
            collectionsReference = collectionsDatabase.getReference()
                    .child(Constants.COLLECTION)
                    .child(userID);
            //TODO: PULL COLLECTIONS FROM FIREBASE TO GO TO LIST. NEED TO BUILD RV COMPONENTS
            collectionsReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    ArrayList<Collection> collectionArrayList = new ArrayList<Collection>();
                    for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                        Collection collectionToAdd = snapshot.getValue(Collection.class);
                        collectionArrayList.add(collectionToAdd);
                    }
                    layoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
                    titlesRecycler.setLayoutManager(layoutManager);
                    collectionAdapter = new CollectionAdapter(collectionArrayList, userID, CollectionsActivity.this);
                    titlesRecycler.setAdapter(collectionAdapter);

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }catch (NullPointerException e){
            Toast.makeText(this, "Something went wrong, please go back and try again", Toast.LENGTH_LONG).show();
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.collections_drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.collections_nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.collections_drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.collections, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();


        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        switch (id) {
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
            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.collections_drawer_layout);
            drawer.closeDrawer(GravityCompat.START);
            return true;
        }
    }

