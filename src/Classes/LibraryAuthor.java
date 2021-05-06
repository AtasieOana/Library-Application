package Classes;

import CSVManage.CSVReadWrite;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
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
        modifyCVS();
    }

    /**
     * Method for removing a book from its author
     **/
    public void removeBook(LibraryBook libraryBook){
        books.remove(libraryBook);
        write.deleteFromCSV("LibraryAuthor.csv",this);
    }

    /**
     * When a new book is added, for the author of the book,
     * the books written in CSV corresponding to the authors in the library are updated.
     */
    public void modifyCVS(){
        ArrayList<String> read = new ArrayList<>();
        int numberLine = 1;
        int writerLine = 1;
        boolean ok = false;
        String firstLine = "";
        try(BufferedReader reader = new BufferedReader(new FileReader("LibraryAuthor.csv"))) {
            firstLine = reader.readLine();
            String line = reader.readLine();
            while (line != null) {
                String[] elements = line.split(",");
                if (elements[0].toLowerCase().equals(getLastName().toLowerCase()) &&
                        elements[1].toLowerCase().equals(getFirstName().toLowerCase())) {
                    ok = true;
                }
                else{
                    if(!ok) {
                        numberLine += 1;
                    }
                }
                read.add(line);
                line = reader.readLine();
            }
        } catch (IOException exception) {
            exception.printStackTrace();
        }

        try(FileWriter writer = new FileWriter("LibraryAuthor.csv", false)){
            if(ok) {
                writer.write(firstLine);
                writer.append("\n");
                for (String i : read) {
                    if (numberLine != writerLine) {
                        writer.write(i);
                    }
                    else{
                        String[] elements = i.split(",");
                        writer.append(elements[0]);
                        writer.append(",");
                        writer.append(elements[1]);
                        writer.append(",");
                        writer.append((getBooksTitle()));
                    }
                    writer.append("\n");
                    writerLine+=1;
                }
            }
        } catch (IOException exception) {
            exception.printStackTrace();
        }

    }

}
