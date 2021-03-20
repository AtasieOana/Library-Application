package Main;

import Classes.*;
import Services.LibraryService;

import java.io.IOException;
import java.util.Scanner;

public class Main {

    private static int Options() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("1. Add a book in the library");
        System.out.println("2. Remove a book from the library");
        System.out.println("3. See all books written by an author");
        System.out.println("4. See all the books in a section");
        System.out.println("5. See all the books in the library");
        System.out.println("6. Adding a new reader");
        System.out.println("10. End program");
        System.out.println("Choose one of the above options:");

        int opt = scanner.nextInt();
        return opt;
    }

    public static void main(String[] args){

        int opt = Options();
        Scanner scan = new Scanner(System.in);
        LibraryService service = new LibraryService();
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
