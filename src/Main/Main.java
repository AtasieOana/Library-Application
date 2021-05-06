package Main;

import CSVManage.CSVAuditService;
import Classes.*;
import Services.HelperService;
import Services.LibraryService;
import CSVManage.CSVReadWrite;

import java.util.*;

public class Main {

    /**
     * Creating the initial library using the CSV files
     */
    private static Library CreateLibraryFromCSV(){

        Library library = new Library();
        CSVReadWrite read = CSVReadWrite.getInstance();
        /* adding initial librarians */
        ArrayList<Librarian> librarians = read.readObjects("Librarian.csv", "Librarian");
        for(Librarian l: librarians){
            library.addLibrarian(l);
        }
        /* adding initial readers */
        ArrayList<Reader> readers = read.readObjects("Reader.csv", "Reader");
        for(Reader r: readers){
            library.addReader(r);
        }
        /* adding initial authors **/
        ArrayList<LibraryAuthor> libraryAuthors = read.readObjects("LibraryAuthor.csv","LibraryAuthor");
        for(LibraryAuthor la: libraryAuthors){
            library.addAuthor(la);
        }
        /* adding initial sections */
        ArrayList<Section> sections = read.readObjects("Section.csv", "Section");
        for(Section s: sections){
            library.addSection(s);
        }
        /* adding initial books */
        ArrayList<LibraryBook> libraryBooks = read.readObjects("LibraryBook.csv","LibraryBook");
        for(LibraryBook lb: libraryBooks){
            library.addBookInSection(lb.getSection(),lb);
            library.addBookAtAuthor(lb.getAuthor(),lb);
        }
        /* adding initial required book */
        ArrayList<RequiredBook> requiredBook = read.readObjects("RequiredBook.csv","RequiredBook");
        for(RequiredBook rb: requiredBook){
            library.addRequiredBook(rb);
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

        Scanner scan = new Scanner(System.in);
        LibraryService service = new LibraryService(CreateLibraryFromCSV());
        CSVReadWrite write = CSVReadWrite.getInstance();

        try {
            int opt = Options();
            CSVAuditService writeAudit = CSVAuditService.getInstance();
            while (opt != -1) {
                if (opt == 0) {
                    opt = Options();
                }
                if (opt == 1) {
                    Option1(service);
                    writeAudit.writeCSV("Adding a book in the library");
                }
                if (opt == 2) {
                    Option2(service);
                    writeAudit.writeCSV("Removing a book from the library");
                }
                if (opt == 3) {
                    Option3(service);
                    writeAudit.writeCSV("Seeing all books written by an author");
                }
                if (opt == 4){
                    Option4(service);
                    writeAudit.writeCSV("Seeing all the books in a section");

                }
                if(opt == 5){
                    Option5(service);
                    writeAudit.writeCSV("Seeing all the books in the library");
                }
                if(opt == 6){
                    Option6(service);
                    writeAudit.writeCSV("Adding a new reader");
                }
                if(opt == 7){
                    Option7(service);
                    writeAudit.writeCSV("Removing a new reader");
                }
                if(opt == 8){
                    LibraryAuthor author = new LibraryAuthor("Eminescu", "Mihai");
                    service.removeAuthor(author);
                    writeAudit.writeCSV("Removing an author from the library");
                }
                if(opt == 9){
                    Author author = new Author("Christie", "Agatha");
                    String bookName = "The Mysterious Affair at Styles";
                    String firstNameReader = "Valentin";
                    String lastNameReader = "Gica";
                    String firstNameLibrarian = "Mihai";
                    String lastNameLibrarian ="Mircea";
                    int year = 1920;
                    service.borrowBook(bookName, author, firstNameReader, lastNameReader,
                            firstNameLibrarian,lastNameLibrarian,year);
                    writeAudit.writeCSV("Borrowing a book");
                }
                if(opt == 10){
                    String bookName = "The Mysterious Affair at Styles";
                    String firstNameReader = "Valentin";
                    String lastNameReader = "Gica";
                    service.returnBook(bookName, firstNameReader, lastNameReader);
                    writeAudit.writeCSV("Returning a book");
                }
                if(opt == 11){
                    service.findMostRequestedBook();
                    writeAudit.writeCSV("Finding the most requested book");
                }
                if(opt == 12){
                    writeAudit.writeCSV("Ending program");
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
        System.out.println("Enter the birth day of the reader::");
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
}
