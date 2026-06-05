package Netflix;

class Content {

    private String contentId;
    private String title;
    private String genre;
    private int durationMinutes;

    public Content(String contentId,
                   String title,
                   String genre,
                   int durationMinutes) {

        this.contentId = contentId;
        this.title = title;
        this.genre = genre;
        this.durationMinutes = durationMinutes;
    }

    public String getContentId() {
        return contentId;
    }

    public String getTitle() {
        return title;
    }

    public String getGenre() {
        return genre;
    }

    public int getDurationMinutes() {
        return durationMinutes;
    }
}