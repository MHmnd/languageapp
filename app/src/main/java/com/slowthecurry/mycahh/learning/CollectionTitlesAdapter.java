package com.slowthecurry.mycahh.learning;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.sql.Array;
import java.util.ArrayList;

/**
 * Created by Mycah on 7/6/2017.
 * set up with an ArrayList so that eventually can add logic to pull the list from the Firebase
 * database so entries can be added and adjusted as need be
 */

public class CollectionTitlesAdapter extends RecyclerView.Adapter<CollectionTitlesAdapter.ViewHolder> {

    private ArrayList<Collection> collectionArrayList;
    private String userID;
    private Activity callingActivity;
    private String[] tutorialsTites;

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final Button titleButton;

        public ViewHolder(final View currentView) {
            super(currentView);
            final Context context = currentView.getContext();
            titleButton = (Button) currentView.findViewById(R.id.collections_title_button);
        }

        public Button getTitleButton() {
            return titleButton;
        }
    }

    public CollectionTitlesAdapter(ArrayList<Collection> collectionArrayList, String userID, Activity callingActivity) {
        this.collectionArrayList = collectionArrayList;
        this.userID = userID;
        this.callingActivity = callingActivity;
    }

    public CollectionTitlesAdapter(Activity callingActivity, String[] tutorialsTites) {
        this.callingActivity = callingActivity;
        this.tutorialsTites = tutorialsTites;
    }

    @Override
    public CollectionTitlesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.collections_title, parent, false);


        return new  ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final ActivityOptionsCompat activityOptionsCompat =
                ActivityOptionsCompat.makeSceneTransitionAnimation(callingActivity, null);
        if(collectionArrayList != null) {
            final Collection collection = collectionArrayList.get(position);

            holder.getTitleButton().setText(collection.getCollectionTitle());
            holder.getTitleButton().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent goToCollectionList = new Intent(callingActivity.getApplicationContext()
                            , CollectionListActivity.class);
                    goToCollectionList.putExtra(Constants.USERS, userID);
                    goToCollectionList.putExtra(Constants.COLLECTION, collection.getKey());
                    goToCollectionList.putExtra("Title", collection.getCollectionTitle());
                    callingActivity.startActivity(goToCollectionList, activityOptionsCompat.toBundle());
                }
            });
        }

        if(tutorialsTites != null){
            final String titleString = tutorialsTites[position];
            holder.getTitleButton().setText(titleString);

            holder.getTitleButton().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent goToLesson = new Intent(callingActivity.getApplicationContext(),
                            TutorialLesson.class);
                    goToLesson.putExtra(Constants.TUTORIAL_TITLE, titleString);
                    callingActivity.startActivity(goToLesson, activityOptionsCompat.toBundle());
                }
            });

        }
    }




    @Override
    public int getItemCount() {
        if(collectionArrayList != null) {
            return collectionArrayList.size();
        }
        if(tutorialsTites != null){
            return tutorialsTites.length;
        }else {
            return 0;
        }

    }
}
