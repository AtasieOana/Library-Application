package Main;

import Classes.*;
import Services.LibraryService;

import java.util.*;
import java.util.Calendar;

public class Main {

    private static Library CreateLibrary(){

        Library library = new Library();
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, 2000);
        calendar.set(Calendar.MONTH, 1);
        calendar.set(Calendar.DATE, 21);
        Librarian librarian1 = new Librarian("Mircea", "Mihai", calendar.getTime(),
                "Adresa X", 1600);
        calendar.set(Calendar.YEAR, 1989);
        calendar.set(Calendar.MONTH, 9);
        calendar.set(Calendar.DATE, 6);
        Librarian librarian2 = new Librarian("Ionel", "Maria", calendar.getTime(),
                "Adresa Y", 2000);
        library.addLibrarian(librarian1);
        library.addLibrarian(librarian2);
        LibraryAuthor author = new LibraryAuthor("Christie", "Agatha");
        library.addAuthor(author);
        LibraryAuthor author1 = new LibraryAuthor("Twain", "Mark");
        library.addAuthor(author1);
        Section section = new Section(SectionType.FictionBooks);
        library.addSection(section);
        LibraryBook libraryBook = new LibraryBook( "The Mysterious Affair at Styles", 298,
                1920, "Engleza", author, section, 10);
        LibraryBook libraryBook1 = new LibraryBook( "Eve's Diary", 157,
                1906, "Engleza", author1, section, 1);
        library.addBookAtAuthor(author, libraryBook);
        library.addBookInSection(section, libraryBook);
        library.addBookAtAuthor(author1, libraryBook1);
        library.addBookInSection(section, libraryBook1);
        Author a = new Author("Wilde","Oscar");
        RequiredBook requiredBook = new RequiredBook("The Happy Prince and Other Tales", a,
                1888, 1);
        library.addRequiredBook(requiredBook);
        return library;

    }

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
        LibraryService service = new LibraryService(CreateLibrary());
        int opt = Options();
        while (opt != -1) {
            if (opt == 0) {
                opt = Options();
            }
            if (opt == 1) {
                LibraryAuthor author = new LibraryAuthor("Eminescu", "Mihai");
                Section section = new Section(SectionType.PoemsBook);
                service.addBookInLibrary("Luceafarul", 10, 1883,
                        "Romana", author, section, 4);
            }
            if (opt == 2) {
                LibraryAuthor author = new LibraryAuthor("Eminescu", "Mihai");
                Section section = new Section(SectionType.PoemsBook);
                LibraryBook book = new LibraryBook("Luceafarul", 10, 1883,
                        "Romana", author, section, 4);
                service.removeBookFromLibrary(book);
            }
            if (opt == 3) {
                LibraryAuthor author1 = new LibraryAuthor("Eminescu", "Mihai");
                service.findBooksFromAuthor(author1);
            }
            if (opt == 4){
                Section section = new Section(SectionType.PoemsBook);
                service.findBooksFromSection(section);
            }
            if(opt == 5){
                service.findBooksFromLibrary();
            }
            if(opt == 6 ){
                Calendar calendar = Calendar.getInstance();
                calendar.set(Calendar.YEAR, 2000);
                calendar.set(Calendar.MONTH, 11);
                calendar.set(Calendar.DATE, 21);
                Date date = calendar.getTime();
                Reader reader = new Reader("Gica","Valentin", date,
                        "6130110018580", "Strada X", "0756146777");
                service.addReader(reader);
            }
            if(opt == 7){
                Calendar calendar = Calendar.getInstance();
                calendar.set(Calendar.YEAR, 2000);
                calendar.set(Calendar.MONTH, 11);
                calendar.set(Calendar.DATE, 21);
                Date date = calendar.getTime();
                Reader reader = new Reader("Gica","Valentin", date,
                        "6130110018580", "Strada X", "0756146777");
                service.removeReader(reader);
            }
            if(opt == 8){
                LibraryAuthor author = new LibraryAuthor("Eminescu", "Mihai");
                service.removeAuthor(author);
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
            }
            if(opt == 10){
                String bookName = "The Mysterious Affair at Styles";
                String firstNameReader = "Valentin";
                String lastNameReader = "Gica";
                service.returnBook(bookName, firstNameReader, lastNameReader);
            }
            if(opt == 11){
                service.findMostRequestedBook();
            }
            if(opt == 12){
                break;
            }
            if (opt < 0 || opt > 11) {
                System.out.println("The entered option is invalid!");
            }

            System.out.print("Press any key to continue . . . ");
            scan.nextLine();
            opt = 0;
        }

    }
}
