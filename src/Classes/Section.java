package Classes;

import CSVManage.CSVReadWrite;

import java.util.*;


public class Section implements Comparable<Section> {

    private SectionType sectionType;
    private TreeSet<LibraryBook> books;
    private final CSVReadWrite write = CSVReadWrite.getInstance();

    public Section(){
        sectionType = null;
        books = new TreeSet<>();
    }

    public Section(SectionType sectionType){
        this.sectionType = sectionType;
        books = new TreeSet<>();
    }

    public Section(SectionType sectionType, TreeSet<LibraryBook> books) {
        this.sectionType = sectionType;
        this.books = books;
    }

    public SectionType getSectionType() {
        return sectionType;
    }

    public void setSectionType(SectionType sectionType) {
        this.sectionType = sectionType;
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
        Section section = (Section) o;
        return sectionType == section.sectionType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(sectionType, books);
    }

    @Override
    public int compareTo(Section section) {
        return this.sectionType.compareTo(section.sectionType);
    }

    @Override
    public String toString() {
        return "sectionType=" + sectionType;
    }

    /**
     * Method to find a book in the section
     **/
    public boolean findBook(LibraryBook book){
        if(books.isEmpty()){
            return false;
        }
        return books.contains(book);
    }

    /**
     * Method for adding a book to its section
     **/
    public void addBook(LibraryBook libraryBook) {
        books.add(libraryBook);
        write.updateBooksInCVS("Section.csv",this);
    }

    /**
     * Method for removing a book from its section
     **/
    public void removeBook(LibraryBook libraryBook){
        books.remove(libraryBook);
        write.deleteFromCSV("Section.csv",this);
    }

}