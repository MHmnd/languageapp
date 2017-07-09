package com.slowthecurry.mycahh.learning;

import java.util.ArrayList;

/**
 * Created by Mycah on 7/6/2017.
 * Class to mirror structure on Firebase Databes to build objects to
 * be passed to the ArrayAdapter to build the Main UI
 */

public class Categories {
    String title;
    ArrayList<subCategories> subCategoriesArrayList;

    public Categories() {
    }

    public Categories(String title, ArrayList<subCategories> subCategoriesArrayList) {
        this.title = title;
        this.subCategoriesArrayList = subCategoriesArrayList;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ArrayList<subCategories> getSubCategoriesArrayList() {
        return subCategoriesArrayList;
    }

    public void setSubCategoriesArrayList(ArrayList<subCategories> subCategoriesArrayList) {
        this.subCategoriesArrayList = subCategoriesArrayList;
    }

    public class subCategories{
        String subTitle;
        ArrayList<entrie> entries;

        public subCategories() {
        }

        public ArrayList<entrie> getEntries() {
            return entries;
        }

        public void setEntries(ArrayList<entrie> entries) {
            this.entries = entries;
        }

        public String getSubTitle() {
            return subTitle;
        }

        public void setSubTitle(String subTitle) {
            this.subTitle = subTitle;
        }

        //inner class for individual words and phrases.
        public class entrie {
            String tongan;
            String english;
            String key;

            public entrie() {
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
    }
}
