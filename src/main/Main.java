package main;

import csvManage.AuditService;
import classes.*;
import services.HelperService;
import services.LibraryService;
import csvManage.CSVReadWrite;

import java.util.*;

public class Main {

    /**
     * Creating the initial library using the CSV files
     */
    private static Library CreateLibraryFromCSV(){

        Library library = new Library();
        CSVReadWrite readWrite = CSVReadWrite.getInstance();
        /* adding initial librarians */
        if(HelperService.checkIfExists("LibrarianWrite.csv")) {
            ArrayList<Librarian> librarians = readWrite.readObjects("Librarian.csv", "Librarian");
            for(Librarian l: librarians){
                library.addLibrarian(l);
                readWrite.writeCSV("LibrarianWrite.csv",l);
            }
        }else {
            ArrayList<Librarian> librarians = readWrite.readObjects("LibrarianWrite.csv", "Librarian");
            for(Librarian l: librarians){
                library.addLibrarian(l);
            }
        }
        /* adding initial readers */
        if(HelperService.checkIfExists("ReaderWrite.csv")) {
            ArrayList<Reader> readers = readWrite.readObjects("Reader.csv", "Reader");
            for(Reader r: readers){
                library.addReader(r);
                readWrite.writeCSV("ReaderWrite.csv",r);
            }
        }else {
            ArrayList<Reader> readers = readWrite.readObjects("ReaderWrite.csv", "Reader");
            for(Reader r: readers){
                library.addReader(r);
            }
        }
        /* adding initial authors **/
        if(HelperService.checkIfExists("LibraryAuthorWrite.csv")) {
            ArrayList<LibraryAuthor> libraryAuthors = readWrite.readObjects("LibraryAuthor.csv","LibraryAuthor");
            for(LibraryAuthor la: libraryAuthors){
                library.addAuthor(la);
                readWrite.writeCSV("LibraryAuthorWrite.csv",la);
            }
        }else {
            ArrayList<LibraryAuthor> libraryAuthors = readWrite.readObjects("LibraryAuthorWrite.csv","LibraryAuthor");
            for(LibraryAuthor la: libraryAuthors){
                library.addAuthor(la);
            }
        }
        /* adding initial sections */
        if(HelperService.checkIfExists("SectionWrite.csv")) {
            ArrayList<Section> sections = readWrite.readObjects("Section.csv", "Section");
            for(Section s: sections){
                library.addSection(s);
                readWrite.writeCSV("SectionWrite.csv",s);
            }
        }else {
            ArrayList<Section> sections = readWrite.readObjects("SectionWrite.csv", "Section");
            for(Section s: sections){
                library.addSection(s);
            }
        }
        /* adding initial books */
        if(HelperService.checkIfExists("LibraryBookWrite.csv")) {
            ArrayList<LibraryBook> libraryBooks = readWrite.readObjects("LibraryBook.csv","LibraryBook");
            for(LibraryBook lb: libraryBooks){
                readWrite.writeCSV("LibraryBookWrite.csv",lb);
                library.addBookInSection(lb.getSection(),lb);
                library.addBookAtAuthor(lb.getAuthor(),lb);
            }
        }else {
            ArrayList<LibraryBook> libraryBooks = readWrite.readObjects("LibraryBookWrite.csv","LibraryBook");
            for(LibraryBook lb: libraryBooks){
                library.addBookInSection(lb.getSection(),lb);
                library.addBookAtAuthor(lb.getAuthor(),lb);
            }
        }
        /* adding initial required book */
        if(HelperService.checkIfExists("RequiredBookWrite.csv")) {
            ArrayList<RequiredBook> requiredBook = readWrite.readObjects("RequiredBook.csv","RequiredBook");
            for(RequiredBook rb: requiredBook){
                library.addRequiredBook(rb);
                readWrite.writeCSV("RequiredBookWrite.csv",rb);
            }
        }else {
            ArrayList<RequiredBook> requiredBook = readWrite.readObjects("RequiredBookWrite.csv","RequiredBook");
            for(RequiredBook rb: requiredBook){
                library.addRequiredBook(rb);
            }
        }
        return library;
    }

    /**
     * Illustrating the actions that can be done in the library
     */
    private static int Options() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("1. Add a book in the library");
        System.out.println("2. Remove a book from the library");
        System.out.println("3. See all books written by an author");
        System.out.println("4. See all the books in a section");
        System.out.println("5. See all the books in the library");
        System.out.println("6. Add a new reader");
        System.out.println("7. Remove a reader");
        System.out.println("8. Remove an author from the library");
        System.out.println("9. Borrow a book;");
        System.out.println("10. Return a book");
        System.out.println("11. Find the most requested book;");
        System.out.println("12. End program");
        System.out.println("Choose one of the above options:");

        return scanner.nextInt();
    }

    public static void main(String[] args){
        //filesManage();
        Scanner scan = new Scanner(System.in);
        LibraryService service = new LibraryService(CreateLibraryFromCSV());
        CSVReadWrite write = CSVReadWrite.getInstance();

        try {
            int opt = Options();
            AuditService writeAudit = AuditService.getInstance();
            while (opt != -1) {
                if (opt == 0) {
                    opt = Options();
                }
                if (opt == 1) {
                    Option1(service);
                    writeAudit.logAction("Adding a book in the library");
                }
                if (opt == 2) {
                    Option2(service);
                    writeAudit.logAction("Removing a book from the library");
                }
                if (opt == 3) {
                    Option3(service);
                    writeAudit.logAction("Seeing all books written by an author");
                }
                if (opt == 4){
                    Option4(service);
                    writeAudit.logAction("Seeing all the books in a section");

                }
                if(opt == 5){
                    Option5(service);
                    writeAudit.logAction("Seeing all the books in the library");
                }
                if(opt == 6){
                    Option6(service);
                    writeAudit.logAction("Adding a new reader");
                }
                if(opt == 7){
                    Option7(service);
                    writeAudit.logAction("Removing a new reader");
                }
                if(opt == 8){
                    Option8(service);
                    writeAudit.logAction("Removing an author from the library");
                }
                if(opt == 9){
                    Option9(service);
                    writeAudit.logAction("Borrowing a book");
                }
                if(opt == 10){
                    Option10(service);
                    writeAudit.logAction("Returning a book");
                }
                if(opt == 11){
                    Option11(service);
                    writeAudit.logAction("Finding the most requested book");
                }
                if(opt == 12){
                    writeAudit.logAction("Ending program");
                    break;
                }
                if (opt < 0 || opt > 12) {
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

    /* Adding a book in the library */
    private static void Option1(LibraryService service){
        Scanner scan = new Scanner(System.in);
        System.out.println("Enter the name of the book:");
        String name = scan.nextLine();
        System.out.println("Enter the number of pages in the book:");
        int numberOfPages = scan.nextInt();
        System.out.println("Enter the year of publication:");
        int year = scan.nextInt();
        scan.nextLine();
        System.out.println("Enter the language of the book:");
        String language = scan.nextLine();
        System.out.println("Enter the author's last name:");
        String authorLastName = scan.nextLine();
        System.out.println("Enter the author's first name:");
        String authorFirstName = scan.nextLine();
        LibraryAuthor author = new LibraryAuthor(authorLastName, authorFirstName);
        System.out.println("Enter the section type:");
        String sectionType = scan.nextLine();
        Section section = HelperService.createSectionWithSectionType(sectionType);
        System.out.println("Enter the number of copies:");
        int numberOfCopies = scan.nextInt();
        scan.nextLine();
        service.addBookInLibrary(name, numberOfPages, year, language, author, section, numberOfCopies);
    }

    /* Removing a book from the library */
    private static void Option2(LibraryService service){
        Scanner scan = new Scanner(System.in);
        System.out.println("Enter the name of the book:");
        String name = scan.nextLine();
        System.out.println("Enter the author's last name:");
        String authorLastName = scan.nextLine();
        System.out.println("Enter the author's first name:");
        String authorFirstName = scan.nextLine();
        System.out.println("Enter the year of publication:");
        int year = scan.nextInt();
        scan.nextLine();
        service.removeBookFromLibrary(name, authorFirstName, authorLastName, year);
    }

    /* Seeing all books written by an author */
    private static void Option3(LibraryService service){
        Scanner scan = new Scanner(System.in);
        System.out.println("Enter the author's last name:");
        String authorLastName = scan.nextLine();
        System.out.println("Enter the author's first name:");
        String authorFirstName = scan.nextLine();
        LibraryAuthor author = new LibraryAuthor(authorLastName, authorFirstName);
        service.findBooksFromAuthor(author);
    }

    /* Seeing all the books in a section */
    private static void Option4(LibraryService service){
        Scanner scan = new Scanner(System.in);
        System.out.println("Enter the section type:");
        String sectionType = scan.nextLine();
        Section section = HelperService.createSectionWithSectionType(sectionType);
        service.findBooksFromSection(section);
    }

    /* Seeing all the books in the library */
    private static void Option5(LibraryService service){
        service.findBooksFromLibrary();
    }

    /* Adding a new reader*/
    private static void Option6(LibraryService service) {
        Scanner scan = new Scanner(System.in);
        System.out.println("Enter the reader's last name:");
        String lastName = scan.nextLine();
        System.out.println("Enter the reader's first name:");
        String firstName = scan.nextLine();
        System.out.println("Enter the birth year of the reader:");
        int year = scan.nextInt();
        scan.nextLine();
        System.out.println("Enter the birth month of the reader:");
        int month = scan.nextInt();
        scan.nextLine();
        System.out.println("Enter the birth day of the reader:");
        int day = scan.nextInt();
        scan.nextLine();
        Date date = HelperService.makeDate(year, month, day);
        System.out.println("Enter the reader's CNP:");
        String CNP = scan.nextLine();
        System.out.println("Enter the reader's address:");
        String address = scan.nextLine();
        System.out.println("Enter the reader's phone number:");
        String phoneNumber = scan.nextLine();
        Reader reader = new Reader(lastName,firstName, date, CNP, address, phoneNumber);
        service.addReader(reader);
    }

    /* Removing a reader*/
    private static void Option7(LibraryService service) {
        Scanner scan = new Scanner(System.in);
        System.out.println("Enter the reader's last name:");
        String lastName = scan.nextLine();
        System.out.println("Enter the reader's first name:");
        String firstName = scan.nextLine();
        System.out.println("Enter the reader's CNP:");
        String CNP = scan.nextLine();
        service.removeReader(lastName, firstName, CNP);
    }

    /* Removing an author from the library */
    private static void Option8(LibraryService service){
        Scanner scan = new Scanner(System.in);
        System.out.println("Enter the author's last name:");
        String authorLastName = scan.nextLine();
        System.out.println("Enter the author's first name:");
        String authorFirstName = scan.nextLine();
        LibraryAuthor author = new LibraryAuthor(authorLastName, authorFirstName);
        service.removeAuthor(author);
    }

    /* Borrowing a book */
    private static void Option9(LibraryService service) {
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
        service.borrowBook(bookName, author, firstNameReader, lastNameReader, firstNameLibrarian, lastNameLibrarian, year);
    }

    private static void Option10(LibraryService service) {
        Scanner scan = new Scanner(System.in);
        System.out.println("Enter the author's last name:");
        String authorLastName = scan.nextLine();
        System.out.println("Enter the author's first name:");
        String authorFirstName = scan.nextLine();
        Author author = new Author(authorLastName, authorFirstName);
        System.out.println("Enter the name of the book:");
        String bookName = scan.nextLine();
        System.out.println("Enter the reader's last name:");
        String lastNameReader = scan.nextLine();
        System.out.println("Enter the reader's first name:");
        String firstNameReader = scan.nextLine();
        service.returnBook(bookName, authorLastName, authorFirstName, lastNameReader, firstNameReader);
    }

    /* Finding the most requested book */
    private static void Option11(LibraryService service) {
        service.findMostRequestedBook();
    }


}
