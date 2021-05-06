package Services;

import CSVManage.CSVReadWrite;
import Classes.*;
import Classes.Reader;

import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.io.*;


public class LibraryService {

    private Library library;
    private final CSVReadWrite write = CSVReadWrite.getInstance();

    public LibraryService(){
        this.library = new Library();
    }

    public LibraryService(Library library) {
        this.library = library;
    }

    public Library getLibrary() {
        return library;
    }

    public void setLibrary(Library library) {
        this.library = library;
    }

    /** Adding a book in the library;
     * If the author of the book is not in the library, then it is added.
     * If the section is not in the library, then it is added.
     * The book is added to the author and the corresponding section.
     */
    public void addBookInLibrary(String name, int numberOfPages, int yearOfPublication, String language,
                                 LibraryAuthor libraryAuthor, Section section, int numberOfCopies){
        if(!library.findSection(section)){
            library.addSection(section);
            write.writeCSV("Section.csv", section);
        }
        if(!library.findAuthor(libraryAuthor)){
            library.addAuthor(libraryAuthor);
            write.writeCSV("LibraryAuthor.csv", libraryAuthor);
        }
        LibraryBook book = new LibraryBook(name, numberOfPages, yearOfPublication, language, libraryAuthor,
                section, numberOfCopies);
        library.addBookInSection(section, book);
        library.addBookAtAuthor(libraryAuthor, book);
        write.writeCSV("LibraryBook.csv", book);
        System.out.println("The book was added!");
    }

    /** Remove a book from the library;
     * If the author of the book has no other book in the library then it will be deleted.
     * The book will be removed from the section to which it belonged.
     */
    public void removeBookFromLibrary(LibraryBook book){

        boolean found = false;
        LibraryAuthor libraryAuthor = new LibraryAuthor();
        for (LibraryAuthor author : library.getLibraryAuthors())
            if (author.equals(book.getAuthor())) {
                libraryAuthor = author;
                found = true;
                break;
            }
        TreeSet<LibraryBook> libraryBookTreeSet = libraryAuthor.getBooks();
        libraryAuthor.removeBook(book);
        if(libraryBookTreeSet.isEmpty()){
            library.getLibraryAuthors().remove(libraryAuthor);
        }
        for (Section section : library.getSections())
            if (section.equals(book.getSection())) {
                section.removeBook(book);
                break;
            }
        if(found){
            System.out.println("The book was removed!");
        }
        else {
            System.out.println("The book doesn't exit!");
        }

    }

    /**
     * See all books written by an author
     */
    public TreeSet<LibraryBook> findBooksFromAuthor(LibraryAuthor author){

        boolean found = false;
        TreeSet<LibraryBook> libraryBookTreeSet = new TreeSet<>();
        for(LibraryAuthor la: library.getLibraryAuthors())
            if(la.equals(author)){
                libraryBookTreeSet = la.getBooks();
                found = true;
            }

        if(found) {
            for (LibraryBook libraryBook : libraryBookTreeSet) {
                System.out.println(libraryBook);
            }
           author.modifyCVS();
        }
        else{
            System.out.println("Author doesn't exist!");
        }
        return libraryBookTreeSet;
    }

    /**
     *  See all the books in a section;
     */
    public void findBooksFromSection(Section section) {
        boolean found = false;
        TreeSet<LibraryBook> libraryBookTreeSet = new TreeSet<>();
        for (Section sec : library.getSections())
            if (sec.equals(section)) {
                libraryBookTreeSet = sec.getBooks();
                found = true;
            }

        if (found) {
            boolean exist = false;
            for (LibraryBook libraryBook : libraryBookTreeSet) {
                System.out.println(libraryBook);
                exist = true;
            }
            if(!exist){
                System.out.println("Section is empty!");
            }
        } else {
            System.out.println("Section doesn't exist!");
        }
    }

    /** See all the books in the library;
     *  A book in the library can be found in its section or in its author.
     *  I chose to use the sections to display all the books.
     */
    public void findBooksFromLibrary() {
        boolean found = false;
        TreeSet<LibraryBook> libraryBookTreeSet;
        for (Section sec : library.getSections()) {
            libraryBookTreeSet = sec.getBooks();
            for (LibraryBook libraryBook : libraryBookTreeSet) {
                System.out.println(libraryBook);
                found = true;
            }
        }

        if (!found){
            System.out.println("There are no books in the library!");
        }
    }

    /**
     * Adding a new reader;
     */
    public void addReader(Reader reader){
        ArrayList<Reader> allReaders = library.getReaders();
        allReaders.add(reader);
        library.setReaders(allReaders);
        System.out.println("The reader has been added!");
    }

    /**
     * Removing a reader;
     */
    public void removeReader(Reader reader){
        boolean found = false;
        ArrayList<Reader> allReaders = library.getReaders();
        Iterator<Reader> iterator = allReaders.iterator();
        while (iterator.hasNext()) {
            Reader r = iterator.next();
            if (r.equals(reader)) {
                iterator.remove();
                found = true;
            }
        }
        if(!found){
            System.out.println("Reader doesn't exist!");
        }
        else{
            System.out.println("The reader has been removed!");
        }
    }

    /**
     * Removing an author from the library;
     * When the author is removed, the books written by him are also removed.
     */
    public void removeAuthor(LibraryAuthor libraryAuthor){

        boolean found = false;
        TreeSet<LibraryAuthor> allLibraryAuthors = library.getLibraryAuthors();
        Iterator<LibraryAuthor> iterator = allLibraryAuthors.iterator();
        while (iterator.hasNext()) {
            LibraryAuthor la = iterator.next();
            if (la.equals(libraryAuthor)) {
                TreeSet<LibraryBook> libraryBookTreeSet = la.getBooks();
                for(LibraryBook libraryBook:libraryBookTreeSet){
                    for (Section section : library.getSections()){
                        if (section.equals(libraryBook.getSection())) {
                            section.removeBook(libraryBook);
                        }
                    }
                }
                iterator.remove();
                found = true;
            }
        }
        if(!found){
            System.out.println("Author doesn't exist!");
        }
        else{
            System.out.println("The author has been deleted!");
        }

    }

    /**
     * Borrowing a book
     * If a book that is not in the library is requested, then it will be added to the list of required books;
     */
    public void borrowBook(String name, Author author, String firstNameReader, String lastNameReader,
                           String firstNameLibrarian, String lastNameLibrarian, int yearBook){

        /*
         * Check if the reader and the librarian are part of the library
         */
        Librarian librarian = library.findLibrarianByName(lastNameLibrarian,firstNameLibrarian);
        Reader reader = library.findReaderByName(lastNameReader, firstNameReader);
        if(!librarian.equals(new Librarian()) && !reader.equals(new Reader())) {
            boolean found = false;
            boolean stock = true;
            LibraryAuthor libraryAuthor = library.checkAuthor(author);
            LibraryBook book = new LibraryBook();
            if (libraryAuthor.equals(new LibraryAuthor()))
                System.out.println("The author is not currently in the library.");
            else {
                TreeSet<LibraryBook> libraryBookTreeSet = findBooksFromAuthor(libraryAuthor);
                for (LibraryBook libraryBook : libraryBookTreeSet) {
                    if (libraryBook.getName().equals(name) && libraryBook.getYearOfPublication() == yearBook) {
                        if (libraryBook.getNumberOfCopies() >= 1) {
                            System.out.println("The loan will be completed!");
                            found = true;
                            book = libraryBook;
                            libraryBook.setNumberOfCopies(libraryBook.getNumberOfCopies()-1);
                        } else {
                            stock = false;
                            System.out.println("The book is no longer in stock!");
                        }
                        break;
                    }
                }
                if(found){
                    Calendar calendar = Calendar.getInstance();
                    calendar.set(Calendar.YEAR, 2021);
                    calendar.set(Calendar.MONTH, 2);
                    calendar.set(Calendar.DATE, 14);
                    Date date = calendar.getTime();
                    Loan loan = new Loan(book, reader, librarian, date);
                    library.addLoan(loan);
                }
                else{
                    if(stock) {
                        System.out.println("The book doesn't exit in the library!");
                        RequiredBook requiredBook = library.findRequiredBook(name);
                        if (requiredBook.getName().equals("")) {
                            RequiredBook requiredBook1 = new RequiredBook(name, author, yearBook, 1);
                            library.addRequiredBook(requiredBook1);
                        } else {
                            requiredBook.increaseTheNumberOfRequests();
                        }
                    }
                }
            }
        }
        else{
            if(librarian.equals(new Librarian())) {
                System.out.println("The librarian is not registered at the library!");
            }
            else{
                System.out.println("The reader is not registered at the library!");
            }
        }
    }

    /**
     *  Returning a book;
     */
    public void returnBook(String name, String firstNameReader, String lastNameReader) {

        Loan loan = library.findLoan(firstNameReader, lastNameReader, name);
        if (loan.getLoanDate() == null) {
            System.out.println("Loan doesn't exist!");
        } else {
            Date d1 = new Date();
            long diff = d1.getTime() - loan.getLoanDate().getTime();
            long days_diff = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS) - Loan.getLoanDays();
            System.out.println("The book was returned with a delay of " + days_diff + " days.");
            loan.getBook().setNumberOfCopies(loan.getBook().getNumberOfCopies() + 1);
            library.removeLoan(loan);
        }
    }

    /**
     *  Find the most requested book;
     */
    public void findMostRequestedBook() {
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
