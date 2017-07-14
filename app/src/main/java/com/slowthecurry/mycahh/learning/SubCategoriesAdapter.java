package com.slowthecurry.mycahh.learning;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Mycah on 7/6/2017.
 * set up with an ArrayList so that eventually can add logic to pull the list from the Firebase
 * database so entries can be added and adjusted as need be
 */

public class SubCategoriesAdapter extends RecyclerView.Adapter<SubCategoriesAdapter.ViewHolder> {

    private ArrayList<String> subCategories;
    private String category;

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final Button subCategoryTitleView;

        public ViewHolder(View currentView) {
            super(currentView);
            final Context context = currentView.getContext();
            subCategoryTitleView = (Button) currentView.findViewById(R.id.sub_category_title_view);
            subCategoryTitleView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent goToSubCategoryList = new Intent(context, SubCategoryListActivity.class);
                    goToSubCategoryList.putExtra(Constants.SUB_CATEGORY_TITLE, subCategoryTitleView.getText().toString());
                    goToSubCategoryList.putExtra(Constants.CATEGORIES, category);
                    Log.d("CLICKED", subCategoryTitleView.getText().toString());
                    context.startActivity(goToSubCategoryList);
                }
            });
        }

        public Button getSubCategoryTitleView() {
            return subCategoryTitleView;
        }

    }

    public SubCategoriesAdapter(ArrayList<String> subCategories, String category) {
        this.subCategories = subCategories;
        this.category = category;
    }

    @Override
    public SubCategoriesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.subcategory_list_item, parent, false);

        return new  ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.getSubCategoryTitleView().setText(subCategories.get(position));
    }

    @Override
    public int getItemCount() {
        return subCategories.size();
    }
}
