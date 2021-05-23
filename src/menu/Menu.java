package menu;

import config.DatabaseSetup;
import csvManage.audit.AuditService;
import services.*;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Menu {

    private static SectionService sectionService;
    private static ReaderService readerService;
    private static AuditService writeAudit;
    private static RequiredBookService requiredBookService;
    private static LibraryAuthorService libraryAuthorService;
    private static LibraryBookService libraryBookService;
    private static LoanService loanService;
    private static LibraryService libraryService;
    private static DatabaseSetup databaseSetup;
    private static LibrarianService librarianService;


    private static int BookOptions(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("1. Add a book in the library");
        System.out.println("2. Remove a book from the library");
        System.out.println("3. See all books written by an author");
        System.out.println("4. See all the books in a section");
        System.out.println("5. See all the books in the library");
        System.out.println("6. Borrow a book;");
        System.out.println("7. Return a book");
        System.out.println("8. Find the most requested book;");
        System.out.println("9. Remove the required books with the fewest requests");
        System.out.println("10. Remove an author from the library");
        System.out.println("Choose one of the above options:");
        return scanner.nextInt();
    }

    private static int ReaderOptions(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("1. Add a new reader");
        System.out.println("2. Remove a reader");
        System.out.println("3. Update last name for reader;");
        System.out.println("Choose one of the above options:");
        return scanner.nextInt();
    }

    private static int LibrarianOptions(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("1. Add a librarian in the library;");
        System.out.println("2. Remove a librarian from the library;");
        System.out.println("3. Update last name for librarian;");
        System.out.println("Choose one of the above options:");
        return scanner.nextInt();
    }

    /**
     * Illustrating the actions that can be done in the library
     */
    private static int Options() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("1. Options for books (add/remove/borrow/return/view)");
        System.out.println("2. Options for readers (add/remove/modify)");
        System.out.println("3. Options for librarians (add/remove/modify)");
        System.out.println("4. End program");
        System.out.println("Choose one of the above options:");
        return scanner.nextInt();
    }

    private static void initialise(){
        sectionService = SectionService.getInstance();
        readerService = ReaderService.getInstance();
        writeAudit = AuditService.getInstance();
        requiredBookService = RequiredBookService.getInstance();
        libraryAuthorService = LibraryAuthorService.getInstance();
        loanService = LoanService.getInstance();
        libraryService = LibraryService.getInstance();
        libraryBookService = LibraryBookService.getInstance();
        databaseSetup = new DatabaseSetup();
        librarianService = LibrarianService.getInstance();
    }

    public static void BooksMenu(){
        int opt = BookOptions();
        if (opt == 1) {
            libraryBookService.addBookFromInput();
            writeAudit.logAction("Adding a book in the library");
        }
        if (opt == 2) {
            libraryBookService.removeBookFromInput();
            writeAudit.logAction("Removing a book from the library");
        }
        if (opt == 3) {
            libraryAuthorService.seeAllBooks();
            writeAudit.logAction("Seeing all books written by an author");
        }
        if (opt == 4){
            sectionService.seeAllBooks();
            writeAudit.logAction("Seeing all the books in a section");
        }
        if(opt == 5){
            libraryService.seeAllBookInLibrary();
            writeAudit.logAction("Seeing all the books in the library");
        }
        if(opt == 6){
            loanService.borrowBookFromInput();
            writeAudit.logAction("Borrowing a book");
        }
        if(opt == 7){
            loanService.returnBookFromInput();
            writeAudit.logAction("Returning a book");
        }
        if(opt == 8){
            requiredBookService.findMostRequestedBook();
            writeAudit.logAction("Finding the most requested book");
        }
        if(opt == 9){
            requiredBookService.removeRequestBooks();
            writeAudit.logAction("Removing the required books with the fewest requests");
        }
        if(opt == 10){
            libraryAuthorService.removeAuthorFromInput();
            writeAudit.logAction("Removing an author from the library");
        }
    }

    public static void ReadersMenu() {
        int opt = ReaderOptions();
        if(opt == 1){
            readerService.addNewReaderFromInput();
            writeAudit.logAction("Adding a new reader");
        }
        if(opt == 2){
            readerService.removeReaderFromInput();
            writeAudit.logAction("Removing a reader");
        }
        if(opt == 3){
            readerService.modifyReaderFromInput();
            writeAudit.logAction("Updating last name for reader");
        }
    }

    public static void LibrariansMenu() {
        int opt = LibrarianOptions();
        if(opt == 1){
            librarianService.addNewLibrarianFromInput();
            writeAudit.logAction("Adding a new librarian");
        }
        if(opt == 2){
            librarianService.removeLibrarianFromInput();
            writeAudit.logAction("Removing a librarian");
        }
        if(opt == 3){
            librarianService.modifyLibrarianFromInput();
            writeAudit.logAction("Updating last name for librarian");
        }
    }


    public static void startApp(){
        Scanner scan = new Scanner(System.in);
        initialise();
        databaseSetup.setUp();
        try {
            int opt = Options();
            while (opt != -1) {
                if (opt == 0) {
                    opt = Options();
                }
                if (opt == 1) {
                    BooksMenu();
                }
                if (opt == 2) {
                    ReadersMenu();
                }
                if (opt == 3) {
                    LibrariansMenu();
                }
                if(opt == 4){
                    writeAudit.logAction("Ending program");
                    break;
                }
                if (opt < 0 || opt > 4) {
                    System.out.println("The entered option is invalid!");
                }

                System.out.print("Write the word 'next' to continue . . . \n");
                String aux = scan.nextLine();
                while (!aux.equals("next"))
                {
                    System.out.print("Write the word 'next' to continue!\n");
                    aux = scan.nextLine();
                }
                opt = 0;
            }
        }

        catch (InputMismatchException exception) {
            System.out.println("Invalid input! You have to enter a number");
        }

    }

}
