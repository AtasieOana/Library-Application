package services;

import classes.Library;
import classes.LibraryBook;
import classes.Section;
import csvManage.csvReadWrite.SectionReadWrite;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.TreeSet;

public class SectionService {
    private static SectionService INSTANCE = null;
    private final SectionReadWrite sectionReadWrite;
    private static LibraryService libraryService;

    private SectionService() {
        sectionReadWrite = SectionReadWrite.getInstance();
        libraryService = LibraryService.getInstance();
    }
    public static synchronized SectionService getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new SectionService();
        }
        return INSTANCE;
    }

    public Library addInitialSections(Library library){
        if(HelperService.checkIfExists("src/resources/writeCSV/SectionWrite.csv")) {
            ArrayList<Section> sections = sectionReadWrite.readObjects();
            for(Section s: sections){
                libraryService.addSection(library, s);
                sectionReadWrite.writeObjects(s);
            }
        }else {
            ArrayList<Section> sections = sectionReadWrite.readObjectsAgain();
            for(Section s: sections){
                libraryService.addSection(library, s);
            }
        }
        return library;
    }

    public String getBooksTitle(Section section){
        StringBuilder booksTitle = new StringBuilder();
        for(LibraryBook lb: section.getBooks()){
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
        sectionReadWrite.updateBookInObjectFromCSV(section);
    }

    /**
     * Method for removing a book from its section
     **/
    public void removeBook(Section section,LibraryBook libraryBook){
        TreeSet<LibraryBook> libraryBookTreeSet = section.getBooks();
        libraryBookTreeSet.remove(libraryBook);
        section.setBooks(libraryBookTreeSet);
        sectionReadWrite.deleteFromCSV(section);
    }


    /**
     *  See all the books in a section;
     */
    private void findBooksFromSection(Library library, Section section) {
        boolean found = false;
        TreeSet<LibraryBook> libraryBookTreeSet = new TreeSet<>();
        for (Section sec : library.getSections())
            if (sec.equals(section)) {
                libraryBookTreeSet = sec.getBooks();
                found = true;
            }

        if (found) {
            boolean exist = false;
            for (LibraryBook libraryBook : libraryBookTreeSet) {
                System.out.println(libraryBook);
                exist = true;
            }
            if(!exist){
                System.out.println("Section is empty!");
            }
        } else {
            System.out.println("Section doesn't exist!");
        }
    }

    /**
     * Seeing all the books in a section received as input
     **/
    public void seeAllBooks(Library library){
        Scanner scan = new Scanner(System.in);
        System.out.println("Enter the section type:");
        String sectionType = scan.nextLine();
        Section section = HelperService.createSectionWithSectionType(sectionType);
        findBooksFromSection(library, section);
    }
}
