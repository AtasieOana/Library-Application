package Main;

import Classes.*;
import Services.Service;

public class Main {

    public static void main(String[] args){

        Service service = new Service();
        LibraryAuthor author = new LibraryAuthor("Mihai","Mircea", null);
        Section section = new Section( SectionType.FictionBooks,null);
        service.addBookInLibrary("Nume", 54, 1987, "romana", author, section, 4);
    }

}
