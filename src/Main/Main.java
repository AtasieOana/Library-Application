package Main;

import Classes.*;
import Services.LibraryService;

import java.io.IOException;
import java.util.*;

public class Main {

    private static Library CreateLibrary(){

        Library library = new Library();
        Librarian librarian1 = new Librarian("Mircea", "Mihai", new Date(2000, Calendar.DECEMBER,
                21), "Adresa X", 1600);
        Librarian librarian2 = new Librarian("Ionel", "Maria", new Date(1989, Calendar.NOVEMBER,
                06),"Adresa Y", 2000);
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
        System.out.println("10. End program");
        System.out.println("Choose one of the above options:");

        int opt = scanner.nextInt();
        return opt;
    }

    public static void main(String[] args){

        LibraryAuthor author11 = new LibraryAuthor("Christie", "Agatha");
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
                Reader reader = new Reader("Gica","Valentin", new Date(2000, 11, 21),
                        "6130110018580", "Strada X", "0756146777");
                service.addReader(reader);
            }
            if(opt == 7){
                Reader reader = new Reader("Gica","Valentin", new Date(2000, 11, 21),
                        "6130110018580", "Strada X", "0756146777");
                service.removeReader(reader);
            }
            if(opt == 8){
                LibraryAuthor author = new LibraryAuthor("Eminescu", "Mihai");
                service.removeAuthor(author);
            }
            if(opt == 9){
                LibraryAuthor author = new LibraryAuthor("Eminescu", "Mihai");
                String bookName = "Luceafarul";
                String firstNameReader = "Valentin";
                String lastNameReader = "Gica";
                String firstNameLibrarian = "Mihai";
                String lastNameLibrarian ="Mircea";
                service.borrowBook(bookName, author, firstNameReader, lastNameReader,
                        firstNameLibrarian,lastNameLibrarian);
            }

            if( opt == 10){
                opt = -1;
                break;
            }
            if (opt > 10) {
                System.out.println("The entered option is invalid!");
            }

            System.out.print("Press any key to continue . . . ");
            scan.nextLine();
            opt = 0;
        }

    }
}
