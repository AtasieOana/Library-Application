package services;

import classes.*;
import csvManage.csvReadWrite.RequiredBookReadWrite;
import repository.AuthorRepository;
import repository.RequiredBookRepository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class RequiredBookService {
    private static RequiredBookService INSTANCE = null;
    private static LibraryService libraryService;
    private final RequiredBookRepository requiredBookRepository = new RequiredBookRepository();
    private final AuthorRepository authorRepository = new AuthorRepository();


    private RequiredBookService() {
        libraryService = LibraryService.getInstance();
    }
    public static synchronized RequiredBookService getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new RequiredBookService();
        }
        return INSTANCE;
    }

    public ArrayList<RequiredBook> createFirstRequiredBooks() {
        Author author = authorRepository.getAuthorByName("Wilde", "Oscar");
        if(author == null){
            authorRepository.insertAuthorInDatabase(new Author("Wilde", "Oscar"));
        }
        RequiredBook requiredBook = new RequiredBook("The Happy Prince and Other Tales",author,
                2000,5);
        return new ArrayList<>(Collections.singletonList(requiredBook));
    }

    public void addInitialRequiredBooks(){
        ArrayList<RequiredBook> requiredBooks = createFirstRequiredBooks();
        for(RequiredBook rb: requiredBooks){
            libraryService.addRequiredBook(rb);
        }
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

}
