package services;

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
     * Creating the initial library
     */
    public void CreateLibraryFromDatabase(){
        /* adding initial librarians */
        librarianService.addInitialLibrarians();
        /* adding initial readers */
        readerService.addInitialReaders();
        /* adding initial authors **/
        libraryAuthorService.addInitialLibraryAuthors();
        /* adding initial sections */
        sectionService.addInitialSections();
        /* adding initial books */
        libraryBookService.addInitialLibraryBooks();
        /* adding initial required book */
        requiredBookService.addInitialRequiredBooks();
    }
}
