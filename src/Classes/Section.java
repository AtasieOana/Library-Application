package Classes;

import java.util.Arrays;
import java.util.Objects;

public class Section {

    private SectionType sectionType;
    private LibraryBook[] books;

    public Section(SectionType sectionType, LibraryBook[] books) {
        this.sectionType = sectionType;
        this.books = books;
    }

    public SectionType getSectionType() {
        return sectionType;
    }

    public void setSectionType(SectionType sectionType) {
        this.sectionType = sectionType;
    }

    public Book[] getBooks() {
        return books;
    }

    public void setBooks(LibraryBook[] books) {
        this.books = books;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o){
            return true;
        }
        if (o == null || getClass() != o.getClass()){
            return false;
        }
        Section section = (Section) o;
        return sectionType == section.sectionType && Arrays.equals(books, section.books);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(sectionType);
        result = 31 * result + Arrays.hashCode(books);
        return result;
    }
}
