package services;

import classes.*;
import classes.Reader;
import repository.*;


public class LibraryService {

    private static LibraryService INSTANCE = null;
    private static final SectionService sectionService = SectionService.getInstance();
    private static final LibraryAuthorService libraryAuthorService = LibraryAuthorService.getInstance();
    private final LibrarianRepository librarianRepository = new LibrarianRepository();
    private final ReaderRepository readerRepository = new ReaderRepository();
    private final SectionRepository sectionRepository = new SectionRepository();
    private final LibraryAuthorRepository libraryAuthorRepository = new LibraryAuthorRepository();
    private final LibraryBookRepository libraryBookRepository = new LibraryBookRepository();
    private final LoanRepository loanRepository = new LoanRepository();
    private final RequiredBookRepository requiredBookRepository = new RequiredBookRepository();



    public static synchronized LibraryService getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new LibraryService();
        }
        return INSTANCE;
    }


    /** See all the books in the library */
    public void findBooksFromLibrary() {
        libraryBookRepository.displayAllBooks();
    }


    /**
     * Method for adding a new section to the library
     **/
    public void addSection(Section section) {
        sectionRepository.insertSectionInDatabase(section);
    }

    /**
     * Method for adding a new required book to the library
     **/
    public void addRequiredBook(RequiredBook requiredBook) {
        AuthorRepository authorRepository = new AuthorRepository();
        Author author = authorRepository.getAuthorByName(requiredBook.getAuthor().getLastName(), requiredBook.getAuthor().getFirstName());
        if( author == null) {
            author = authorRepository.insertAuthorInDatabase(requiredBook.getAuthor());
        }
        requiredBook.setAuthor(author);
        requiredBookRepository.insertRequiredBookInDatabase(requiredBook);
    }

    /**
     * Method for adding a new loan to the library
     **/
    public void addLoan(Loan loan) {
        loanRepository.insertLoanInDatabase(loan);
    }

    /**
     * Method for adding a new librarian to the library
     **/
    public void addLibrarian(Librarian librarian) {
        librarianRepository.insertLibrarianInDatabase(librarian);
    }

    /**
     * Method for adding a new reader to the library
     **/
    public void addReader(Reader reader) {
        readerRepository.insertReaderInDatabase(reader);
    }

    /**
     * Method for removing a loan
     **/
    public void removeLoan(Loan loan) {
        loanRepository.removeLoanFromDatabase(loan.getIdLoan());
    }

    /**
     * Method for finding a section in the library
     **/
    public Boolean findSection(Section section) {
        Section sectionFind = sectionRepository.getSectionFromDatabase(section.getSectionType().toString());
        if(sectionFind == null){
            return Boolean.FALSE;
        }
        else{
            return Boolean.TRUE;
        }
    }

    /**
     * Method for adding a new author to the library
     **/
    public LibraryAuthor addAuthor(LibraryAuthor libraryAuthor) {
        return libraryAuthorRepository.insertLibraryAuthorInDatabase(libraryAuthor);
    }

    /**
     * Method for adding a new book to the library
     **/
    public void addBook(LibraryBook libraryBook) {
        libraryBookRepository.insertLibraryBookInDatabase(libraryBook);
    }

    /**
     * Method for finding a author in the library
     **/
    public Boolean findAuthor(LibraryAuthor libraryAuthor) {
        LibraryAuthor authorFind = libraryAuthorRepository.getLibraryAuthorByName(libraryAuthor.getLastName(),libraryAuthor.getFirstName());
        if(authorFind == null){
            return Boolean.FALSE;
        }
        else{
            return Boolean.TRUE;
        }
    }

    /**
     * Method to check if there is an author in the library
     */
    public LibraryAuthor checkAuthor(LibraryAuthor libraryAuthor){
        return libraryAuthorRepository.getLibraryAuthorByName(libraryAuthor.getLastName(),libraryAuthor.getFirstName());
    }

    /**
     * Method to find a reader by its name
     */
    public Reader findReaderByCNP(String CNP) {
        return readerRepository.getReaderFromDatabase(CNP);
    }

    /**
     * Method to find a librarian by its CNP
     */
    public Librarian findLibrarianByCNP(String CNP) {
        return librarianRepository.getLibrarianFromDatabase(CNP);
    }

    /**
     * Method for finding a loan by the reader's name and the name of the book
     */
    public Loan findLoan(String bookName, String CNP, String lastNameAuthor, String firstNameAuthor, int year){
       LibraryBook lb = libraryBookRepository.getLibraryBookByName(bookName,lastNameAuthor,firstNameAuthor,year);
       if(lb == null){
           return null;
       }
       return loanRepository.getLoanFromDatabaseByParameters(lb.getIdLibraryBook(),CNP);
    }

    /**
     * Method for finding a required book by its name
     */
    public RequiredBook findRequiredBook(String bookName){
        return requiredBookRepository.getRequiredBookByName(bookName);
    }

    /* Seeing all the books in the library */
    public void seeAllBookInLibrary(){
        findBooksFromLibrary();
    }
}
