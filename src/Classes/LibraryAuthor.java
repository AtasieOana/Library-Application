package Classes;

import java.util.Arrays;

public class LibraryAuthor extends Author{

    private LibraryBook[] books;

    public LibraryAuthor(LibraryBook[] books) {
        this.books = books;
    }

    public LibraryAuthor(String lastName, String firstName, LibraryBook[] books) {
        super(lastName, firstName);
        this.books = books;
    }

    public void addBook(LibraryBook libraryBook){
        // books = ArrayUtils.add(libraryBook); ???
    }

    @Override
    public boolean equals(Object o) {
        if (this == o){
            return true;
        }
        if (o == null || getClass() != o.getClass()){
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        LibraryAuthor that = (LibraryAuthor) o;
        return Arrays.equals(books, that.books);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + Arrays.hashCode(books);
        return result;
    }
}
