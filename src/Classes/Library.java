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

    public void addSection(SectionType sectionType){

        TreeSet<LibraryBook> nullBooks = new TreeSet<>();
        Section section = new Section(sectionType, nullBooks);
        sections.add(section);

    }

    public Boolean findSection(Section section){

        for (Section s : sections) {
            if (s == section)
                return true;
        }
        return false;
    }

    public void addAuthor(String firstName, String lastName){

        TreeSet<LibraryBook> nullBooks = new TreeSet<>();
        LibraryAuthor libraryAuthor = new LibraryAuthor(firstName, lastName, nullBooks);
        libraryAuthors.add(libraryAuthor);

    }

    public Boolean findAuthor(LibraryAuthor libraryAuthor){

        for (LibraryAuthor la: libraryAuthors) {
            if (la == libraryAuthor)
                return true;
        }
        return false;
    }

}
