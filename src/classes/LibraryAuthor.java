package classes;

import java.util.*;

public class LibraryAuthor extends Author implements Comparable<LibraryAuthor>, CSVCompatible{

    private TreeSet<LibraryBook> books;

    public LibraryAuthor(){
        this.books = new TreeSet<>();
    }

    public LibraryAuthor(String lastName, String firstName) {
        super(lastName, firstName);
        this.books = new TreeSet<>();
    }

    public LibraryAuthor(String lastName, String firstName, TreeSet<LibraryBook> books) {
        super(lastName, firstName);
        this.books = books;
    }

    public LibraryAuthor(int idLibraryAuthor, String lastName, String firstName) {
        super(idLibraryAuthor, lastName, firstName);
    }

    public TreeSet<LibraryBook> getBooks() {
        return books;
    }

    public void setBooks(TreeSet<LibraryBook> books) {
        this.books = books;
    }


    @Override
    public int compareTo(LibraryAuthor author) {
        if (this.getFirstName().equalsIgnoreCase(author.getFirstName())) {
            return this.getLastName().compareToIgnoreCase(author.getLastName());
        }
        return this.getFirstName().compareToIgnoreCase(author.getFirstName());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        LibraryAuthor that = (LibraryAuthor) o;
        return Objects.equals(books, that.books);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), books);
    }

    @Override
    public String toString() {
        return super.toString();
    }

}
