package Classes;

import java.util.Date;

public class Loan {

    private Book book;
    private Reader reader;
    private Librarian librarian;
    private Date loanDate;
    private static final int loanWeeks = 2;

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
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

    public static int getLoanWeeks() {
        return loanWeeks;
    }
}
