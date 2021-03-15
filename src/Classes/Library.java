package Classes;

public class Library {

    private Librarian[] librarians;
    private LibraryAuthor[] libraryAuthors;
    private Section[] sections;
    private Reader[] readers;
    private RequiredBook[] requiredBooks;
    private Loan[] loans;

    public Section[] getSections() {
        return sections;
    }

    public void setSections(Section[] sections) {
        this.sections = sections;
    }

    public void addSection(SectionType sectionType){

        LibraryBook[] nullBooks = new LibraryBook[0];
        Section section = new Section(sectionType, nullBooks);
        // sections = ArrayUtils.add(sections); ???

    }

    public Boolean findSection(Section section){

        for (Section s : sections) {
            if (s == section)
                return true;
        }
        return false;
    }

    public void addAuthor(String firstName, String lastName){

        LibraryBook[] nullBooks = new LibraryBook[0];
        LibraryAuthor libraryAuthor = new LibraryAuthor(firstName, lastName, nullBooks);
        // libraryAuthors = ArrayUtils.add(libraryAuthor); ???

    }

    public Boolean findAuthor(LibraryAuthor libraryAuthor){

        for (LibraryAuthor la: libraryAuthors) {
            if (la == libraryAuthor)
                return true;
        }
        return false;
    }

}
