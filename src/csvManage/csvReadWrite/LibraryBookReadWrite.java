package csvManage.csvReadWrite;

import classes.LibraryAuthor;
import classes.LibraryBook;
import classes.Section;
import services.HelperService;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class LibraryBookReadWrite implements CSVReadWrite<LibraryBook>{
    private static LibraryBookReadWrite INSTANCE = null;
    private static final String FILE_NAME_READ = "src/resources/readCSV/LibraryBook.csv";
    private static final String FILE_NAME_WRITE = "src/resources/writeCSV/LibraryBookWrite.csv";

    @Override
    public String getFileReadName() {
        return FILE_NAME_READ;
    }

    @Override
    public String getFileWriteName() {
        return FILE_NAME_WRITE;
    }

    public static synchronized LibraryBookReadWrite getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new LibraryBookReadWrite();
        }
        return INSTANCE;
    }

    @Override
    public LibraryBook convertCSVLineInObject(String line) {
        String[] elements = line.split(",");
        String name = elements[0];
        int numberOfPages = Integer.parseInt(elements[1]);
        int yearOfPublication = Integer.parseInt(elements[2]);
        String language = elements[3];
        LibraryAuthor libraryAuthor = new LibraryAuthor(elements[4], elements[5]);
        Section section = HelperService.createSectionWithSectionType(elements[6]);
        int numberOfCopies = Integer.parseInt(elements[7]);
        return new LibraryBook(name, numberOfPages, yearOfPublication, language,
                libraryAuthor, section, numberOfCopies);
    }

    @Override
    public String convertObjectToCSVLine(LibraryBook object) {
        return object.getName() + SEPARATOR + object.getNumberOfPages() +
                SEPARATOR + object.getYearOfPublication() + SEPARATOR + object.getLanguage() +
                SEPARATOR + object.getAuthor().getLastName() + SEPARATOR + object.getAuthor().getFirstName() +
                SEPARATOR + object.getSection().getSectionType().toString() + SEPARATOR +
                object.getNumberOfCopies() + "\n";
    }

    @Override
    public void deleteObjectFromCSV(LibraryBook object) {
        int writerLine = 1;
        int numberLine = findElementInCSV(object);
        ArrayList<String> fileContent = readFileContent();
        try (FileWriter writer = new FileWriter(FILE_NAME_WRITE, false)) {
            if (numberLine > 0) {
                writer.write("Name,Number Of Pages,Year Of Publication,Language,Author Last Name,Author First Name,Section Type,Number Of Copies\n");
                for (String i : fileContent) {
                    if (numberLine != writerLine) {
                        writer.append(i).append("\n");
                    }
                    writerLine += 1;
                }
            }
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    @Override
    public void updateNumberInObjectFromCSV(LibraryBook object, String Option) {
        int writerLine = 1;
        int numberLine = findElementInCSV(object);
        ArrayList<String> fileContent = readFileContent();
        try (FileWriter writer = new FileWriter(FILE_NAME_WRITE, false)) {
            if (numberLine > 0) {
                writer.write("Name,Number Of Pages,Year Of Publication,Language,Author Last Name,Author First Name,Section Type,Number Of Copies\n");
                for (String i : fileContent) {
                    if (numberLine != writerLine) {
                        writer.append(i);
                    } else {
                        writer.append(addNumberObjectToCSVLine(object,Option));
                    }
                    writer.append("\n");
                    writerLine += 1;
                }
            }
        } catch (IOException exception) {
            exception.printStackTrace();
        } }

    @Override
    public int findElementInCSV(LibraryBook object) {
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
                if (elements[0].equalsIgnoreCase(object.getName())
                        && elements[4].equalsIgnoreCase(object.getAuthor().getLastName()) &&
                        elements[5].equalsIgnoreCase(object.getAuthor().getFirstName())) {
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
    public void updateBookInObjectFromCSV(LibraryBook object) { }

    public String addNumberObjectToCSVLine(LibraryBook object,  String Option) {
        String returnString =  object.getName() + SEPARATOR + object.getNumberOfPages() +
                SEPARATOR + object.getYearOfPublication() + SEPARATOR + object.getLanguage() +
                SEPARATOR + object.getAuthor().getLastName() + SEPARATOR + object.getAuthor().getFirstName() +
                SEPARATOR + object.getSection().getSectionType().toString() + SEPARATOR;
        if(Option.equalsIgnoreCase("add")) {
            returnString += String.valueOf(object.getNumberOfCopies() + 1);
        }
        else{
            returnString += String.valueOf(object.getNumberOfCopies() - 1);
        }
        return returnString;
    }

}
