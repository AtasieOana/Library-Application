package services;

import classes.Librarian;
import classes.Library;
import csvManage.csvReadWrite.LibrarianReadWrite;

import java.util.ArrayList;

public class LibrarianService {
    private static LibrarianService INSTANCE = null;
    private final LibrarianReadWrite librarianReadWrite;
    private static LibraryService libraryService;

    private LibrarianService() {
        librarianReadWrite = LibrarianReadWrite.getInstance();
        libraryService = LibraryService.getInstance();
    }
    public static synchronized LibrarianService getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new LibrarianService();
        }
        return INSTANCE;
    }

    public Library addInitialLibrarians(Library library){
        if(HelperService.checkIfExists("src/resources/writeCSV/LibrarianWrite.csv")) {
            ArrayList<Librarian> librarians = librarianReadWrite.readObjects();
            for(Librarian l: librarians){
                libraryService.addLibrarian(library,l);
                librarianReadWrite.writeObjects(l);
            }
        }else {
            ArrayList<Librarian> librarians = librarianReadWrite.readObjectsAgain();
            for(Librarian l: librarians){
                libraryService.addLibrarian(library,l);
            }
        }
        return library;
    }
}
