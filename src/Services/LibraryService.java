package Services;

import Classes.*;

import java.util.*;

public class LibraryService {

    private Library library;

    public LibraryService(){
        this.library = new Library();
    }

    public LibraryService(Library library) {
        this.library = library;
    }

    public Library getLibrary() {
        return library;
    }

    public void setLibrary(Library library) {
        this.library = library;
    }

    /** Adding a book in the library;
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

    /** Remove a book from the library;
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
     * See all books written by an author
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
     *  See all the books in a section;
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

    /** See all the books in the library;
     *  A book in the library can be found in its section or in its author.
     *  I chose to use the sections to display all the books.
     */
    public void findBooksFromLibrary() {
        boolean found = false;
        TreeSet<LibraryBook> libraryBookTreeSet = new TreeSet<>();
        for (Section sec : library.getSections())
            libraryBookTreeSet = sec.getBooks();
        for (LibraryBook libraryBook : libraryBookTreeSet) {
            System.out.println(libraryBook);
            found = true;
        }

        if (!found){
            System.out.println("There are no books in the library!");
        }
    }

    /**
     * Adding a new reader;
     */
    public void addReader(Reader reader){
        ArrayList<Reader> allReaders = library.getReaders();
        allReaders.add(reader);
        library.setReaders(allReaders);
    }

    /**
     * Removing a reader;
     */
    public void removeReader(Reader reader){

        boolean found = false;
        ArrayList<Reader> allReaders = library.getReaders();
        Iterator<Reader> iterator = allReaders.iterator();
        while (iterator.hasNext()) {
            Reader r = iterator.next();
            if (r.equals(reader)) {
                iterator.remove();
                found = true;
            }
        }
        if(!found){
            System.out.println("Reader doesn't exist!");
        }
    }

    /**
     * Removing an author from the library;
     * When the author is removed, the books written by him are also removed.
     */
    public void removeAuthor(LibraryAuthor libraryAuthor){

        boolean found = false;
        TreeSet<LibraryAuthor> allLibraryAuthors = library.getLibraryAuthors();
        Iterator<LibraryAuthor> iterator = allLibraryAuthors.iterator();
        while (iterator.hasNext()) {
            LibraryAuthor la = iterator.next();
            if (la.equals(libraryAuthor)) {
                TreeSet<LibraryBook> libraryBookTreeSet = la.getBooks();
                for(LibraryBook libraryBook:libraryBookTreeSet){
                    libraryBook.getSection().removeBook(libraryBook);
                }
                iterator.remove();
                found = true;
            }
        }
        if(!found){
            System.out.println("Author doesn't exist!");
        }

    }

}
