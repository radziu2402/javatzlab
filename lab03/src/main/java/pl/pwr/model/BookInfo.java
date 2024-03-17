package pl.pwr.model;


public class BookInfo {
    private final String title;
    private final String authorFirstName;
    private final String authorLastName;
    private final int pages;

    public BookInfo(String title, String authorFirstName, String authorLastName, int pages) {
        this.title = title;
        this.authorFirstName = authorFirstName;
        this.authorLastName = authorLastName;
        this.pages = pages;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthorFirstName() {
        return authorFirstName;
    }

    public String getAuthorLastName() {
        return authorLastName;
    }

    public int getPages() {
        return pages;
    }
}
