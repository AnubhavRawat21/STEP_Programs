package Netflix;

import java.util.ArrayList;
import java.util.List;

class Profile {

    private String profileName;
    private String ageCategory;

    private List<Content> watchList;

    public Profile(String profileName,
                   String ageCategory) {

        this.profileName = profileName;
        this.ageCategory = ageCategory;

        watchList = new ArrayList<>();
    }

    public String getProfileName() {
        return profileName;
    }

    public String getAgeCategory() {
        return ageCategory;
    }

    public void addToWatchList(Content content) {
        watchList.add(content);
    }

    public void removeFromWatchList(String contentId) {

        watchList.removeIf(
            c -> c.getContentId().equals(contentId)
        );
    }

    public void displayWatchList() {

        System.out.println(
            "\nWatchlist of " + profileName
        );

        for (Content c : watchList) {

            System.out.println(
                c.getTitle()
            );
        }
    }
}