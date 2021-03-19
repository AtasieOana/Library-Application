package Services;

import Classes.Library;
import Classes.LibraryAuthor;
import Classes.LibraryBook;
import Classes.Section;

public class Service {

    private Library library;

    public Service(){
        this.library = new Library();
    }

    public Service(Library library) {
        this.library = library;
    }

    public Library getLibrary() {
        return library;
    }

    public void setLibrary(Library library) {
        this.library = library;
    }

    /** Adding a book in the library **/
    /** If the author of the book is not in the library, then it is added.
     * If the section is not in the library, then it is added.
     * The book is added to the author and the corresponding section.
     **/
    public void addBookInLibrary(String name, int numberOfPages, int yearOfPublication, String language,
                                 LibraryAuthor libraryAuthor, Section section, int numberOfCopies){
        if(!library.findSection(section)){
            library.addSection(section);
        }
        if(!library.findAuthor(libraryAuthor)){
            library.addAuthor(libraryAuthor);
        }
        LibraryBook book = new LibraryBook(name, numberOfPages, yearOfPublication, language, libraryAuthor,
                                           section, numberOfCopies);
        libraryAuthor.addBook(book);
        section.addBook(book);
    }



}
