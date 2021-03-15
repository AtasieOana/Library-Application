package Classes;

public class LibraryBook extends Book {

    private LibraryAuthor author;
    private Section section;
    private int numberOfCopies;

    public LibraryBook(String name, int numberOfPages, int yearOfPublication, String language,
                       LibraryAuthor author, Section section, int numberOfCopies) {
        super(name, numberOfPages, yearOfPublication, language);
        this.author = author;
        this.section = section;
        this.numberOfCopies = numberOfCopies;
    }

    public LibraryAuthor getAuthor() {
        return author;
    }

    public void setAuthor(LibraryAuthor author) {
        this.author = author;
    }

    public Section getSection() {
        return section;
    }

    public void setSection(Section section) {
        this.section = section;
    }

    public int getNumberOfCopies() {
        return numberOfCopies;
    }

    public void setNumberOfCopies(int numberOfCopies) {
        this.numberOfCopies = numberOfCopies;
    }
}
