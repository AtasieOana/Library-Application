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

    public TreeSet<LibraryBook> getBooks() {
        return books;
    }

    public void setBooks(TreeSet<LibraryBook> books) {
        this.books = books;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        return super.equals(o);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), books);
    }

    @Override
    public int compareTo(LibraryAuthor author) {
        if (this.getFirstName().equalsIgnoreCase(author.getFirstName())) {
            return this.getLastName().compareToIgnoreCase(author.getLastName());
        }
        return this.getFirstName().compareToIgnoreCase(author.getFirstName());
    }

    @Override
    public String toString() {
        return super.toString();
    }

}
