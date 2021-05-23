package classes;

import java.util.Date;

public class Loan {

    private int idLoan;
    private LibraryBook book;
    private Reader reader;
    private Librarian librarian;
    private Date loanDate;
    private static final int loanDays = 12;

    public Loan(){
    }

    public Loan(LibraryBook book, Reader reader, Librarian librarian, Date loanDate) {
        this.book = book;
        this.reader = reader;
        this.librarian = librarian;
        this.loanDate = loanDate;
    }

    public Loan(int idLoan, LibraryBook book, Reader reader, Librarian librarian, Date loanDate) {
        this.idLoan = idLoan;
        this.book = book;
        this.reader = reader;
        this.librarian = librarian;
        this.loanDate = loanDate;
    }

    public int getIdLoan() {
        return idLoan;
    }

    public void setIdLoan(int idLoan) {
        this.idLoan = idLoan;
    }

    public LibraryBook getBook() {
        return book;
    }

    public void setBook(LibraryBook book) {
        this.book = book;
    }

    public Reader getReader() {
        return reader;
    }

    public void setReader(Reader reader) {
        this.reader = reader;
    }

    public Librarian getLibrarian() {
        return librarian;
    }

    public void setLibrarian(Librarian librarian) {
        this.librarian = librarian;
    }

    public Date getLoanDate() {
        return loanDate;
    }

    public void setLoanDate(Date loanDate) {
        this.loanDate = loanDate;
    }

    public static int getLoanDays() {
        return loanDays;
    }
}
