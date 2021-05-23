package services;

import classes.Librarian;
import classes.Library;
import csvManage.csvReadWrite.LibrarianReadWrite;
import repository.LibrarianRepository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

public class LibrarianService {
    private static LibrarianService INSTANCE = null;
    private static LibraryService libraryService;

    private LibrarianService() {
        libraryService = LibraryService.getInstance();
        LibrarianRepository librarianRepository = new LibrarianRepository();
    }
    public static synchronized LibrarianService getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new LibrarianService();
        }
        return INSTANCE;
    }

    public ArrayList<Librarian> createFirstLibrarians() {
        Date dateOfBirth1 = HelperService.makeDate(1994, 10, 22);
        Librarian librarian1= new Librarian("6020518409892","Mihulescu","Vladimir",dateOfBirth1,"Aviators",1000);
        Date dateOfBirth2 = HelperService.makeDate(2000, 4, 20);
        Librarian librarian2= new Librarian("5020518409399","Cernat","Mihaela",dateOfBirth2,"Summer Street",1500);
        return new ArrayList<>(Arrays.asList(librarian1,librarian2));
    }


    public void addInitialLibrarians(){
        ArrayList<Librarian> librarians = createFirstLibrarians();
        for(Librarian l: librarians){
            libraryService.addLibrarian(l);
        }
    }
}
