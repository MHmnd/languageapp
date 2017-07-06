/**
 * Created by Mycah on 7/6/2017.
 * Class to mirror structure on Firebase Databes to build objects to
 * be passed to the ArrayAdapter to build the Main UI
 */

public class Categories {

    public class subCategories{
        String Title;

        public subCategories() {
        }

        public String getTitle() {
            return Title;
        }

        public void setTitle(String title) {
            Title = title;
        }

        //inner class for individual words and phrases.
        public class entries{
            String tongan;
            String english;

            public entries() {
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
