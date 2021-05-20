package services;

import classes.Library;
import classes.Reader;
import csvManage.csvReadWrite.ReaderReadWrite;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.Scanner;

public class ReaderService {

    private static ReaderService INSTANCE = null;
    private final ReaderReadWrite readerReadWrite;
    private static LibraryService libraryService;

    private ReaderService() {
        readerReadWrite = ReaderReadWrite.getInstance();
        libraryService = LibraryService.getInstance();
    }
    public static synchronized ReaderService getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ReaderService();
        }
        return INSTANCE;
    }


    /**
     * Adding a new reader;
     */
    private void addReader(Library library, Reader reader){
        ArrayList<Reader> allReaders = library.getReaders();
        allReaders.add(reader);
        library.setReaders(allReaders);
        readerReadWrite.writeObjects(reader);
        System.out.println("The reader has been added!");
    }

    /**
     * Removing a reader;
     */
    private void removeReader(Library library, String lastName, String firstName, String CNP) {
        boolean found = false;
        ArrayList<Reader> allReaders = library.getReaders();
        Iterator<Reader> iterator = allReaders.iterator();
        Reader reader = new Reader();
        while (iterator.hasNext()) {
            Reader r = iterator.next();
            if (r.getLastName().equalsIgnoreCase(lastName) && r.getFirstName().equalsIgnoreCase(firstName)
                    && r.getCNP().equals(CNP)) {
                reader = r;
                iterator.remove();
                found = true;
            }
        }
        if (!found) {
            System.out.println("Reader doesn't exist!");
        } else {
            System.out.println("The reader has been removed!");
            readerReadWrite.deleteFromCSV(reader);
        }
    }

    /* Adding a new reader using input from console */
    public void addNewReaderFromInput(Library library) {
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
        Reader reader = new Reader(lastName,firstName, date, CNP, address, phoneNumber);
        addReader(library, reader);
    }

    /* Removing a reader using input from console*/
    public void removeReaderFromInput(Library library) {
        Scanner scan = new Scanner(System.in);
        System.out.println("Enter the reader's last name:");
        String lastName = scan.nextLine();
        System.out.println("Enter the reader's first name:");
        String firstName = scan.nextLine();
        System.out.println("Enter the reader's CNP:");
        String CNP = scan.nextLine();
        removeReader(library, lastName, firstName, CNP);
    }

    public Library addInitialReaders(Library library){
        if(HelperService.checkIfExists("src/resources/writeCSV/ReaderWrite.csv")) {
            ArrayList<Reader> readers = readerReadWrite.readObjects();
            for(Reader r: readers){
                libraryService.addReader(library,r);
                readerReadWrite.writeObjects(r);
            }
        }else {
            ArrayList<Reader> readers = readerReadWrite.readObjectsAgain();
            for(Reader r: readers){
                libraryService.addReader(library,r);
            }
        }
        return library;
    }
}
