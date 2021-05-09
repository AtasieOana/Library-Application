package Classes;

import CSVManage.CSVReadWrite;

import java.util.*;

public class Library {

    private ArrayList<Librarian> librarians;
    private TreeSet<LibraryAuthor> libraryAuthors;
    private TreeSet<Section> sections;
    private ArrayList<Reader> readers;
    private ArrayList<RequiredBook> requiredBooks;
    private ArrayList<Loan> loans;

    public Library() {
        this.librarians = new ArrayList<>();
        this.libraryAuthors = new TreeSet<>();
        this.sections = new TreeSet<>();
        this.readers = new ArrayList<>();
        this.requiredBooks = new ArrayList<>();
        this.loans = new ArrayList<>();
    }

    public Library(ArrayList<Librarian> librarians, TreeSet<LibraryAuthor> libraryAuthors, TreeSet<Section> sections,
                   ArrayList<Reader> readers, ArrayList<RequiredBook> requiredBooks, ArrayList<Loan> loans) {
        this.librarians = librarians;
        this.libraryAuthors = libraryAuthors;
        this.sections = sections;
        this.readers = readers;
        this.requiredBooks = requiredBooks;
        this.loans = loans;
    }

    public ArrayList<Librarian> getLibrarians() {
        return librarians;
    }

    public void setLibrarians(ArrayList<Librarian> librarians) {
        this.librarians = librarians;
    }

    public TreeSet<LibraryAuthor> getLibraryAuthors() {
        return libraryAuthors;
    }

    public void setLibraryAuthors(TreeSet<LibraryAuthor> libraryAuthors) {
        this.libraryAuthors = libraryAuthors;
    }

    public TreeSet<Section> getSections() {
        return sections;
    }

    public void setSections(TreeSet<Section> sections) {
        this.sections = sections;
    }

    public ArrayList<Reader> getReaders() {
        return readers;
    }

    public void setReaders(ArrayList<Reader> readers) {
        this.readers = readers;
    }

    public ArrayList<RequiredBook> getRequiredBooks() {
        return requiredBooks;
    }

    public void setRequiredBooks(ArrayList<RequiredBook> requiredBooks) {
        this.requiredBooks = requiredBooks;
    }

    public ArrayList<Loan> getLoans() {
        return loans;
    }

    public void setLoans(ArrayList<Loan> loans) {
        this.loans = loans;
    }

    /**
     * Method for adding a new section to the library
     **/
    public void addSection(Section section) {
        sections.add(section);
    }

    /**
     * Method for adding a new required book to the library
     **/
    public void addRequiredBook(RequiredBook requiredBook) {
        requiredBooks.add(requiredBook);
    }

    /**
     * Method for adding a new loan to the library
     **/
    public void addLoan(Loan loan) {
        loans.add(loan);

    }

    /**
     * Method for adding a new librarian to the library
     **/
    public void addLibrarian(Librarian librarian) {
        librarians.add(librarian);
    }

    /**
     * Method for adding a new reader to the library
     **/
    public void addReader(Reader reader) {
        readers.add(reader);
    }

    /**
     * Method for removing a loan
     **/
    public void removeLoan(Loan loan) {
        loans.remove(loan);
    }

    /**
     * Method for finding a section in the library
     **/
    public Boolean findSection(Section section) {
        if (sections.isEmpty()) {
            return false;
        }
        return sections.contains(section);
    }

    /**
     * Method for adding a new author to the library
     **/
    public void addAuthor(LibraryAuthor libraryAuthor) {
        libraryAuthors.add(libraryAuthor);
    }

    /**
     * Method for finding a author in the library
     **/
    public Boolean findAuthor(LibraryAuthor libraryAuthor) {
        if (libraryAuthors.isEmpty()) {
            return false;
        }
        return libraryAuthors.contains(libraryAuthor);
    }

    /**
     * Method for adding a book to a section
     **/
    public void addBookInSection(Section section, LibraryBook book) {
        boolean found = false;
        for (Section sec : sections) {
            if (sec.equals(section)) {
                sec.addBook(book);
                found = true;
            }
        }
        if (!found) {
            System.out.println("Section doesn't exist!");
        }
    }

    /**
     * Method to add a book to the corresponding author
     **/
    public void addBookAtAuthor(LibraryAuthor author, LibraryBook book) {
        boolean found = false;
        for (LibraryAuthor aut : libraryAuthors) {
            if (aut.equals(author)) {
                aut.addBook(book);
                found = true;
            }
        }
        if (!found) {
            System.out.println("Author doesn't exist!");
        }
    }

    /**
     * Method to check if there is an author in the library
     */
    public LibraryAuthor checkAuthor(Author author){
        for (LibraryAuthor aut : libraryAuthors) {
            if (aut.getFirstName().equals(author.getFirstName()) && aut.getLastName().equals(author.getLastName())) {
                return aut;
            }
        }
        return new LibraryAuthor();
    }

    /**
     * Method to find a reader by its name
     */
    public Reader findReaderByName(String lastname, String firstname) {
        for (Reader reader : readers)
            if (reader.getFirstName().equals(firstname) && reader.getLastName().equals(lastname)) {
                return reader;
            }
        return new Reader();
    }

    /**
     * Method to find a librarian by its name
     */
    public Librarian findLibrarianByName(String lastname, String firstname) {
        for (Librarian librarian : librarians) {
            if (librarian.getFirstName().equals(firstname) && librarian.getLastName().equals(lastname)) {
                return librarian;
            }
        }
        return new Librarian();
    }

    /**
     * Method for finding a loan by the reader's name and the name of the book
     */
    public Loan findLoan(String readerFirstName, String readerLastName, String bookName, String lastNameAuthor, String firstNameAuthor){
        for (Loan loan : loans)
            if (loan.getReader().getFirstName().equals(readerFirstName) &&
                    loan.getReader().getLastName().equalsIgnoreCase(readerLastName) &&
                    loan.getBook().getName().equalsIgnoreCase(bookName) &&
                    loan.getBook().getAuthor().getLastName().equalsIgnoreCase(lastNameAuthor) &&
                    loan.getBook().getAuthor().getFirstName().equals(firstNameAuthor)
            ) {
                return loan;

            }
        return new Loan();
    }

    /**
     * Method for finding a required book by its name
     */
    public RequiredBook findRequiredBook(String bookName){
        for (RequiredBook requiredBook : requiredBooks)
            if (requiredBook.getName().equals(bookName)) {
                return requiredBook;
            }
        return new RequiredBook();
    }

}
