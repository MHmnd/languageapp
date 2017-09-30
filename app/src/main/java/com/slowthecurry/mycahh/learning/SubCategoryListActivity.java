package com.slowthecurry.mycahh.learning;

import android.app.AlertDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.transition.Slide;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class SubCategoryListActivity extends BaseActivity {
    private DatabaseReference databaseReference;
    private FirebaseDatabase firebaseDatabase;

    TextView headerText;
    RecyclerView entriesRecycler;
    LinearLayoutManager layoutManager;
    EntryAdapter entryAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Slide slide = new Slide(5);
        slide.setDuration(700);
        getWindow().setEnterTransition(slide);
        getWindow().setReturnTransition(slide);
        setContentView(R.layout.activity_sub_category_list);
        Intent launchingIntent = getIntent();
        initializeUI();
        String category;
        String header;
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();


        if (launchingIntent.hasExtra(Constants.SUB_CATEGORY_TITLE)
                && launchingIntent.hasExtra(Constants.CATEGORIES)) {
            header = launchingIntent.getStringExtra(Constants.SUB_CATEGORY_TITLE);
            category = launchingIntent.getStringExtra(Constants.CATEGORIES);
            headerText.setText(header);
        } else {
            header = "oops";
            category = "oops";
            headerText.setText(R.string.project_id);
        }

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();

        populateRecyclerView(header, category, user);
    }

    private void populateRecyclerView(final String currentSubCaterogy, final String mainCategory, final FirebaseUser user) {
        if (currentSubCaterogy.equals("oops")) {
            Toast.makeText(this, "Something went wrong, please go back and try again.", Toast.LENGTH_SHORT).show();
            return;
        }
        if (databaseReference.child(mainCategory).child(currentSubCaterogy).getRef() != null) {
            databaseReference.child(Constants.COLLECTION).child(user.getUid())
                    .addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    final ArrayList<String> collectionsTitles = new ArrayList<String>();
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        Collection currentCollection = snapshot.getValue(Collection.class);
                        collectionsTitles.add(currentCollection.getCollectionTitle());
                    }
                    Query entriesQuery = databaseReference.child(mainCategory)
                            .child(currentSubCaterogy)
                            .orderByChild(Constants.ORDER_NUMBER);
                    entriesQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            ArrayList<LanguageEntry> languageEntries = new ArrayList<LanguageEntry>();
                            for (DataSnapshot currentSnapshot : dataSnapshot.getChildren()) {
                                try {
                                    LanguageEntry disOne = currentSnapshot.getValue(LanguageEntry.class);
                                    languageEntries.add(disOne);
                                }catch (DatabaseException e){

                                }
                            }
                            layoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
                            entriesRecycler.setLayoutManager(layoutManager);
                            entryAdapter = new EntryAdapter(languageEntries, collectionsTitles, user.getUid(), SubCategoryListActivity.this);
                            entriesRecycler.setAdapter(entryAdapter);

                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

        }
    }

    private void initializeUI() {
        headerText = (TextView) findViewById(R.id.sub_category_list_header);
        entriesRecycler = (RecyclerView) findViewById(R.id.entries_recycler);
    }
}
