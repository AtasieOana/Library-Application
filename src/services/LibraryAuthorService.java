package services;

import classes.*;
import repository.LibraryAuthorRepository;
import repository.LibraryBookRepository;

import java.util.*;

public class LibraryAuthorService {
    private static LibraryAuthorService INSTANCE = null;
    private final LibraryAuthorRepository libraryAuthorRepository = new LibraryAuthorRepository();
    private final LibraryBookRepository libraryBookRepository = new LibraryBookRepository();


    private LibraryAuthorService() {
    }
    public static synchronized LibraryAuthorService getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new LibraryAuthorService();
        }
        return INSTANCE;
    }

    /**
     * See all books written by an author
     */
    public void findBooksFromAuthor(LibraryAuthor author){
        TreeSet<LibraryBook> lb = libraryAuthorRepository.findAllBookFromAuthor(author.getIdAuthor());
        if(lb ==null){
            System.out.println("Author doesn't exist!");
            return;
        }
        for(LibraryBook b:lb){
            System.out.println(b);
        }
    }

    /**
     * Removing an author from the library;
     * When the author is removed, the books written by him are also removed.
     */
    private void removeAuthor(LibraryAuthor libraryAuthor){
        TreeSet<LibraryBook> libraryBookTreeSet= libraryAuthorRepository.findAllBookFromAuthor(libraryAuthor.getIdAuthor());
        if(libraryBookTreeSet != null) {
            for(LibraryBook libraryBook:libraryBookTreeSet){
                libraryBookRepository.removeLibraryBookFromDatabase(libraryBook.getIdLibraryBook());
            }
        }
        libraryAuthorRepository.removeLibraryAuthorFromDatabase(libraryAuthor.getIdAuthor());
    }

    public String getBooksTitle(LibraryAuthor libraryAuthor){
        StringBuilder booksTitle = new StringBuilder();
        TreeSet<LibraryBook> libraryBookTreeSet= libraryAuthorRepository.findAllBookFromAuthor(libraryAuthor.getIdAuthor());
        for(LibraryBook lb: libraryBookTreeSet){
            booksTitle.append(lb.getName());
            booksTitle.append(";");
        }
        return booksTitle.toString();
    }


    /* Removing an author from the library read from console */
    public void removeAuthorFromInput(){
        Scanner scan = new Scanner(System.in);
        System.out.println("Enter the author's last name:");
        String authorLastName = scan.nextLine();
        System.out.println("Enter the author's first name:");
        String authorFirstName = scan.nextLine();
        LibraryAuthor author = libraryAuthorRepository.getLibraryAuthorByName(authorLastName, authorFirstName);
        if(author == null){
            System.out.println("Author doesn't exist in the library!");
            return;
        }
        removeAuthor(author);
    }

    /* Seeing all books written by an author read from console */
    public void seeAllBooks(){
        Scanner scan = new Scanner(System.in);
        System.out.println("Enter the author's last name:");
        String authorLastName = scan.nextLine();
        System.out.println("Enter the author's first name:");
        String authorFirstName = scan.nextLine();
        LibraryAuthor author = libraryAuthorRepository.getLibraryAuthorByName(authorLastName, authorFirstName);
        if(author == null){
            System.out.println("Author doesn't exist in the library!");
            return;
        }
        findBooksFromAuthor(author);
    }
}
