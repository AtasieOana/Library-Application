package services;

import classes.*;
import repository.RequiredBookRepository;



public class RequiredBookService {
    private static RequiredBookService INSTANCE = null;
    private final RequiredBookRepository requiredBookRepository = new RequiredBookRepository();


    private RequiredBookService() {
    }
    public static synchronized RequiredBookService getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new RequiredBookService();
        }
        return INSTANCE;
    }


    /**
     *  Find the most requested book;
     */
    public void findMostRequestedBook() {
        RequiredBook rb = requiredBookRepository.getMostRequiredBook();
        if(rb == null){
            System.out.println("No required book!");
        }
        else {
            System.out.println(rb);
        }
    }

    /**
     * Method for removing the required books with the fewest requests
     */
    public void removeRequestBooks(){
        requiredBookRepository.removeRequiredBooksFromDatabase();
    }

}
