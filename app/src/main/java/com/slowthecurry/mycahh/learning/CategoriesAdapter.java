package com.slowthecurry.mycahh.learning;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.ArrayList;

/**
 * Created by Mycah on 7/6/2017.
 */

public class CategoriesAdapter extends ArrayAdapter<Categories> {
    public CategoriesAdapter(Context context, ArrayList<Categories> categories){
        super(context, 0, categories);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        final Categories category =  getItem(position);
        View categoryCard = convertView;
        if (categoryCard == null) {
            categoryCard = LayoutInflater.from(getContext())
                    .inflate(R.layout.categories_list_item, parent, false);
        }

    }
}
