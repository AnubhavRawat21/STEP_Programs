package Netflix;
import java.util.List;
class User {

    private String userId;
    private String name;

    private List<Profile> profiles;

    public User(String userId,
                String name,
                List<Profile> profiles) {

        this.userId = userId;
        this.name = name;
        this.profiles = profiles;
    }

    public String getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    public void displayProfiles() {

        System.out.println(
            "\nProfiles for " + name
        );

        for(Profile p : profiles) {

            System.out.println(
                p.getProfileName()
                + " (" +
                p.getAgeCategory()
                + ")"
            );
        }
    }

    public void addProfile(Profile profile) {

        if(profiles.size() < 5) {

            profiles.add(profile);

        } else {

            System.out.println(
                "Maximum 5 profiles allowed."
            );
        }
    }

    public void removeProfile(String profileName) {

        profiles.removeIf(
            p -> p.getProfileName()
                  .equalsIgnoreCase(profileName)
        );
    }
}