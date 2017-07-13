package com.slowthecurry.mycahh.learning;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SubCategoryListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_category_list);
        Intent launchingIntent = getIntent();

        if (launchingIntent.hasExtra(Constants.SUB_CATEGORY_TITLE)) {
            setTitle(launchingIntent.getStringExtra(Constants.SUB_CATEGORY_TITLE));
        }else {
            setTitle(R.string.project_id);
        }
    }
}
