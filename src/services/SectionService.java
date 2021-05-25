package services;

import classes.*;
import repository.SectionRepository;

import java.util.Scanner;
import java.util.TreeSet;

public class SectionService {
    private static SectionService INSTANCE = null;
    private final SectionRepository sectionRepository = new SectionRepository();

    private SectionService() {

    }
    public static synchronized SectionService getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new SectionService();
        }
        return INSTANCE;
    }

    public String getBooksTitle(Section section){
        StringBuilder booksTitle = new StringBuilder();
        TreeSet<LibraryBook> libraryBookTreeSet= sectionRepository.findAllBookFromSection(section.getSectionType().toString());
        for(LibraryBook lb: libraryBookTreeSet){
            booksTitle.append(lb.getName());
            booksTitle.append(";");
        }
        return booksTitle.toString();
    }


    /**
     *  See all the books in a section;
     */
    private void findBooksFromSection(Section section) {
        TreeSet<LibraryBook> lb = sectionRepository.findAllBookFromSection(section.getSectionType().toString());
        if(lb ==null){
            System.out.println("Section doesn't exist!");
            return;
        }
        for(LibraryBook b:lb){
            System.out.println(b);
        }
    }

    /**
     * Seeing all the books in a section received as input
     **/
    public void seeAllBooks(){
        Scanner scan = new Scanner(System.in);
        System.out.println("Enter the section type:");
        String sectionType = scan.nextLine();
        Section section = HelperService.createSectionWithSectionType(sectionType);
        findBooksFromSection(section);
    }
}
