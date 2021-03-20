package Services;

import Classes.*;

import java.util.*;

public class Service {

    private Library library;

    public Service(){
        this.library = new Library();
    }

    public Service(Library library) {
        this.library = library;
    }

    public Library getLibrary() {
        return library;
    }

    public void setLibrary(Library library) {
        this.library = library;
    }

    /** Adding a book in the library involving:
     * If the author of the book is not in the library, then it is added.
     * If the section is not in the library, then it is added.
     * The book is added to the author and the corresponding section.
     */
    public void addBookInLibrary(String name, int numberOfPages, int yearOfPublication, String language,
                                 LibraryAuthor libraryAuthor, Section section, int numberOfCopies){
        if(!library.findSection(section)){
            library.addSection(section);
        }
        if(!library.findAuthor(libraryAuthor)){
            library.addAuthor(libraryAuthor);
        }
        LibraryBook book = new LibraryBook(name, numberOfPages, yearOfPublication, language, libraryAuthor,
                                           section, numberOfCopies);
        library.addBookInSection(section, book);
        library.addBookAtAuthor(libraryAuthor, book);
    }

    /** Remove a book from the library involving:
     * If the author of the book has no other book in the library then it will be deleted.
     * The book will be removed from the section to which it belonged.
    */
    public void removeBookFromLibrary(LibraryBook book){
        book.getAuthor().removeBook(book);
        TreeSet<LibraryBook> libraryBookTreeSet = book.getAuthor().getBooks();
        if(libraryBookTreeSet.isEmpty()){
            library.getLibraryAuthors().remove(book.getAuthor());
        }
        book.getSection().removeBook(book);
    }

    /**
     * Find all books from an author;
     */
    public void findBooksFromAuthor(LibraryAuthor author){

        boolean found = false;
        TreeSet<LibraryBook> libraryBookTreeSet = new TreeSet<>();
        for(LibraryAuthor la: library.getLibraryAuthors())
            if(la.equals(author)){
                libraryBookTreeSet = la.getBooks();
                found = true;
            }

        if(found) {
            for (LibraryBook libraryBook : libraryBookTreeSet) {
                System.out.println(libraryBook);
            }
        }
        else{
            System.out.println("Author doesn't exist!");
        }
    }

    /**
     *  Find all books from a section;
     */
    public void findBooksFromSection(Section section) {
        boolean found = false;
        TreeSet<LibraryBook> libraryBookTreeSet = new TreeSet<>();
        for (Section sec : library.getSections())
            if (sec.equals(section)) {
                libraryBookTreeSet = sec.getBooks();
                found = true;
            }

        if (found) {
            for (LibraryBook libraryBook : libraryBookTreeSet) {
                System.out.println(libraryBook);
            }
        } else {
            System.out.println("Section doesn't exist!");
        }
    }

}
