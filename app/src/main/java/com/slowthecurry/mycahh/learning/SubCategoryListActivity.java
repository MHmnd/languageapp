package com.slowthecurry.mycahh.learning;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ArrayAdapter;
import android.widget.TextView;

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
        setContentView(R.layout.activity_sub_category_list);
        Intent launchingIntent = getIntent();
        initializeUI();
        String category;
        String header;

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

        populateRecyclerView(header, category);
    }

    private void populateRecyclerView(String currentSubCaterogy, String mainCategory) {
        if (currentSubCaterogy.equals("oops")) {
            return;
        }
        if (databaseReference.child(mainCategory).child(currentSubCaterogy).getRef() != null) {
            Query entriesQuery = databaseReference.child(mainCategory).child(currentSubCaterogy).orderByChild(Constants.ORDER_NUMBER);
                entriesQuery.addValueEventListener(new ValueEventListener() {
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
                        entryAdapter = new EntryAdapter(languageEntries);
                        entriesRecycler.setAdapter(entryAdapter);
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
