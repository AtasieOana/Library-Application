package Services;

import Classes.Library;
import Classes.LibraryAuthor;
import Classes.LibraryBook;
import Classes.Section;

public class Service {

    private Library library;

    /** Adding a book in the library **/
    public void addBookInLibrary(String name, int numberOfPages, int yearOfPublication, String language,
                                 LibraryAuthor libraryAuthor, Section section, int numberOfCopies){
        if(!library.findSection(section)){
            library.addSection(section.getSectionType());
        }
        if(!library.findAuthor(libraryAuthor)){
            library.addAuthor(libraryAuthor.getFirstName(),libraryAuthor.getLastName());
        }
        LibraryBook book = new LibraryBook(name, numberOfPages, yearOfPublication, language, libraryAuthor,
                                           section, numberOfCopies);


    }


}
