package com.slowthecurry.mycahh.learning;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * Created by Mycah on 7/6/2017.
 */

public class CategoriesAdapter extends ArrayAdapter<String> {
    public CategoriesAdapter(Context context, ArrayList<String> category){
        super(context, 0, category);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        String title = getItem(position);
        View categoryCard = convertView;
        if (categoryCard == null) {
            categoryCard = LayoutInflater.from(getContext())
                    .inflate(R.layout.categories_list_item, parent, false);
        }
        TextView categoryTitleView = (TextView) categoryCard.findViewById(R.id.category_lable);
        categoryTitleView.setText(title);

    return categoryCard;
    }
}
