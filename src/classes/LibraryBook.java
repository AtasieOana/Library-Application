package classes;

import java.sql.Date;

public class LibraryBook extends Book implements Comparable<LibraryBook>, CSVCompatible {

    private int idLibraryBook;
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

    public LibraryBook(int idLibraryBook, String name,int numberOfPages, int yearOfPublication, String language,
                       LibraryAuthor author, Section section, int numberOfCopies) {
        super(name, numberOfPages, yearOfPublication, language);
        this.author = author;
        this.section = section;
        this.numberOfCopies = numberOfCopies;
        this.idLibraryBook = idLibraryBook;
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

    public int getIdLibraryBook() {
        return idLibraryBook;
    }

    public void setIdLibraryBook(int idLibraryBook) {
        this.idLibraryBook = idLibraryBook;
    }

    @Override
    public int compareTo(LibraryBook libraryBook) {
        if(this.getName().equalsIgnoreCase(libraryBook.getName())) {
            if (this.author.equals(libraryBook.author)) {
                if (this.section.equals(libraryBook.section)) {
                    return this.numberOfCopies - libraryBook.numberOfCopies;
                } else {
                    return this.section.compareTo(libraryBook.section);
                }
            } else {
                return this.author.compareTo(libraryBook.author);
            }
        }
        else{
            return this.getName().compareToIgnoreCase(libraryBook.getName());
        }
    }

    @Override
    public String toString() {
        return "LibraryBook{" +
                "name=" + getName() +
                ", idLibraryBook=" + idLibraryBook +
                ", author=" + author +
                ", section=" + section +
                ", numberOfCopies=" + numberOfCopies +
                '}';
    }
}
