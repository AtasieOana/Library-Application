package Main;

import Classes.*;
import Services.Service;
import java.util.Scanner;

public class Main {

    private static int Options(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("1. Add a book in the library");
        System.out.println("Choose one of the above options:");
        int opt = scanner.nextInt();
        return opt;
    }

    public static void main(String[] args){

        int opt = Options();
        Service service = new Service();
        if(opt == 1){
            LibraryAuthor author = new LibraryAuthor("Mihai","Mircea");
            Section section = new Section( SectionType.FictionBooks);
            service.addBookInLibrary("Nume", 54, 1987, "romana", author, section, 4);
        }
        else{
            System.out.println("The entered option is invalid!");
        }
    }

}
