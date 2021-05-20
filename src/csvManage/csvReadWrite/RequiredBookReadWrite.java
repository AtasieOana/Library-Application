package csvManage.csvReadWrite;

import classes.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class RequiredBookReadWrite implements CSVReadWrite<RequiredBook>{
    private static RequiredBookReadWrite INSTANCE = null;
    private static final String FILE_NAME_READ = "src/resources/readCSV/RequiredBook.csv";
    private static final String FILE_NAME_WRITE = "src/resources/writeCSV/RequiredBookWrite.csv";

    @Override
    public String getFileReadName() {
        return FILE_NAME_READ;
    }

    @Override
    public String getFileWriteName() {
        return FILE_NAME_WRITE;
    }

    public static synchronized RequiredBookReadWrite getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new RequiredBookReadWrite();
        }
        return INSTANCE;
    }

    @Override
    public RequiredBook convertCSVLineInObject(String line) {
        String[] elements = line.split(",");
        String name = elements[0];
        Author author = new Author(elements[1], elements[2]);
        int year = Integer.parseInt(elements[3]);
        int numberOfCopiesRequest = Integer.parseInt(elements[4]);
        return new RequiredBook(name, author, year, numberOfCopiesRequest);
    }

    @Override
    public String convertObjectToCSVLine(RequiredBook object) {
        return object.getName() + SEPARATOR + object.getAuthor().getLastName() + SEPARATOR +
                object.getAuthor().getFirstName() + SEPARATOR + object.getYearOfPublication() + SEPARATOR +
                object.getNumberOfRequests() + "\n";
    }

    @Override
    public void deleteObjectFromCSV(RequiredBook object) { }

    @Override
    public void updateNumberInObjectFromCSV(RequiredBook object, String Option) {
        int writerLine = 1;
        int numberLine = findElementInCSV(object);
        ArrayList<String> fileContent = readFileContent();
        try (FileWriter writer = new FileWriter(FILE_NAME_WRITE, false)) {
            if (numberLine > 0) {
                writer.write("Name,Author Last Name,Author First Name,Year Of Publication,Number Of Requests\n");
                for (String i : fileContent) {
                    if (numberLine != writerLine) {
                        writer.write(i);
                    } else {
                        writer.append(addNumberObjectToCSVLine(object,Option));
                    }
                    writer.append("\n");
                    writerLine += 1;
                }
            }
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    @Override
    public int findElementInCSV(RequiredBook object) {
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
                        && elements[1].equalsIgnoreCase(object.getAuthor().getLastName()) &&
                        elements[2].equalsIgnoreCase(object.getAuthor().getFirstName())) {
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
    public void updateBookInObjectFromCSV(RequiredBook object) { }

    public String addNumberObjectToCSVLine(RequiredBook object,  String Option) {
        String returnString =  object.getName() + SEPARATOR + object.getAuthor().getLastName() + SEPARATOR +
                object.getAuthor().getFirstName() + SEPARATOR + object.getYearOfPublication() + SEPARATOR ;
        if(Option.equalsIgnoreCase("add")) {
            returnString += String.valueOf(object.getNumberOfRequests() + 1);
        }
        return returnString;
    }

}
