package sample.LoggedIn.Diary;

public class Note {
    private String title;
    private String shortDescription;
    private String details;

    public Note(String title, String shortDescription, String details) {
        this.title = title;
        this.shortDescription = shortDescription;
        this.details = details;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    @Override
    public String toString() {
        return title ;
    }
}
