package services;

import classes.*;
import classes.Reader;

import java.util.*;
import java.util.concurrent.TimeUnit;


public class LibraryService {

    private static LibraryService INSTANCE = null;
    private static final SectionService sectionService = SectionService.getInstance();
    private static final LibraryAuthorService libraryAuthorService = LibraryAuthorService.getInstance();


    public static synchronized LibraryService getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new LibraryService();
        }
        return INSTANCE;
    }


    /** See all the books in the library;
     *  A book in the library can be found in its section or in its author.
     *  I chose to use the sections to display all the books.
     */
    public void findBooksFromLibrary(Library library) {
        boolean found = false;
        TreeSet<LibraryBook> libraryBookTreeSet;
        for (Section sec : library.getSections()) {
            libraryBookTreeSet = sec.getBooks();
            for (LibraryBook libraryBook : libraryBookTreeSet) {
                System.out.println(libraryBook);
                found = true;
            }
        }
        if (!found){
            System.out.println("There are no books in the library!");
        }
    }


    /**
     * Method for adding a new section to the library
     **/
    public void addSection(Library library, Section section) {
        TreeSet<Section> sections = library.getSections();
        sections.add(section);
        library.setSections(sections);
    }

    /**
     * Method for adding a new required book to the library
     **/
    public void addRequiredBook(Library library, RequiredBook requiredBook) {
        ArrayList<RequiredBook> requiredBooks = library.getRequiredBooks();
        requiredBooks.add(requiredBook);
        library.setRequiredBooks(requiredBooks);
    }

    /**
     * Method for adding a new loan to the library
     **/
    public void addLoan(Library library, Loan loan) {
        ArrayList<Loan> loans = library.getLoans();
        loans.add(loan);
        library.setLoans(loans);
    }

    /**
     * Method for adding a new librarian to the library
     **/
    public void addLibrarian(Library library,Librarian librarian) {
        ArrayList<Librarian> librarians = library.getLibrarians();
        librarians.add(librarian);
        library.setLibrarians(librarians);
    }

    /**
     * Method for adding a new reader to the library
     **/
    public void addReader(Library library, Reader reader) {
        ArrayList<Reader> readers = library.getReaders();
        readers.add(reader);
        library.setReaders(readers);
    }

    /**
     * Method for removing a loan
     **/
    public void removeLoan(Library library, Loan loan) {
        ArrayList<Loan> loans = library.getLoans();
        loans.remove(loan);
        library.setLoans(loans);
    }

    /**
     * Method for finding a section in the library
     **/
    public Boolean findSection(Library library, Section section) {
        TreeSet<Section> sections = library.getSections();
        if (sections.isEmpty()) {
            return false;
        }
        return sections.contains(section);
    }

    /**
     * Method for adding a new author to the library
     **/
    public void addAuthor(Library library, LibraryAuthor libraryAuthor) {
        TreeSet<LibraryAuthor> libraryAuthors = library.getLibraryAuthors();
        libraryAuthors.add(libraryAuthor);
        library.setLibraryAuthors(libraryAuthors);
    }

    /**
     * Method for finding a author in the library
     **/
    public Boolean findAuthor(Library library, LibraryAuthor libraryAuthor) {
        TreeSet<LibraryAuthor> libraryAuthors = library.getLibraryAuthors();
        if (libraryAuthors.isEmpty()) {
            return false;
        }
        return libraryAuthors.contains(libraryAuthor);
    }

    /**
     * Method for adding a book to a section
     **/
    public void addBookInSection(Library library, Section section, LibraryBook book) {
        boolean found = false;
        for (Section sec : library.getSections()) {
            if (sec.equals(section)) {
                sectionService.addBook(sec, book);
                found = true;
            }
        }
        if (!found) {
            System.out.println("Section doesn't exist!");
        }
    }

    /**
     * Method to add a book to the corresponding author
     **/
    public void addBookAtAuthor(Library library, LibraryAuthor author, LibraryBook book) {
        boolean found = false;
        for (LibraryAuthor aut : library.getLibraryAuthors()) {
            if (aut.equals(author)) {
                libraryAuthorService.addBook(book, aut);
                found = true;
            }
        }
        if (!found) {
            System.out.println("Author doesn't exist!");
        }
    }

    /**
     * Method to check if there is an author in the library
     */
    public LibraryAuthor checkAuthor(Library library,Author author){
        for (LibraryAuthor aut : library.getLibraryAuthors()) {
            if (aut.getFirstName().equalsIgnoreCase(author.getFirstName())
                    && aut.getLastName().equalsIgnoreCase(author.getLastName())) {
                return aut;
            }
        }
        return new LibraryAuthor();
    }

    /**
     * Method to find a reader by its name
     */
    public Reader findReaderByName(Library library, String lastname, String firstname) {
        for (Reader reader : library.getReaders())
            if (reader.getFirstName().equalsIgnoreCase(firstname) && reader.getLastName().equalsIgnoreCase(lastname)) {
                return reader;
            }
        return new Reader();
    }

    /**
     * Method to find a librarian by its name
     */
    public Librarian findLibrarianByName(Library library, String lastname, String firstname) {
        for (Librarian librarian : library.getLibrarians()) {
            if (librarian.getFirstName().equalsIgnoreCase(firstname)
                    && librarian.getLastName().equalsIgnoreCase(lastname)) {
                return librarian;
            }
        }
        return new Librarian();
    }

    /**
     * Method for finding a loan by the reader's name and the name of the book
     */
    public Loan findLoan(Library library, String readerFirstName, String readerLastName, String bookName, String lastNameAuthor, String firstNameAuthor){
        for (Loan loan : library.getLoans())
            if (loan.getReader().getFirstName().equalsIgnoreCase(readerFirstName) &&
                    loan.getReader().getLastName().equalsIgnoreCase(readerLastName) &&
                    loan.getBook().getName().equalsIgnoreCase(bookName) &&
                    loan.getBook().getAuthor().getLastName().equalsIgnoreCase(lastNameAuthor) &&
                    loan.getBook().getAuthor().getFirstName().equalsIgnoreCase(firstNameAuthor)
            ) {
                return loan;

            }
        return new Loan();
    }

    /**
     * Method for finding a required book by its name
     */
    public RequiredBook findRequiredBook(Library library, String bookName){
        for (RequiredBook requiredBook : library.getRequiredBooks())
            if (requiredBook.getName().equalsIgnoreCase(bookName)) {
                return requiredBook;
            }
        return new RequiredBook();
    }

    /* Seeing all the books in the library */
    public void seeAllBookInLibrary(Library library){
        findBooksFromLibrary(library);
    }
}
