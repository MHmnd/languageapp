package com.slowthecurry.mycahh.learning;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * Created by Mycah on 7/6/2017.
 * Adapter do display the given vocab words for each subcategory
 * handles onLongClicks to add to a user's collection.
 */

public class CollectionListAdapter extends RecyclerView.Adapter<CollectionListAdapter.ViewHolder> {

    private ArrayList<LanguageEntry> languageEntries;
    private Activity callingActivity;

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView englishText;
        private final TextView tonganText;

        public ViewHolder(final View currentView) {
            super(currentView);
            final Context context = currentView.getContext();
            englishText = (TextView) currentView.findViewById(R.id.english_entry);
            tonganText = (TextView) currentView.findViewById(R.id.tongan_entry);

            //TODO:ADD LOGIC TO RETRIEVE AND PLAY APPROPRIATE AUDIO FILE.
            currentView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(currentView.getContext(), "Audio coming soon!", Toast.LENGTH_SHORT).show();
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

    public CollectionListAdapter(ArrayList<LanguageEntry> languageEntries,
                                 Activity callingActivity) {
        this.languageEntries = languageEntries;
        this.callingActivity = callingActivity;
    }

    @Override
    public CollectionListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
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
