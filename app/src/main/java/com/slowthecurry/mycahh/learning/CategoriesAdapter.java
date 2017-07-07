package com.slowthecurry.mycahh.learning;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.ArrayList;

/**
 * Created by Mycah on 7/6/2017.
 */

public class CategoriesAdapter extends ArrayAdapter {
    public CategoriesAdapter(Context context, ArrayList<Categories> categoriesArrayList){
        super(context, 0, categoriesArrayList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return super.getView(position, convertView, parent);
    }
}
