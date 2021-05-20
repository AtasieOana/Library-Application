package services;

import classes.Library;
import classes.LibraryAuthor;
import classes.LibraryBook;
import classes.Section;
import csvManage.csvReadWrite.LibraryAuthorReadWrite;
import csvManage.csvReadWrite.LibraryBookReadWrite;
import csvManage.csvReadWrite.SectionReadWrite;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.TreeSet;

public class LibraryBookService {
    private static LibraryBookService INSTANCE = null;
    private final LibraryBookReadWrite libraryBookReadWrite;
    private final SectionReadWrite sectionReadWrite;
    private final LibraryAuthorReadWrite libraryAuthorReadWrite;
    private final SectionService sectionService;
    private final LibraryAuthorService libraryAuthorService;
    private static LibraryService libraryService;

    private LibraryBookService() {
        libraryBookReadWrite = LibraryBookReadWrite.getInstance();
        sectionReadWrite = SectionReadWrite.getInstance();
        libraryAuthorReadWrite = LibraryAuthorReadWrite.getInstance();
        sectionService = SectionService.getInstance();
        libraryAuthorService = LibraryAuthorService.getInstance();
        libraryService = LibraryService.getInstance();
    }
    public static synchronized LibraryBookService getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new LibraryBookService();
        }
        return INSTANCE;
    }

    public Library addInitialLibraryBooks(Library library){
        if(HelperService.checkIfExists("src/resources/writeCSV/LibraryBookWrite.csv")) {
            ArrayList<LibraryBook> libraryBooks = libraryBookReadWrite.readObjects();
            for(LibraryBook lb: libraryBooks){
                libraryBookReadWrite.writeObjects(lb);
                libraryService.addBookInSection(library,lb.getSection(),lb);
                libraryService.addBookAtAuthor(library,lb.getAuthor(),lb);
            }
        }else {
            ArrayList<LibraryBook> libraryBooks = libraryBookReadWrite.readObjectsAgain();
            for(LibraryBook lb: libraryBooks){
                libraryService.addBookInSection(library,lb.getSection(),lb);
                libraryService.addBookAtAuthor(library,lb.getAuthor(),lb);
            }
        }
        return library;
    }

    /** Adding a book in the library;
     * If the author of the book is not in the library, then it is added.
     * If the section is not in the library, then it is added.
     * The book is added to the author and the corresponding section.
     */
    public void addBookInLibrary(Library library, String name, int numberOfPages, int yearOfPublication, String language,
                                 LibraryAuthor libraryAuthor, Section section, int numberOfCopies){
        if(!libraryService.findSection(library,section)){
            libraryService.addSection(library,section);
            sectionReadWrite.writeObjects(section);
        }
        if(!libraryService.findAuthor(library,libraryAuthor)){
            libraryService.addAuthor(library,libraryAuthor);
            libraryAuthorReadWrite.writeObjects(libraryAuthor);
        }
        LibraryBook book = new LibraryBook(name, numberOfPages, yearOfPublication, language, libraryAuthor,
                section, numberOfCopies);
        libraryService.addBookInSection(library,section, book);
        libraryService.addBookAtAuthor(library,libraryAuthor, book);
        libraryBookReadWrite.writeObjects(book);
        System.out.println("The book was added!");
    }

    /** Remove a book from the library;
     * If the author of the book has no other book in the library then it will be deleted.
     * The book will be removed from the section to which it belonged.
     */
    public void removeBookFromLibrary(Library library,String name, String firstName, String lastName, int year){
        boolean foundAuthor= false;
        boolean foundBook= false;
        LibraryAuthor libraryAuthor = new LibraryAuthor();
        for (LibraryAuthor author : library.getLibraryAuthors()) {
            if (author.getFirstName().equalsIgnoreCase(firstName) && author.getLastName().equalsIgnoreCase(lastName)) {
                libraryAuthor = author;
                foundAuthor = true;
                break;
            }
        }
        if(foundAuthor) {
            LibraryBook libraryBook = new LibraryBook();
            TreeSet<LibraryBook> libraryBookTreeSet = libraryAuthor.getBooks();
            for (LibraryBook lb : libraryBookTreeSet) {
                if (lb.getName().equalsIgnoreCase(name) && (lb.getYearOfPublication() == year)) {
                    libraryBook = lb;
                    foundBook = true;
                }
            }
            if (foundBook) {
                libraryAuthorService.removeBook(libraryAuthor, libraryBook);
                if (libraryBookTreeSet.isEmpty()) {
                    library.getLibraryAuthors().remove(libraryAuthor);
                }
                for (Section section : library.getSections())
                    if (section.equals(libraryBook.getSection())) {
                        sectionService.removeBook(section,libraryBook);
                        break;
                    }
                libraryBookReadWrite.deleteFromCSV(libraryBook);
            }
            else {
                System.out.println("The book doesn't exist!");
            }
        }
        else{
            System.out.println("The author doesn't exist!");
        }
    }

    /* Adding a book in the library read from console */
    public void addBookFromInput(Library library){
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
        addBookInLibrary(library, name, numberOfPages, year, language, author, section, numberOfCopies);
    }

    /* Removing a book from the library read from console */
    public void removeBookFromInput(Library library){
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
        removeBookFromLibrary(library, name, authorFirstName, authorLastName, year);
    }

}
