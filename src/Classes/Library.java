package Classes;

import com.sun.source.tree.Tree;

import java.util.*;

public class Library {

    private ArrayList<Librarian> librarians;
    private TreeSet<LibraryAuthor> libraryAuthors;
    private TreeSet<Section> sections;
    private ArrayList<Reader> readers;
    private ArrayList<RequiredBook> requiredBooks;
    private ArrayList<Loan> loans;

    public Library() {
        this.librarians = new ArrayList<>();
        this.libraryAuthors = new TreeSet<>();
        this.sections = new TreeSet<>();
        this.readers = new ArrayList<>();
        this.requiredBooks = new ArrayList<>();
        this.loans = new ArrayList<>();
    }

    public Library(ArrayList<Librarian> librarians, TreeSet<LibraryAuthor> libraryAuthors, TreeSet<Section> sections,
                   ArrayList<Reader> readers, ArrayList<RequiredBook> requiredBooks, ArrayList<Loan> loans) {
        this.librarians = librarians;
        this.libraryAuthors = libraryAuthors;
        this.sections = sections;
        this.readers = readers;
        this.requiredBooks = requiredBooks;
        this.loans = loans;
    }

    public ArrayList<Librarian> getLibrarians() {
        return librarians;
    }

    public void setLibrarians(ArrayList<Librarian> librarians) {
        this.librarians = librarians;
    }

    public TreeSet<LibraryAuthor> getLibraryAuthors() {
        return libraryAuthors;
    }

    public void setLibraryAuthors(TreeSet<LibraryAuthor> libraryAuthors) {
        this.libraryAuthors = libraryAuthors;
    }

    public TreeSet<Section> getSections() {
        return sections;
    }

    public void setSections(TreeSet<Section> sections) {
        this.sections = sections;
    }

    public ArrayList<Reader> getReaders() {
        return readers;
    }

    public void setReaders(ArrayList<Reader> readers) {
        this.readers = readers;
    }

    public ArrayList<RequiredBook> getRequiredBooks() {
        return requiredBooks;
    }

    public void setRequiredBooks(ArrayList<RequiredBook> requiredBooks) {
        this.requiredBooks = requiredBooks;
    }

    public ArrayList<Loan> getLoans() {
        return loans;
    }

    public void setLoans(ArrayList<Loan> loans) {
        this.loans = loans;
    }

    /**
     * Method for adding a new section to the library
     */
    public void addSection(Section section){
        sections.add(section);
    }

    /**
     * Method for finding a section in the library
     */
    public Boolean findSection(Section section){
        if(sections.isEmpty()){
            return false;
        }
        return sections.contains(section);
    }

    /**
     * Method for adding a new author to the library
     */
    public void addAuthor(LibraryAuthor libraryAuthor){
        libraryAuthors.add(libraryAuthor);

    }

    /**
     * Method for finding a author in the library
     */
    public Boolean findAuthor(LibraryAuthor libraryAuthor){
        if(libraryAuthors.isEmpty()){
            return false;
        }
        return libraryAuthors.contains(libraryAuthor);
    }

    public void addBookInSection(Section section, LibraryBook book){
        if(findSection(section)){
            for(Section sec:sections){
                if(sec.equals(section)){
                    section.addBook(book);
                }
            }
        }
        else{
            System.out.println("Section doesn't exist!");
        }
    }

    public void addBookAtAuthor(LibraryAuthor author, LibraryBook book){
        if(findAuthor(author)){
            for(LibraryAuthor aut:libraryAuthors){
                if(aut.equals(author)){
                    author.addBook(book);
                }
            }
        }
        else{
            System.out.println("Author doesn't exist!");
        }
    }
}
