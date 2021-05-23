package services;

import classes.Librarian;
import classes.Library;
import classes.Reader;
import csvManage.csvReadWrite.ReaderReadWrite;
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

    public ArrayList<Reader> createFirstReaders() {
        Date dateOfBirth1 = HelperService.makeDate(1999, 9, 12);
        Reader reader1 = new Reader("6020518409892","Mihulescu","Vladimir",dateOfBirth1,"Aviators","0455879671");
        Date dateOfBirth2 = HelperService.makeDate(2001, 4, 4);
        Reader reader2 = new Reader("5020518409399","Cernat","Mihaela",dateOfBirth2,"Summer Street","0766112365");
        return new ArrayList<>(Arrays.asList(reader1,reader2));
    }

    public void addInitialReaders(){
        ArrayList<Reader> readers = createFirstReaders();
        for(Reader r: readers) {
            libraryService.addReader(r);
        }
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
