package services;

import classes.Librarian;
import repository.LibrarianRepository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Scanner;

public class LibrarianService {
    private static LibrarianService INSTANCE = null;
    private static LibraryService libraryService;
    private final LibrarianRepository librarianRepository = new LibrarianRepository();

    private LibrarianService() {
        libraryService = LibraryService.getInstance();

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

    /* Update the name of a librarian*/
    public void modifyLibrarian(Librarian librarian, String newLastName){
        librarianRepository.updateLastName(newLastName,librarian.getCNP());
    }

    /* Update the last name of a librarian from input*/
    public void modifyLibrarianFromInput(){
        Scanner scan = new Scanner(System.in);
        System.out.println("Enter the librarian's CNP:");
        String CNP = scan.nextLine();
        System.out.println("Enter the new last name for librarian:");
        String newLastName = scan.nextLine();
        Librarian librarian = librarianRepository.getLibrarianFromDatabase(CNP);
        if(librarian == null){
            System.out.println("The librarian is not registered at the library!");
        }
        else {
            modifyLibrarian(librarian, newLastName);
        }
    }

    /**
     * Removing a librarian;
     */
    private void removeLibrarian(String CNP) {
        librarianRepository.removeLibrarianFromDatabase(CNP);
    }

    /* Adding a new librarian using input */
    public void addNewLibrarianFromInput() {
        Scanner scan = new Scanner(System.in);
        System.out.println("Enter the librarian's last name:");
        String lastName = scan.nextLine();
        System.out.println("Enter the librarian's first name:");
        String firstName = scan.nextLine();
        System.out.println("Enter the birth year of the librarian:");
        int year = scan.nextInt();
        scan.nextLine();
        System.out.println("Enter the birth month of the librarian:");
        int month = scan.nextInt();
        scan.nextLine();
        System.out.println("Enter the birth day of the librarian:");
        int day = scan.nextInt();
        scan.nextLine();
        Date date = HelperService.makeDate(year, month, day);
        System.out.println("Enter the librarian's CNP:");
        String CNP = scan.nextLine();
        System.out.println("Enter the librarian's address:");
        String address = scan.nextLine();
        System.out.println("Enter the librarian's salary:");
        int salary = scan.nextInt();
        scan.nextLine();
        Librarian librarian = new Librarian(CNP, lastName,firstName, date,  address, salary);
        libraryService.addLibrarian(librarian);
    }

    /* Removing a librarian using input*/
    public void removeLibrarianFromInput() {
        Scanner scan = new Scanner(System.in);
        System.out.println("Enter the librarian's CNP:");
        String CNP = scan.nextLine();
        removeLibrarian(CNP);
    }
}
