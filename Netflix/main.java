package Netflix;
import java.util.ArrayList;
import java.util.List;

class Main {

    public static void main(String[] args) {

        Content c1 =
            new Content(
                "C101",
                "Stranger Things",
                "Sci-Fi",
                55
            );

        Content c2 =
            new Content(
                "C102",
                "Money Heist",
                "Crime",
                48
            );

        Profile p1 =
            new Profile(
                "John",
                "Adult"
            );

        Profile p2 =
            new Profile(
                "Kids",
                "Child"
            );

        p1.addToWatchList(c1);
        p1.addToWatchList(c2);

        List<Profile> profiles =
            new ArrayList<>();

        profiles.add(p1);
        profiles.add(p2);

        User user =
            new User(
                "U101",
                "John Doe",
                profiles
            );

        user.displayProfiles();

        p1.displayWatchList();
    }
}