package Classes;

public class LibraryBook {

    private LibraryAuthor author;
    private Section section;
    private int numberOfCopies;

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
