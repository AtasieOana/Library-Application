package services;

import classes.*;
import repository.LibraryBookRepository;
import repository.RequiredBookRepository;

import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class LoanService {
    private static LoanService INSTANCE = null;
    private static LibraryService libraryService;
    private final LibraryBookRepository libraryBookRepository = new LibraryBookRepository();
    private final RequiredBookRepository requiredBookRepository = new RequiredBookRepository();


    private LoanService(){
        libraryService = LibraryService.getInstance();
    }

    public static synchronized LoanService getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new LoanService();
        }
        return INSTANCE;
    }


    /**
     * Borrowing a book
     * If a book that is not in the library is requested, then it will be added to the list of required books;
     */
    public void borrowBook(String name, LibraryAuthor author, String readerCNP, String librarianCNP, int yearBook){
        // Check if the reader and the librarian are part of the library
        Librarian librarian = libraryService.findLibrarianByCNP(librarianCNP);
        Reader reader =  libraryService.findReaderByCNP(readerCNP);
        if(!(librarian == null) && !(reader == null)) {
            LibraryAuthor la = libraryService.checkAuthor(author);
            if (!(la == null)){
                LibraryBook lb = libraryBookRepository.getLibraryBookByName(name,la.getLastName(),la.getFirstName(),yearBook);
                if(!(lb == null)){
                    if (lb.getNumberOfCopies() >= 1) {
                        System.out.println("The loan will be completed!");
                        lb.setNumberOfCopies(lb.getNumberOfCopies() - 1);
                        Date date = new Date();
                        Loan loan = new Loan(lb, reader, librarian, date);
                        libraryService.addLoan(loan);
                        libraryBookRepository.updateNumberOfCopies(lb.getNumberOfCopies(),lb.getIdLibraryBook());
                    }
                    else {
                        System.out.println("The book is no longer in stock!");
                    }
                } else{
                    System.out.println("The book doesn't exist in the library!");
                    RequiredBook requiredBook = libraryService.findRequiredBook(name);
                    if (requiredBook == null) {
                        RequiredBook requiredBook1 = new RequiredBook(name, author, yearBook, 1);
                        libraryService.addRequiredBook(requiredBook1);
                    } else {
                        requiredBook.increaseTheNumberOfRequests();
                        requiredBookRepository.updateNumberOfRequests(requiredBook.getNumberOfRequests(),requiredBook.getIdRequiredBook());
                    }
                }
            }
            else{
                System.out.println("The author doesn't exist in the library!");
            }
        }
        else{
            if(librarian == null) {
                System.out.println("The librarian is not registered at the library!");
            }
            else{
                System.out.println("The reader is not registered at the library!");
            }
        }
    }

    /**
     *  Returning a book;
     */
    public void returnBook(String name, String lastNameAuthor, String firstNameAuthor,
                           String CNP, int yearBook) {
        Loan loan = libraryService.findLoan(name, CNP, lastNameAuthor, firstNameAuthor,yearBook);
        if (loan == null) {
            System.out.println("Loan doesn't exist!");
        } else {
            Date d1 = new Date();
            long diff = d1.getTime() - loan.getLoanDate().getTime();
            long days_diff = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS) - Loan.getLoanDays();
            System.out.println("The book was returned with a delay of " + days_diff + " days.");
            LibraryBook lb = loan.getBook();
            lb.setNumberOfCopies(lb.getNumberOfCopies() + 1);
            libraryBookRepository.updateNumberOfCopies(lb.getNumberOfCopies(),loan.getBook().getIdLibraryBook());
            libraryService.removeLoan(loan);
        }
    }

    /* Borrowing a book */
    public void borrowBookFromInput() {
        Scanner scan = new Scanner(System.in);
        System.out.println("Enter the author's last name:");
        String authorLastName = scan.nextLine();
        System.out.println("Enter the author's first name:");
        String authorFirstName = scan.nextLine();
        LibraryAuthor author = new LibraryAuthor(authorLastName, authorFirstName);
        System.out.println("Enter the name of the book:");
        String bookName = scan.nextLine();
        System.out.println("Enter the year of publication:");
        int year = scan.nextInt();
        scan.nextLine();
        System.out.println("Enter the reader's CNP:");
        String readerCNP = scan.nextLine();
        System.out.println("Enter the librarian's CNP:");
        String librarianCNP = scan.nextLine();
        borrowBook(bookName, author, readerCNP, librarianCNP, year);
    }

    /* Returning a book */
    public void returnBookFromInput() {
        Scanner scan = new Scanner(System.in);
        System.out.println("Enter the author's last name:");
        String authorLastName = scan.nextLine();
        System.out.println("Enter the author's first name:");
        String authorFirstName = scan.nextLine();
        System.out.println("Enter the name of the book:");
        String bookName = scan.nextLine();
        System.out.println("Enter the year of publication:");
        int year = scan.nextInt();
        scan.nextLine();
        System.out.println("Enter the reader's CNP:");
        String CNP = scan.nextLine();
        returnBook(bookName, authorLastName, authorFirstName, CNP, year);
    }


}
