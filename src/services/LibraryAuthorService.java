package services;

import classes.*;
import csvManage.csvReadWrite.LibraryAuthorReadWrite;
import csvManage.csvReadWrite.LibraryBookReadWrite;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;
import java.util.TreeSet;

public class LibraryAuthorService {
    private static LibraryAuthorService INSTANCE = null;
    private final LibraryAuthorReadWrite libraryAuthorReadWrite;
    private final SectionService sectionService;
    private final LibraryBookReadWrite libraryBookReadWrite;
    private static LibraryService libraryService;


    private LibraryAuthorService() {
        libraryAuthorReadWrite = LibraryAuthorReadWrite.getInstance();
        sectionService = SectionService.getInstance();
        libraryBookReadWrite = LibraryBookReadWrite.getInstance();
        libraryService = LibraryService.getInstance();
    }
    public static synchronized LibraryAuthorService getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new LibraryAuthorService();
        }
        return INSTANCE;
    }

    public Library addInitialLibraryAuthors(Library library){
        if(HelperService.checkIfExists("src/resources/writeCSV/LibraryAuthorWrite.csv")) {
            ArrayList<LibraryAuthor> libraryAuthors = libraryAuthorReadWrite.readObjects();
            for(LibraryAuthor la: libraryAuthors){
                libraryService.addAuthor(library,la);
                libraryAuthorReadWrite.writeObjects(la);
            }
        }else {
            ArrayList<LibraryAuthor> libraryAuthors = libraryAuthorReadWrite.readObjectsAgain();
            for(LibraryAuthor la: libraryAuthors){
                libraryService.addAuthor(library,la);
            }
        }
        return library;
    }

    /**
     * See all books written by an author
     */
    public TreeSet<LibraryBook> findBooksFromAuthor(Library library, LibraryAuthor author){
        boolean found = false;
        TreeSet<LibraryBook> libraryBookTreeSet = new TreeSet<>();
        for(LibraryAuthor la: library.getLibraryAuthors())
            if(la.equals(author)){
                libraryBookTreeSet = la.getBooks();
                found = true;
            }
        if(found) {
            for (LibraryBook libraryBook : libraryBookTreeSet) {
                System.out.println(libraryBook);
            }
        }
        else{
            System.out.println("Author doesn't exist!");
        }
        return libraryBookTreeSet;
    }

    /**
     * Removing an author from the library;
     * When the author is removed, the books written by him are also removed.
     */
    private void removeAuthor(Library library,LibraryAuthor libraryAuthor){
        boolean found = false;
        TreeSet<LibraryAuthor> allLibraryAuthors = library.getLibraryAuthors();
        Iterator<LibraryAuthor> iterator = allLibraryAuthors.iterator();
        while (iterator.hasNext()) {
            LibraryAuthor la = iterator.next();
            if (la.equals(libraryAuthor)) {
                TreeSet<LibraryBook> libraryBookTreeSet = la.getBooks();
                for(LibraryBook libraryBook:libraryBookTreeSet){
                    libraryBookReadWrite.deleteFromCSV(libraryBook);
                    for (Section section : library.getSections()){
                        if (section.equals(libraryBook.getSection())) {
                            sectionService.removeBook(section, libraryBook); }
                    }
                }
                iterator.remove();
                found = true;
            }
        }
        if(!found){
            System.out.println("Author doesn't exist!");
        }
        else{
            System.out.println("The author has been deleted!");
            libraryAuthorReadWrite.deleteFromCSV(libraryAuthor);
        }
    }

    public String getBooksTitle(LibraryAuthor libraryAuthor){
        StringBuilder booksTitle = new StringBuilder();
        for(LibraryBook lb: libraryAuthor.getBooks()){
            booksTitle.append(lb.getName());
            booksTitle.append(";");
        }
        return booksTitle.toString();
    }

    /**
     * Method for adding a book to its author
     **/
    public void addBook(LibraryBook libraryBook, LibraryAuthor libraryAuthor){
        TreeSet<LibraryBook> libraryBookTreeSet = libraryAuthor.getBooks();
        libraryBookTreeSet.add(libraryBook);
        libraryAuthor.setBooks(libraryBookTreeSet);
        libraryAuthorReadWrite.updateBookInObjectFromCSV(libraryAuthor);
    }

    /**
     * Method for removing a book from its author
     **/
    public void removeBook(LibraryAuthor libraryAuthor, LibraryBook libraryBook){
        TreeSet<LibraryBook> libraryBookTreeSet = libraryAuthor.getBooks();
        libraryBookTreeSet.remove(libraryBook);
        libraryAuthor.setBooks(libraryBookTreeSet);
        libraryAuthorReadWrite.deleteFromCSV(libraryAuthor);
    }

    /* Removing an author from the library read from console */
    public void removeAuthorFromInput(Library library){
        Scanner scan = new Scanner(System.in);
        System.out.println("Enter the author's last name:");
        String authorLastName = scan.nextLine();
        System.out.println("Enter the author's first name:");
        String authorFirstName = scan.nextLine();
        LibraryAuthor author = new LibraryAuthor(authorLastName, authorFirstName);
        removeAuthor(library, author);
    }

    /* Seeing all books written by an author read from console */
    public void seeAllBooks(Library library){
        Scanner scan = new Scanner(System.in);
        System.out.println("Enter the author's last name:");
        String authorLastName = scan.nextLine();
        System.out.println("Enter the author's first name:");
        String authorFirstName = scan.nextLine();
        LibraryAuthor author = new LibraryAuthor(authorLastName, authorFirstName);
        findBooksFromAuthor(library, author);
    }
}
