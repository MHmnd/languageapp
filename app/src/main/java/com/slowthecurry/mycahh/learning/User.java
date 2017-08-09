package com.slowthecurry.mycahh.learning;

import android.net.Uri;

import java.net.URI;

/**
 * Created by Mycah on 8/8/2017.
 */

public class User {
    String firebaseUID;
    String googleUID;
    String displayName;
    Uri photoURL;

    public User() {
    }

    public User(String firebaseUID, String googleUID, String displayName, Uri photoURL) {
        this.firebaseUID = firebaseUID;
        this.googleUID = googleUID;
        this.displayName = displayName;
        this.photoURL = photoURL;
    }

    public String getFirebaseUID() {
        return firebaseUID;
    }

    public void setFirebaseUID(String firebaseUID) {
        this.firebaseUID = firebaseUID;
    }

    public String getGoogleUID() {
        return googleUID;
    }

    public void setGoogleUID(String googleUID) {
        this.googleUID = googleUID;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public Uri getPhotoURL() {
        return photoURL;
    }

    public void setPhotoURL(Uri photoURL) {
        this.photoURL = photoURL;
    }
}
