package classes;

import csvManage.CSVReadWrite;

import java.util.*;

public class LibraryAuthor extends Author implements Comparable<LibraryAuthor>{

    private TreeSet<LibraryBook> books;
    private final CSVReadWrite write = CSVReadWrite.getInstance();

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

    public String getBooksTitle(){
        StringBuilder booksTitle = new StringBuilder();
        for(LibraryBook lb: books){
            booksTitle.append(lb.getName());
            booksTitle.append(";");
        }
        return booksTitle.toString();
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

    /**
     * Method for adding a book to its author
     **/
    public void addBook(LibraryBook libraryBook){
        books.add(libraryBook);
        CSVReadWrite.instance.updateBooksInCSV("LibraryAuthorWrite.csv",this, write);
    }

    /**
     * Method for removing a book from its author
     **/
    public void removeBook(LibraryBook libraryBook){
        books.remove(libraryBook);
        write.deleteFromCSV("LibraryAuthorWrite.csv",this);
    }


}