package services;

import classes.*;
import repository.SectionRepository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.TreeSet;

public class SectionService {
    private static SectionService INSTANCE = null;
    private static LibraryService libraryService;
    private final SectionRepository sectionRepository = new SectionRepository();

    private SectionService() {
        libraryService = LibraryService.getInstance();
    }
    public static synchronized SectionService getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new SectionService();
        }
        return INSTANCE;
    }

    public ArrayList<Section> createFirstSections(){
        Section section1 = new Section(SectionType.POEMS);
        Section section2 = new Section(SectionType.FICTIONAL);
        return new ArrayList<>(Arrays.asList(section1, section2));
    }

    public void addInitialSections() {
        ArrayList<Section> sections = createFirstSections();
        for (Section s : sections) {
            libraryService.addSection(s);
        }
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
     * Method to find a book in the section
     **/
    public boolean findBook(Section section, LibraryBook book){
        if(section.getBooks().isEmpty()){
            return false;
        }
        return section.getBooks().contains(book);
    }

    /**
     * Method for adding a book to its section
     **/
    public void addBook(Section section, LibraryBook libraryBook) {
        TreeSet<LibraryBook> libraryBookTreeSet = section.getBooks();
        libraryBookTreeSet.add(libraryBook);
        section.setBooks(libraryBookTreeSet);
    }

    /**
     * Method for removing a book from its section
     **/
    public void removeBook(Section section,LibraryBook libraryBook){
        TreeSet<LibraryBook> libraryBookTreeSet = section.getBooks();
        libraryBookTreeSet.remove(libraryBook);
        section.setBooks(libraryBookTreeSet);
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
