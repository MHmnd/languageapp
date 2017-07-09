package com.slowthecurry.mycahh.learning;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddEntry extends BaseActivity
        implements AdapterView.OnItemSelectedListener{
    private Categories categories;
    private Categories.subCategories subCategories;

    //UI elements
    private Spinner categoriesSpinner;
    private Spinner subCategoriesSpinner;
    private EditText englishEntry;
    private EditText tonganEntry;

    //Firebase
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_entry);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();


        categories = new Categories();
        subCategories = categories.new subCategories();
        categoriesSpinner = (Spinner) findViewById(R.id.categories_spinner);
        subCategoriesSpinner = (Spinner) findViewById(R.id.sub_categories_spinner);
        categoriesSpinner.setOnItemSelectedListener(this);
        subCategoriesSpinner.setOnItemSelectedListener(this);
        final ArrayAdapter<CharSequence> categoryAdapter =
                ArrayAdapter.createFromResource(this, R.array.categories_array, android.R.layout.simple_spinner_item);
        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categoriesSpinner.setAdapter(categoryAdapter);

        englishEntry = (EditText) findViewById(R.id.add_english);
        tonganEntry = (EditText) findViewById(R.id.add_tongan);



        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(englishEntry.getText() != null && tonganEntry.getText() != null){
                    Categories.subCategories.entrie entrie = subCategories.new entrie();
                    entrie.setEnglish(englishEntry.getText().toString());
                    entrie.setTongan(tonganEntry.getText().toString());
                    String key = databaseReference.child(categories.getTitle()).child(subCategories.getSubTitle()).push().getKey();
                    entrie.setKey(key);
                    databaseReference.child(categories.getTitle()).child(subCategories.getSubTitle()).child(key).setValue(entrie);
                }
                Log.d("ADD ENTRY", categories.getTitle() + " " + subCategories.getSubTitle());
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()){
            case R.id.categories_spinner:
            categories.setTitle(parent.getItemAtPosition(position).toString());
            Log.d("ADD ENTRY", categories.getTitle());
            ArrayAdapter<CharSequence> subCategoriesAdapter =
                    ArrayAdapter.createFromResource(this, R.array.basics_array, android.R.layout.simple_spinner_item);

            if (categories.getTitle() != null) {
                if (categories.getTitle().equals("Basics")) {
                    subCategoriesAdapter =
                            ArrayAdapter.createFromResource(this, R.array.basics_array, android.R.layout.simple_spinner_item);
                    subCategoriesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    subCategoriesSpinner.setAdapter(subCategoriesAdapter);
                } else if (categories.getTitle().equals("Preaching Skills")) {
                    subCategoriesAdapter =
                            ArrayAdapter.createFromResource(this, R.array.preaching_array, android.R.layout.simple_spinner_item);
                    subCategoriesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    subCategoriesSpinner.setAdapter(subCategoriesAdapter);
                } else if (categories.getTitle().equals("Teaching Skills")) {
                    subCategoriesAdapter =
                            ArrayAdapter.createFromResource(this, R.array.teaching_array, android.R.layout.simple_spinner_item);
                    subCategoriesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    subCategoriesSpinner.setAdapter(subCategoriesAdapter);
                } else if (categories.getTitle().equals("Other")) {
                    subCategoriesAdapter =
                            ArrayAdapter.createFromResource(this, R.array.other_array, android.R.layout.simple_spinner_item);
                    subCategoriesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    subCategoriesSpinner.setAdapter(subCategoriesAdapter);
                }
            }
            subCategoriesAdapter.notifyDataSetChanged();
                break;
            case R.id.sub_categories_spinner:
            Log.d("SUB CATEGORY", parent.getItemAtPosition(position).toString());
            subCategories.setSubTitle(parent.getItemAtPosition(position).toString());
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
