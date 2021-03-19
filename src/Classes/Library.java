package Classes;

import java.util.*;

public class Library {

    private ArrayList<Librarian> librarians;
    private TreeSet<LibraryAuthor> libraryAuthors;
    private ArrayList<Section> sections;
    private ArrayList<Reader> readers;
    private ArrayList<RequiredBook> requiredBooks;
    private ArrayList<Loan> loans;

    public Library() {
        this.librarians = new ArrayList<>();
        this.libraryAuthors = new TreeSet<>();
        this.sections = new ArrayList<>();
        this.readers = new ArrayList<>();
        this.requiredBooks = new ArrayList<>();
        this.loans = new ArrayList<>();
    }

    public Library(ArrayList<Librarian> librarians, TreeSet<LibraryAuthor> libraryAuthors, ArrayList<Section> sections,
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

    public ArrayList<Section> getSections() {
        return sections;
    }

    public void setSections(ArrayList<Section> sections) {
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

    public void addSection(Section section){
        sections.add(section);
    }

    public Boolean findSection(Section section){
        if(sections.isEmpty()){
            return false;
        }
        return sections.contains(section);
    }

    public void addAuthor(LibraryAuthor libraryAuthor){
        libraryAuthors.add(libraryAuthor);

    }

    public Boolean findAuthor(LibraryAuthor libraryAuthor){
        if(libraryAuthors.isEmpty()){
            return false;
        }
        return libraryAuthors.contains(libraryAuthor);
    }

}
