package com.slowthecurry.mycahh.learning;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.transition.Slide;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class CollectionListActivity extends BaseActivity {

    //UI components
    TextView titleText;
    RecyclerView collectionsRecycler;
    LinearLayoutManager layoutManager;

    //Firebase
    private FirebaseDatabase databaseCL;
    private DatabaseReference databaseReferenceCL;
    CollectionListAdapter collectionListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Slide slide = new Slide(5);
        slide.setDuration(700);
        getWindow().setEnterTransition(slide);
        getWindow().setReturnTransition(slide);
        setContentView(R.layout.activity_collections_list);
        titleText = (TextView) findViewById(R.id.collection_list_header);
        collectionsRecycler = (RecyclerView) findViewById(R.id.collection_list_recycler);

        databaseCL = FirebaseDatabase.getInstance();
        databaseReferenceCL = databaseCL.getReference();
        Intent callingIntent = getIntent();
        String userId = callingIntent.getStringExtra(Constants.USERS);
        String key = callingIntent.getStringExtra(Constants.COLLECTION);
        titleText.setText(callingIntent.getStringExtra("Title"));

        populateCollectionRecycler(userId, key);
    }


    private void populateCollectionRecycler(final String userIDR, String keyR) {
        databaseReferenceCL.child(Constants.COLLECTION).child(userIDR).child(keyR)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Collection currentCollection = dataSnapshot.getValue(Collection.class);
                        ArrayList collectionEntries = currentCollection.getLanguageEntryArrayList();

                        layoutManager = new LinearLayoutManager(getApplicationContext(),
                                LinearLayoutManager.VERTICAL,
                                false);
                        collectionsRecycler.setLayoutManager(layoutManager);
                        collectionListAdapter = new CollectionListAdapter(collectionEntries, userIDR,
                                CollectionListActivity.this);
                        collectionsRecycler.setAdapter(collectionListAdapter);

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }

                });
    }
}
