package Classes;

import java.util.*;


public class Section implements Comparable<Section> {

    private SectionType sectionType;
    private TreeSet<LibraryBook> books;

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

    public void addBook(LibraryBook libraryBook) {
        // books = ArrayUtils.add(libraryBook); ???
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
        return sectionType == section.sectionType && Objects.equals(books, section.books);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sectionType, books);
    }

    @Override
    public int compareTo(Section section) {
        return this.sectionType.compareTo(section.sectionType);
    }

}