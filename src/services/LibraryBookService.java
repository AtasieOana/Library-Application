package services;

import classes.*;

import repository.LibraryAuthorRepository;
import repository.LibraryBookRepository;
import repository.SectionRepository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class LibraryBookService {
    private static LibraryBookService INSTANCE = null;
    private static LibraryService libraryService;
    private final LibraryBookRepository libraryBookRepository = new LibraryBookRepository();
    private final LibraryAuthorRepository libraryAuthorRepository = new LibraryAuthorRepository();
    private final SectionRepository sectionRepository = new SectionRepository();



    private LibraryBookService() {
        libraryService = LibraryService.getInstance();
    }
    public static synchronized LibraryBookService getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new LibraryBookService();
        }
        return INSTANCE;
    }

    public ArrayList<LibraryBook> createFirstBooks() {
        LibraryAuthor libraryAuthor = libraryAuthorRepository.getLibraryAuthorByName("Christie","Agatha");
        if(libraryAuthor == null){
            libraryAuthor = new LibraryAuthor("Christie","Agatha");
            libraryAuthorRepository.insertLibraryAuthorInDatabase(libraryAuthor);
        }
        LibraryAuthor libraryAuthor1 = libraryAuthorRepository.getLibraryAuthorByName("Twain","Mark");
        if(libraryAuthor1 == null){
            libraryAuthor1 = new LibraryAuthor("Twain","Mark");
            libraryAuthorRepository.insertLibraryAuthorInDatabase(libraryAuthor1);
        }
        Section section = sectionRepository.getSectionFromDatabase(String.valueOf(SectionType.FICTIONAL));
        if(section == null){
            section = new Section(SectionType.FICTIONAL);
            sectionRepository.insertSectionInDatabase(section);
        }
        LibraryBook libraryBook = new LibraryBook( "The Mysterious Affair at Styles", 298,
                1920, "English", libraryAuthor, section, 10);
        LibraryBook libraryBook1 = new LibraryBook( "Eve's Diary", 157,
                1906, "English", libraryAuthor1, section, 1);
        return new ArrayList<>(Arrays.asList(libraryBook,libraryBook1));
    }

    public void addInitialLibraryBooks(){
        ArrayList<LibraryBook> libraryBooks = createFirstBooks();
        for(LibraryBook lb: libraryBooks){
            libraryService.addBook(lb);
        }
    }

    /** Adding a book in the library;
     * If the author of the book is not in the library, then it is added.
     * If the section is not in the library, then it is added.
     * The book is added to the author and the corresponding section.
     */
    public void addBookInLibrary(String name, int numberOfPages, int yearOfPublication, String language,
                                 LibraryAuthor libraryAuthor, Section section, int numberOfCopies){
        if(!libraryService.findSection(section)){
            libraryService.addSection(section);
        }
        LibraryAuthor authorDB;
        if(!libraryService.findAuthor(libraryAuthor)){
            authorDB = libraryService.addAuthor(libraryAuthor);
        }else{
            authorDB = libraryAuthorRepository.getLibraryAuthorByName(libraryAuthor.getLastName(),libraryAuthor.getFirstName());
        }
        LibraryBook book = new LibraryBook(name, numberOfPages, yearOfPublication, language, authorDB,
                section, numberOfCopies);
        if(libraryBookRepository.getLibraryBookByName(name,libraryAuthor.getLastName(),libraryAuthor.getFirstName(),yearOfPublication)!=null){
            System.out.println("The book is already in library!");
        }else {
            libraryBookRepository.insertLibraryBookInDatabase(book);
        }
    }

    /** Remove a book from the library;
     * If the author of the book has no other book in the library then it will be deleted.
     * The book will be removed from the section to which it belonged.
     */
    public void removeBookFromLibrary(String name, String firstName, String lastName, int year){
        LibraryBook lb = libraryBookRepository.getLibraryBookByName(name,lastName,firstName,year);
        if(lb == null){
            return;
        }
        int idAuthor = lb.getAuthor().getIdAuthor();
        libraryBookRepository.removeLibraryBookFromDatabase(lb.getIdLibraryBook());
        if(libraryAuthorRepository.findAllBookFromAuthor(idAuthor) == null){
            libraryAuthorRepository.removeLibraryAuthorFromDatabase(idAuthor);
        }
    }

    /* Adding a book in the library read from console */
    public void addBookFromInput(){
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
        addBookInLibrary(name, numberOfPages, year, language, author, section, numberOfCopies);
    }

    /* Removing a book from the library read from console */
    public void removeBookFromInput(){
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
        removeBookFromLibrary(name, authorFirstName, authorLastName, year);
    }

}
