package com.slowthecurry.mycahh.learning;

import java.util.ArrayList;

/**
 * Created by Mycah on 7/10/2017.
 */

public class Category {
    String title;
    ArrayList<String> subCategories;

    public Category() {
    }

    public Category(String title, ArrayList<String> subCategories) {
        this.title = title;
        this.subCategories = subCategories;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ArrayList<String> getSubCategories() {
        return subCategories;
    }

    public void setSubCategories(ArrayList<String> subCategories) {
        this.subCategories = subCategories;
    }
}
