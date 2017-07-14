package com.slowthecurry.mycahh.learning;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Looper;
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
                    Toast toast = new Toast(currentView.getContext());
                    toast.setText("Audio coming soon!");
                    toast.setDuration(Toast.LENGTH_SHORT);
                    toast.show();

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

    public EntryAdapter(ArrayList<LanguageEntry> languageEntries) {
        this.languageEntries = languageEntries;
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
