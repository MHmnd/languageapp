package com.slowthecurry.mycahh.learning;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Mycah on 7/6/2017.
 * set up with an ArrayList so that eventually can add logic to pull the list from the Firebase
 * database so entries can be added and adjusted as need be
 */

public class CollectionAdapter extends RecyclerView.Adapter<CollectionAdapter.ViewHolder> {

    private ArrayList<Collection> collectionArrayList;
    private String userID;
    private Activity callingActivity;

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView titleTextView;

        public ViewHolder(final View currentView) {
            super(currentView);
            final Context context = currentView.getContext();
            titleTextView = (TextView) currentView.findViewById(R.id.collections_title_textview);
            currentView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        }

        public TextView getTitleTextView() {
            return titleTextView;
        }
    }

    public CollectionAdapter(ArrayList<Collection> languageEntries, String userID, Activity callingActivity) {
        this.collectionArrayList = languageEntries;
        this.userID = userID;
        this.callingActivity = callingActivity;
    }

    @Override
    public CollectionAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.collections_title, parent, false);


        return new  ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Collection collection = collectionArrayList.get(position);

        holder.getTitleTextView().setText(collection.getCollectionTitle());

    }




    @Override
    public int getItemCount() {
        return collectionArrayList.size();
    }
}
