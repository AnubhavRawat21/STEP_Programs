package Netflix;
import java.util.ArrayList;
import java.util.List;

class Catalog {

    private final List<Content> contentList;

    public Catalog() {
        contentList = new ArrayList<>();
    }

    public void addContent(Content content) {
        contentList.add(content);
    }

    public void removeContentById(String id) {

        Content found = null;

        for (Content c : contentList) {
            if (c.getContentId().equals(id)) {
                found = c;
                break;
            }
        }

        if (found != null) {
            contentList.remove(found);
        }
    }

    public Content searchByTitle(String title) {

        for (Content c : contentList) {

            if (c.getTitle().equalsIgnoreCase(title)) {
                return c;
            }
        }

        return null;
    }

    public void displayAll() {

        if (contentList.isEmpty()) {
            System.out.println("Catalog Empty");
            return;
        }

        for (Content c : contentList) {

            System.out.println(
                    c.getContentId()
                    + " | "
                    + c.getTitle()
                    + " | "
                    + c.getGenre()
                    + " | "
                    + c.getDurationMinutes()
                    + " mins"
            );
        }
    }
}