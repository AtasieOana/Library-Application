package services;

import classes.*;
import csvManage.csvReadWrite.LibraryBookReadWrite;
import csvManage.csvReadWrite.RequiredBookReadWrite;

import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;
import java.util.TreeSet;
import java.util.concurrent.TimeUnit;

public class LoanService {
    private static LoanService INSTANCE = null;
    private static LibraryService libraryService;
    private final LibraryAuthorService libraryAuthorService;
    private final RequiredBookReadWrite requiredBookReadWrite;
    private final LibraryBookReadWrite libraryBookReadWrite;


    private LoanService(){
        libraryService = LibraryService.getInstance();
        libraryAuthorService = LibraryAuthorService.getInstance();
        requiredBookReadWrite = RequiredBookReadWrite.getInstance();
        libraryBookReadWrite = LibraryBookReadWrite.getInstance();
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
    public void borrowBook(Library library, String name, Author author, String firstNameReader, String lastNameReader,
                           String firstNameLibrarian, String lastNameLibrarian, int yearBook){
        // Check if the reader and the librarian are part of the library
        Librarian librarian = libraryService.findLibrarianByName(library,lastNameLibrarian,firstNameLibrarian);
        Reader reader =  libraryService.findReaderByName(library, lastNameReader, firstNameReader);
        if(!librarian.getLastName().equals("") && !reader.getLastName().equals("")) {
            boolean found = false;
            boolean stock = true;
            LibraryAuthor libraryAuthor = libraryService.checkAuthor(library, author);
            LibraryBook book = new LibraryBook();
            if (!libraryAuthor.equals(new LibraryAuthor())){
                TreeSet<LibraryBook> libraryBookTreeSet = libraryAuthorService.findBooksFromAuthor(library,libraryAuthor);
                for (LibraryBook libraryBook : libraryBookTreeSet) {
                    if (libraryBook.getName().equalsIgnoreCase(name) && libraryBook.getYearOfPublication() == yearBook) {
                        if (libraryBook.getNumberOfCopies() >= 1) {
                            System.out.println("The loan will be completed!");
                            found = true;
                            book = libraryBook;
                            libraryBookReadWrite.updateNumberInCSV(libraryBook,"Minus");
                            libraryBook.setNumberOfCopies(libraryBook.getNumberOfCopies() - 1);
                        } else {
                            stock = false;
                            System.out.println("The book is no longer in stock!");
                        }
                        break;
                    }
                }
            }
            if(found){
                Calendar calendar = Calendar.getInstance();
                calendar.set(Calendar.YEAR, 2021);
                calendar.set(Calendar.MONTH, 2);
                calendar.set(Calendar.DATE, 14);
                Date date = calendar.getTime();
                Loan loan = new Loan(book, reader, librarian, date);
                libraryService.addLoan(library, loan);
            }
            else{
                if(stock) {
                    System.out.println("The book doesn't exit in the library!");
                    RequiredBook requiredBook = libraryService.findRequiredBook(library, name);
                    if (requiredBook.getName().equals("")) {
                        RequiredBook requiredBook1 = new RequiredBook(name, author, yearBook, 1);
                        libraryService.addRequiredBook(library,requiredBook1);
                        requiredBookReadWrite.writeObjects(requiredBook1);
                    } else {
                        requiredBookReadWrite.updateNumberInCSV(requiredBook, "Add");
                        requiredBook.increaseTheNumberOfRequests();
                    }
                }
            }
        }
        else{
            if(librarian.getLastName().equals("")) {
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
    public void returnBook(Library library, String name, String lastNameAuthor, String firstNameAuthor,
                           String lastNameReader, String firstNameReader) {
        Loan loan = libraryService.findLoan(library,firstNameReader, lastNameReader, name, lastNameAuthor, firstNameAuthor);
        if (loan.getLoanDate() == null) {
            System.out.println("Loan doesn't exist!");
        } else {
            Date d1 = new Date();
            long diff = d1.getTime() - loan.getLoanDate().getTime();
            long days_diff = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS) - Loan.getLoanDays();
            System.out.println("The book was returned with a delay of " + days_diff + " days.");
            libraryBookReadWrite.updateNumberInCSV( loan.getBook(),"Add");
            loan.getBook().setNumberOfCopies(loan.getBook().getNumberOfCopies() + 1);
            libraryService.removeLoan(library,loan);
        }
    }

    /* Borrowing a book */
    public void borrowBookFromInput(Library library) {
        Scanner scan = new Scanner(System.in);
        System.out.println("Enter the author's last name:");
        String authorLastName = scan.nextLine();
        System.out.println("Enter the author's first name:");
        String authorFirstName = scan.nextLine();
        Author author = new Author(authorLastName, authorFirstName);
        System.out.println("Enter the name of the book:");
        String bookName = scan.nextLine();
        System.out.println("Enter the year of publication:");
        int year = scan.nextInt();
        scan.nextLine();
        System.out.println("Enter the reader's last name:");
        String lastNameReader = scan.nextLine();
        System.out.println("Enter the reader's first name:");
        String firstNameReader = scan.nextLine();
        System.out.println("Enter the librarian's last name:");
        String lastNameLibrarian = scan.nextLine();
        System.out.println("Enter the librarian's first name:");
        String firstNameLibrarian = scan.nextLine();
        borrowBook(library,bookName, author, firstNameReader, lastNameReader, firstNameLibrarian, lastNameLibrarian, year);
    }

    /* Returning a book */
    public void returnBookFromInput(Library library) {
        Scanner scan = new Scanner(System.in);
        System.out.println("Enter the author's last name:");
        String authorLastName = scan.nextLine();
        System.out.println("Enter the author's first name:");
        String authorFirstName = scan.nextLine();
        System.out.println("Enter the name of the book:");
        String bookName = scan.nextLine();
        System.out.println("Enter the reader's last name:");
        String lastNameReader = scan.nextLine();
        System.out.println("Enter the reader's first name:");
        String firstNameReader = scan.nextLine();
        returnBook(library,bookName, authorLastName, authorFirstName, lastNameReader, firstNameReader);
    }


}
