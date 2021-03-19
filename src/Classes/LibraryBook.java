package Classes;

public class LibraryBook extends Book implements Comparable<LibraryBook> {

    private LibraryAuthor author;
    private Section section;
    private int numberOfCopies;

    public LibraryBook(){
        author = new LibraryAuthor();
        section = new Section();
        numberOfCopies = 0;

    }

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

    @Override
    public int compareTo(LibraryBook libraryBook) {
        if (this.author.equals(libraryBook.author)) {
            if (this.section.equals(libraryBook.section)) {
                return this.numberOfCopies - libraryBook.numberOfCopies;
            }
            else{
                return this.section.compareTo(libraryBook.section);
            }
        }
        else {
            return this.author.compareTo(libraryBook.author);
        }
    }

    @Override
    public String toString() {
        return super.toString() +
                "      author: " + author + ",\n" +
                "      section: " + section + ",\n" +
                "      numberOfCopies=" + numberOfCopies;
    }
}
