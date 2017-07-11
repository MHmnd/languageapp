package com.slowthecurry.mycahh.learning;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddEntry extends BaseActivity
        implements AdapterView.OnItemSelectedListener {
    private String category;
    private String subCategory;
    private LanguageEntry languageEntry;

    //UI elements
    private Spinner categoriesSpinner;
    private Spinner subCategoriesSpinner;
    private EditText english;
    private EditText tongan;

    //Firebase
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_entry);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        languageEntry = new LanguageEntry();

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();

        categoriesSpinner = (Spinner) findViewById(R.id.categories_spinner);
        subCategoriesSpinner = (Spinner) findViewById(R.id.sub_categories_spinner);
        categoriesSpinner.setOnItemSelectedListener(this);
        subCategoriesSpinner.setOnItemSelectedListener(this);
        final ArrayAdapter<CharSequence> categoryAdapter =
                ArrayAdapter.createFromResource(this, R.array.categories_array, android.R.layout.simple_spinner_item);
        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categoriesSpinner.setAdapter(categoryAdapter);

        english = (EditText) findViewById(R.id.add_english);
        tongan = (EditText) findViewById(R.id.add_tongan);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (english.getText() != null && tongan.getText() != null) {
                    languageEntry.setEnglish(english.getText().toString());
                    languageEntry.setTongan(tongan.getText().toString());
                    String key = databaseReference.child(category)
                            .child(subCategory)
                            .push()
                            .getKey();
                    languageEntry.setKey(key);
                    databaseReference.child(category)
                            .child(subCategory)
                            .child(key)
                            .setValue(languageEntry);
                    Toast.makeText(context, languageEntry.getEnglish() + " was added.",
                            Toast.LENGTH_SHORT).show();
                }
                Log.d("ADD ENTRY", category + " " + subCategory);

            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()) {
            case R.id.categories_spinner:
                category = parent.getItemAtPosition(position).toString();
                Log.d("ADD ENTRY", category);
                ArrayAdapter<CharSequence> subCategoriesAdapter =
                        ArrayAdapter.createFromResource(this, R.array.basics_array, android.R.layout.simple_spinner_item);

                if (category != null) {
                    switch (category) {
                        case "Basics":
                            subCategoriesAdapter =
                                    ArrayAdapter.createFromResource(this, R.array.basics_array, android.R.layout.simple_spinner_item);
                            subCategoriesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            subCategoriesSpinner.setAdapter(subCategoriesAdapter);
                            break;
                        case "Preaching Skills":
                            subCategoriesAdapter =
                                    ArrayAdapter.createFromResource(this, R.array.preaching_array, android.R.layout.simple_spinner_item);
                            subCategoriesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            subCategoriesSpinner.setAdapter(subCategoriesAdapter);
                            break;
                        case "Teaching Skills":
                            subCategoriesAdapter =
                                    ArrayAdapter.createFromResource(this, R.array.teaching_array, android.R.layout.simple_spinner_item);
                            subCategoriesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            subCategoriesSpinner.setAdapter(subCategoriesAdapter);
                            break;
                        case "Other":
                            subCategoriesAdapter =
                                    ArrayAdapter.createFromResource(this, R.array.other_array, android.R.layout.simple_spinner_item);
                            subCategoriesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            subCategoriesSpinner.setAdapter(subCategoriesAdapter);
                            break;
                    }
                }
                subCategoriesAdapter.notifyDataSetChanged();
                break;
            case R.id.sub_categories_spinner:
                Log.d("SUB CATEGORY", parent.getItemAtPosition(position).toString());
                subCategory = parent.getItemAtPosition(position).toString();
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
