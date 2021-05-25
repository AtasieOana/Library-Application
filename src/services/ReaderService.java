package services;

import classes.Reader;
import repository.ReaderRepository;

import java.util.*;

public class ReaderService {

    private static ReaderService INSTANCE = null;
    private static LibraryService libraryService;
    private final ReaderRepository readerRepository = new ReaderRepository();

    private ReaderService() {
        libraryService = LibraryService.getInstance();
    }
    public static synchronized ReaderService getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ReaderService();
        }
        return INSTANCE;
    }


    /**
     * Removing a reader;
     */
    private void removeReader(String CNP) {
        readerRepository.removeReaderFromDatabase(CNP);
    }

    /* Adding a new reader using input from console */
    public void addNewReaderFromInput() {
        Scanner scan = new Scanner(System.in);
        System.out.println("Enter the reader's last name:");
        String lastName = scan.nextLine();
        System.out.println("Enter the reader's first name:");
        String firstName = scan.nextLine();
        System.out.println("Enter the birth year of the reader:");
        int year = scan.nextInt();
        scan.nextLine();
        System.out.println("Enter the birth month of the reader:");
        int month = scan.nextInt();
        scan.nextLine();
        System.out.println("Enter the birth day of the reader:");
        int day = scan.nextInt();
        scan.nextLine();
        Date date = HelperService.makeDate(year, month, day);
        System.out.println("Enter the reader's CNP:");
        String CNP = scan.nextLine();
        System.out.println("Enter the reader's address:");
        String address = scan.nextLine();
        System.out.println("Enter the reader's phone number:");
        String phoneNumber = scan.nextLine();
        Reader reader = new Reader(CNP, lastName,firstName, date,  address, phoneNumber);
        libraryService.addReader(reader);
    }

    /* Removing a reader using input from console*/
    public void removeReaderFromInput() {
        Scanner scan = new Scanner(System.in);
        System.out.println("Enter the reader's CNP:");
        String CNP = scan.nextLine();
        removeReader(CNP);
    }

    /* Update the name of a reader*/
    public void modifyReader(Reader reader, String newLastName){
        readerRepository.updateLastName(newLastName,reader.getCNP());
    }

    /* Update the last name of a reader from input*/
    public void modifyReaderFromInput(){
        Scanner scan = new Scanner(System.in);
        System.out.println("Enter the reader's CNP:");
        String CNP = scan.nextLine();
        System.out.println("Enter the new last name for reader:");
        String newLastName = scan.nextLine();
        Reader reader = readerRepository.getReaderFromDatabase(CNP);
        if(reader == null){
            System.out.println("The reader is not registered at the library!");
        }
        else {
            modifyReader(reader, newLastName);
        }
    }
}
