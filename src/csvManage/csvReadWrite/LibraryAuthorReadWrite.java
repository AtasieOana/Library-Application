package csvManage.csvReadWrite;

import classes.LibraryAuthor;
import classes.LibraryBook;
import services.LibraryAuthorService;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.TreeSet;

public class LibraryAuthorReadWrite implements CSVReadWrite<LibraryAuthor> {
    private static LibraryAuthorReadWrite INSTANCE = null;
    private static final String FILE_NAME_READ = "src/resources/readCSV/LibraryAuthor.csv";
    private static final String FILE_NAME_WRITE = "src/resources/writeCSV/LibraryAuthorWrite.csv";
    private static final LibraryAuthorService libraryAuthorService = LibraryAuthorService.getInstance();


    @Override
    public String getFileReadName() {
        return FILE_NAME_READ;
    }

    @Override
    public String getFileWriteName() {
        return FILE_NAME_WRITE;
    }

    public static synchronized LibraryAuthorReadWrite getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new LibraryAuthorReadWrite();
        }
        return INSTANCE;
    }

    @Override
    public LibraryAuthor convertCSVLineInObject(String line) {
        String[] elements = line.split(",");
        String lastName = elements[0];
        String firstName = elements[1];
        TreeSet<LibraryBook> books = new TreeSet<>();
        return new LibraryAuthor(lastName, firstName, books);
    }

    @Override
    public String convertObjectToCSVLine(LibraryAuthor object) {
        return object.getLastName() + SEPARATOR + object.getFirstName() + SEPARATOR +
                libraryAuthorService.getBooksTitle(object) + "\n";
    }

    @Override
    public void deleteObjectFromCSV(LibraryAuthor object) {
        int writerLine = 1;
        int numberLine = findElementInCSV(object);
        ArrayList<String> fileContent = readFileContent();
        try (FileWriter writer = new FileWriter(FILE_NAME_WRITE, false)) {
            if (numberLine > 0) {
                writer.write("Last Name,First Name,Books Title\n");
                for (String i : fileContent) {
                    if (numberLine != writerLine) {
                        writer.append(i).append("\n");
                    } else {
                        if (!libraryAuthorService.getBooksTitle(object).equals("")) {
                            writer.append(convertObjectToCSVLine(object));
                        }
                    }
                    writerLine += 1;
                }
            }
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    @Override
    public void updateNumberInObjectFromCSV(LibraryAuthor object, String Option) { }

    @Override
    public int findElementInCSV(LibraryAuthor object) {
        int numberLine = 0;
        boolean ok = false;
        try (BufferedReader buffer = new BufferedReader(new FileReader(FILE_NAME_WRITE))) {
            buffer.readLine();  /* it skips the first line because it contains the column names */
            String line = buffer.readLine();
            while (line != null) {
                if (!ok) {
                    numberLine += 1;
                }
                String[] elements = line.split(SEPARATOR);
                if (elements[0].equalsIgnoreCase(object.getLastName()) &&
                        elements[1].equalsIgnoreCase(object.getFirstName())) {
                    ok = true;
                }
                line = buffer.readLine();
            }
        } catch (IOException exception) {
            exception.printStackTrace();
        }
        return numberLine;
    }

    @Override
    public void updateBookInObjectFromCSV(LibraryAuthor object) {
        int writerLine = 1;
        int numberLine = findElementInCSV(object);
        ArrayList<String> fileContent = readFileContent();
        try (FileWriter writer = new FileWriter(FILE_NAME_WRITE, false)) {
            if (numberLine > 0) {
                writer.write("Last Name,First Name,Books Title\n");
                for (String i : fileContent) {
                    if (numberLine != writerLine) {
                        writer.append(i).append("\n");
                    } else {
                        writer.append(convertObjectToCSVLine(object));
                    }
                    writerLine += 1;
                }
            }
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

}
