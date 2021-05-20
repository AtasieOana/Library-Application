package services;

import classes.Library;
import classes.RequiredBook;
import csvManage.csvReadWrite.RequiredBookReadWrite;
import java.util.ArrayList;

public class RequiredBookService {
    private static RequiredBookService INSTANCE = null;
    private final RequiredBookReadWrite requiredBookReadWrite;
    private static LibraryService libraryService;


    private RequiredBookService() {
        requiredBookReadWrite = RequiredBookReadWrite.getInstance();
        libraryService = LibraryService.getInstance();
    }
    public static synchronized RequiredBookService getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new RequiredBookService();
        }
        return INSTANCE;
    }

    public Library addInitialRequiredBooks(Library library){
        if(HelperService.checkIfExists("src/resources/writeCSV/RequiredBookWrite.csv")) {
            ArrayList<RequiredBook> requiredBook = requiredBookReadWrite.readObjects();
            for(RequiredBook rb: requiredBook){
                libraryService.addRequiredBook(library, rb);
                requiredBookReadWrite.writeObjects(rb);
            }
        }else {
            ArrayList<RequiredBook> requiredBook = requiredBookReadWrite.readObjectsAgain();
            for(RequiredBook rb: requiredBook){
                libraryService.addRequiredBook(library,rb);
            }
        }
        return library;
    }

    /**
     *  Find the most requested book;
     */
    public void findMostRequestedBook(Library library) {
        int number = 0;
        RequiredBook max = new RequiredBook();
        for (RequiredBook requiredBook : library.getRequiredBooks()){
            if (requiredBook.getNumberOfRequests() > number) {
                number = requiredBook.getNumberOfRequests();
                max = requiredBook;
            }
        }
        if(number == 0){
            System.out.println("No book required!");
        }
        else {
            System.out.println(max);
        }
    }

}
