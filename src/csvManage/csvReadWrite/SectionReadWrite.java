package csvManage.csvReadWrite;

import classes.Section;
import services.HelperService;
import services.SectionService;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class SectionReadWrite implements CSVReadWrite<Section>{
    private static SectionReadWrite INSTANCE = null;
    private static final String FILE_NAME_READ = "src/resources/readCSV/Section.csv";
    private static final String FILE_NAME_WRITE = "src/resources/writeCSV/SectionWrite.csv";

    @Override
    public String getFileReadName() {
        return FILE_NAME_READ;
    }

    @Override
    public String getFileWriteName() {
        return FILE_NAME_WRITE;
    }

    public static synchronized SectionReadWrite getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new SectionReadWrite();
        }
        return INSTANCE;
    }

    @Override
    public Section convertCSVLineInObject(String line) {
        String[] elements = line.split(SEPARATOR);
        String sectionType = elements[0].toUpperCase();
        return HelperService.createSectionWithSectionType(sectionType);
    }

    @Override
    public String convertObjectToCSVLine(Section object) {
        SectionService sectionService = SectionService.getInstance();
        String returnString = object.getSectionType().toString() + SEPARATOR;
        String books = sectionService.getBooksTitle(object);
        if (books.equals("")) {
            returnString += "/";
        } else {
            returnString += books;
        }
        returnString += "\n";
        return returnString;
    }

    @Override
    public void deleteObjectFromCSV(Section object) {
        int writerLine = 1;
        int numberLine = findElementInCSV(object);
        ArrayList<String> fileContent = readFileContent();
        try (FileWriter writer = new FileWriter(FILE_NAME_WRITE, false)){
            if (numberLine > 0) {
                writer.write("Section Type,Book Titles\n");
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

    @Override
    public void updateNumberInObjectFromCSV(Section object, String Option) { }

    @Override
    public int findElementInCSV(Section object) {
        int numberLine = 0;
        boolean ok = false;
        try (BufferedReader buffer = new BufferedReader(new FileReader(FILE_NAME_WRITE))) {
            buffer.readLine();  /* it skips the first line because it contains the column names */
            String line = buffer.readLine();
            while (line != null) {
                if (!ok) {
                    numberLine += 1;
                }
                String[] elements = line.split(",");
                if (object.getSectionType().toString().equalsIgnoreCase(elements[0])) {
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
    public void updateBookInObjectFromCSV(Section object) {
        int writerLine = 1;
        int numberLine = findElementInCSV(object);
        ArrayList<String> fileContent = readFileContent();
        try (FileWriter writer = new FileWriter(FILE_NAME_WRITE, false)){
            if (numberLine > 0) {
                writer.write("Section Type,Book Titles\n");
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
