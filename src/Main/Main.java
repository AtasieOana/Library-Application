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
        ArrayList<Librarian> librarians = CSVReadWrite.readObjects("Librarian.csv", "Librarian");
        for(Librarian l: librarians){
            library.addLibrarian(l);
        }
        /* adding initial readers */
        ArrayList<Reader> readers = CSVReadWrite.readObjects("Reader.csv", "Reader");
        for(Reader r: readers){
            library.addReader(r);
        }
        /* adding initial authors **/
        ArrayList<LibraryAuthor> libraryAuthors = CSVReadWrite.readObjects("LibraryAuthor.csv","LibraryAuthor");
        for(LibraryAuthor la: libraryAuthors){
            library.addAuthor(la);
        }
        /* adding initial sections */
        ArrayList<Section> sections = CSVReadWrite.readObjects("Section.csv", "Section");
        for(Section s: sections){
            library.addSection(s);
        }
        /* adding initial books */
        ArrayList<LibraryBook> libraryBooks = CSVReadWrite.readObjects("LibraryBook.csv","LibraryBook");
        for(LibraryBook lb: libraryBooks){
            library.addBookInSection(lb.getSection(),lb);
            library.addBookAtAuthor(lb.getAuthor(),lb);
        }
        /* adding initial required book */
        ArrayList<RequiredBook> requiredBook = CSVReadWrite.readObjects("RequiredBook.csv","RequiredBook");
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
                    LibraryAuthor author = new LibraryAuthor("Eminescu", "Mihai");
                    Section section = new Section(SectionType.POEMS);
                    service.addBookInLibrary("Luceafarul", 10, 1883,
                            "Romana", author, section, 4);
                    writeAudit.writeCSV("Adding a book in the library");
                }
                if (opt == 2) {
                    LibraryAuthor author = new LibraryAuthor("Eminescu", "Mihai");
                    Section section = new Section(SectionType.POEMS);
                    LibraryBook book = new LibraryBook("Luceafarul", 10, 1883,
                            "Romana", author, section, 4);
                    service.removeBookFromLibrary(book);
                    writeAudit.writeCSV("Removing a book from the library");
                }
                if (opt == 3) {
                    LibraryAuthor author1 = new LibraryAuthor("Eminescu", "Mihai");
                    service.findBooksFromAuthor(author1);
                    writeAudit.writeCSV("See all books written by an author");
                }
                if (opt == 4){
                    Section section = new Section(SectionType.POEMS);
                    service.findBooksFromSection(section);
                    writeAudit.writeCSV("See all the books in a section");

                }
                if(opt == 5){
                    service.findBooksFromLibrary();
                    writeAudit.writeCSV("See all the books in the library");
                }
                if(opt == 6 ){
                    Reader reader = new Reader("Gica","Valentin", HelperService.makeDate(2000, 11, 21),
                            "6130110018580", "Strada X", "0756146777");
                    service.addReader(reader);
                    writeAudit.writeCSV("Adding a new reader");
                }
                if(opt == 7){
                    Reader reader = new Reader("Gica","Valentin", HelperService.makeDate(2000, 11, 21),
                            "6130110018580", "Strada X", "0756146777");
                    service.removeReader(reader);
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
}
