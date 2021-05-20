package services;

import classes.*;

public class LibraryCreateService {

    private static LibraryCreateService INSTANCE = null;
    private final LibrarianService librarianService;
    private final SectionService sectionService;
    private final ReaderService readerService;
    private final RequiredBookService requiredBookService;
    private final LibraryAuthorService libraryAuthorService;
    private final LibraryBookService libraryBookService;

    private LibraryCreateService() {
        librarianService = LibrarianService.getInstance();
        sectionService = SectionService.getInstance();
        readerService = ReaderService.getInstance();
        requiredBookService = RequiredBookService.getInstance();
        libraryAuthorService = LibraryAuthorService.getInstance();
        libraryBookService = LibraryBookService.getInstance();
    }
    public static synchronized LibraryCreateService getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new LibraryCreateService();
        }
        return INSTANCE;
    }

    /**
     * Creating the initial library using the CSV files
     */
    public Library CreateLibraryFromCSV(){
        Library library = new Library();
        /* adding initial librarians */
        library = librarianService.addInitialLibrarians(library);
        /* adding initial readers */
        library = readerService.addInitialReaders(library);
        /* adding initial authors **/
        library = libraryAuthorService.addInitialLibraryAuthors(library);
        /* adding initial sections */
        library = sectionService.addInitialSections(library);
        /* adding initial books */
        library = libraryBookService.addInitialLibraryBooks(library);
        /* adding initial required book */
        library = requiredBookService.addInitialRequiredBooks(library);
        return library;
    }
}
