package com.slowthecurry.mycahh.learning;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class CollectionListActivity extends AppCompatActivity {

    //UI components
    TextView titleText;
    RecyclerView collectionsRecycler;
    LinearLayoutCompat layoutManager;

    //Firebase
    FirebaseDatabase database;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collection_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        titleText = (TextView) findViewById(R.id.collection_list_header);
        collectionsRecycler = (RecyclerView) findViewById(R.id.collection_list_recycler);


        Intent callingIntent = getIntent();
        String userId = callingIntent.getStringExtra(Constants.USERS);
        String key = callingIntent.getStringExtra(Constants.COLLECTION);
        this.setTitle(callingIntent.getStringExtra("Title"));

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


    }


    private void populateCollectionRecycler(String userIDR, String keyR) {
        databaseReference.child(Constants.COLLECTION).child(userIDR).child(keyR)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        final ArrayList<LanguageEntry> collectionEntries = new ArrayList<LanguageEntry>();
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            LanguageEntry entry = snapshot.getValue(LanguageEntry.class);
                            collectionEntries.add(entry);
                        }
//
//                        layoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
//                        collectionsRecycler.setLayoutManager(layoutManager);
//                        cole = new EntryAdapter(languageEntries, collectionsTitles, user.getUid(), SubCategoryListActivity.this);
//                        entriesRecycler.setAdapter(entryAdapter);

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }

                });
    }
}
