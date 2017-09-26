package com.slowthecurry.mycahh.learning;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Looper;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by Mycah on 7/6/2017.
 * set up with an ArrayList so that eventually can add logic to pull the list from the Firebase
 * database so entries can be added and adjusted as need be
 */

public class EntryAdapter extends RecyclerView.Adapter<EntryAdapter.ViewHolder> {

    private ArrayList<LanguageEntry> languageEntries;
    private String userID;

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView englishText;
        private final TextView tonganText;

        public ViewHolder(final View currentView) {
            super(currentView);
            final Context context = currentView.getContext();
            englishText = (TextView) currentView.findViewById(R.id.english_entry);
            tonganText = (TextView) currentView.findViewById(R.id.tongan_entry);
            currentView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(currentView.getContext(), "Audio coming soon!", Toast.LENGTH_SHORT).show();
            }
            });
            currentView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {

                    /*
                     * Get the info from the item selected and build it
                     * into a new LanguageEntry Object to be passed to
                     * the Collection of choice on Firebase Database
                     */
                    String englishToAdd = englishText.getText().toString();
                    String tonganToAdd = tonganText.getText().toString();
                    LanguageEntry languageEntry = new LanguageEntry(tonganToAdd, englishToAdd);

                    /*
                     * TODO: Check the Firebase Database for any existing
                     * collections and add them to the list
                     */
                    String currentUserId = userID;
                    final String[] tempArray = {"bob", "hi", "is this thing on"};

                    /*
                     * Alert Dialog to select Collection to add languageEntry to
                     * or add a new collection.
                     */
                    AlertDialog.Builder collectionsAlertBuilder = new AlertDialog.Builder(currentView.getContext());
                    collectionsAlertBuilder.setTitle("Add to which collection?");
                    collectionsAlertBuilder.setItems(tempArray, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Toast.makeText(context, tempArray[which], Toast.LENGTH_SHORT).show();
                        }
                    });
                    collectionsAlertBuilder.setPositiveButton("New Collection", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });

                    AlertDialog alertDialog = collectionsAlertBuilder.create();
                    alertDialog.show();
                    return true;
                }
            });
        }


        public TextView getEnglishText() {
            return englishText;
        }
        public TextView getTonganText() {
            return tonganText;
        }

    }

    public EntryAdapter(ArrayList<LanguageEntry> languageEntries, String userID) {
        this.languageEntries = languageEntries;
        this.userID = userID;
    }

    @Override
    public EntryAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.entry_item, parent, false);

        return new  ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        LanguageEntry currentEntry = languageEntries.get(position);

        holder.getEnglishText().setText(currentEntry.getEnglish());
        holder.getTonganText().setText(currentEntry.getTongan());

    }

    @Override
    public int getItemCount() {
        return languageEntries.size();
    }
}
