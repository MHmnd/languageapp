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

public class EntriesAdapter extends RecyclerView.Adapter<EntriesAdapter.ViewHolder> {

    private ArrayList<LanguageEntry> languageEntries;
    private ArrayList<String> collectionTitiles;
    private String userID;
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
                    final LanguageEntry languageEntry = new LanguageEntry(tonganToAdd, englishToAdd);

                    /*
                     * converts Arraylist to String Array
                     * to be past to AlertDialog
                     */
                    String currentUserId = userID;
                    String[] titlesArrayfiller = new String[collectionTitiles.size()];
                    final String[] titlesArray = collectionTitiles.toArray(titlesArrayfiller);



                    /*
                     * Alert Dialog to select Collection to add languageEntry to
                     * or add a new collection.
                     */
                    final AlertDialog.Builder collectionsAlertBuilder =
                            new AlertDialog.Builder(currentView.getContext());
                    collectionsAlertBuilder.setTitle("Add to which collection?");
                    collectionsAlertBuilder.setItems(titlesArray, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            final String selectedTitle = titlesArray[which];
                            FirebaseDatabase database = FirebaseDatabase.getInstance();
                            final DatabaseReference reference = database.getReference();
                            reference.child(Constants.COLLECTION).child(userID)
                                    .addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(DataSnapshot dataSnapshot) {
                                            for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                                                Collection thisCollection = snapshot.getValue(Collection.class);
                                                if(thisCollection.getCollectionTitle().equals(selectedTitle)){
                                                    thisCollection.getLanguageEntryArrayList().add(languageEntry);
                                                    reference.child(Constants.COLLECTION)
                                                            .child(userID)
                                                            .child(snapshot.getKey())
                                                            .setValue(thisCollection);
                                                }
                                            }
                                        }

                                        @Override
                                        public void onCancelled(DatabaseError databaseError) {

                                        }
                                    });
                        }
                    });

                    //Add new Collection
                    collectionsAlertBuilder.setPositiveButton("New Collection", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            final AlertDialog.Builder newCollectionBuilder =
                                    new AlertDialog.Builder(currentView.getContext());
                            LayoutInflater inflater = callingActivity.getLayoutInflater();
                            newCollectionBuilder.setView(inflater.inflate(R.layout.new_collection_dialog, null))
                                    .setPositiveButton("Create", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            Dialog filler = (Dialog) dialog;
                                            EditText newTitle = (EditText) filler.findViewById(R.id.new_title);
                                            ArrayList <LanguageEntry> newEntryList = new ArrayList<>();
                                            newEntryList.add(languageEntry);
                                            FirebaseDatabase database = FirebaseDatabase.getInstance();
                                            DatabaseReference reference = database.getReference();
                                            String newKey = reference.child(Constants.COLLECTION)
                                                    .child(userID)
                                                    .push().getKey();
                                            Collection newCollection =
                                                    new Collection(newTitle.getText().toString(),
                                                            newKey,
                                                            newEntryList);
                                            reference.child(Constants.COLLECTION)
                                                    .child(userID)
                                                    .child(newKey)
                                                    .setValue(newCollection);

                                        }
                                    });
                            AlertDialog newCollectionAlertDialog = newCollectionBuilder.create();
                            newCollectionAlertDialog.show();
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

    public EntriesAdapter(ArrayList<LanguageEntry> languageEntries,
                          ArrayList<String> collectionTitiles,
                          String userID,
                          Activity callingActivity) {
        this.languageEntries = languageEntries;
        this.collectionTitiles = collectionTitiles;
        this.userID = userID;
        this.callingActivity = callingActivity;
    }

    @Override
    public EntriesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
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
