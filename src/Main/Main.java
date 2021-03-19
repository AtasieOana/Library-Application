package Main;

import Classes.Author;
import Classes.Library;
import Classes.LibraryAuthor;

public class Main {

    public static void Main(String[] args){

        Library library = new Library();
        LibraryAuthor author = new LibraryAuthor("Mihai","Mircea", null);

        System.out.println(library.findAuthor(author));
    }

}
