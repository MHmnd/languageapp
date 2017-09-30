package com.slowthecurry.mycahh.learning;

import java.util.ArrayList;

/**
 * Created by Mycah on 9/29/2017.
 */

public class Collection {
    String collectionTitle;
    String key;
    ArrayList <LanguageEntry> languageEntryArrayList;

    public Collection() {
    }

    public Collection(String collectionTitle, String key, ArrayList<LanguageEntry> languageEntryArrayList) {
        this.collectionTitle = collectionTitle;
        this.key = key;
        this.languageEntryArrayList = languageEntryArrayList;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getCollectionTitle() {
        return collectionTitle;
    }

    public void setCollectionTitle(String collectionTitle) {
        this.collectionTitle = collectionTitle;
    }

    public ArrayList<LanguageEntry> getLanguageEntryArrayList() {
        return languageEntryArrayList;
    }

    public void setLanguageEntryArrayList(ArrayList<LanguageEntry> languageEntryArrayList) {
        this.languageEntryArrayList = languageEntryArrayList;
    }
}
