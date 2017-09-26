package com.slowthecurry.mycahh.learning;

import java.util.ArrayList;

/**
 * Created by Mycah on 7/6/2017.
 * Class to mirror structure on Firebase Databes to build objects to
 * be passed to the ArrayAdapter to build the Main UI
 */

public class LanguageEntry {
    public LanguageEntry() {
    }

    public LanguageEntry(String tongan, String english) {
        this.tongan = tongan;
        this.english = english;
    }

    String tongan;
    String english;
    String key;
    Integer orderNumber;

    public Integer getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(Integer orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getTongan() {
        return tongan;
    }

    public void setTongan(String tongan) {
        this.tongan = tongan;
    }

    public String getEnglish() {
        return english;
    }

    public void setEnglish(String english) {
        this.english = english;
    }
}