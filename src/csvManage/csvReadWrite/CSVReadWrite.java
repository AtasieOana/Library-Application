package csvManage.csvReadWrite;

import classes.*;

import java.io.*;
import java.util.ArrayList;


public interface CSVReadWrite<T extends CSVCompatible> {

    String SEPARATOR = ",";
    String SEPARATOR_DATE = "/";

    String getFileReadName();

    String getFileWriteName();

    T convertCSVLineInObject(String line);

    String convertObjectToCSVLine(T object);

    void deleteObjectFromCSV(T object);

    void updateNumberInObjectFromCSV(T object, String Option);

    int findElementInCSV(T object);

    void updateBookInObjectFromCSV(T object);


    default ArrayList<T> readObjects() {
        ArrayList<T> read = new ArrayList<>();
        try (BufferedReader buffer = new BufferedReader(new FileReader(getFileReadName()))) {
            /* it skips the first line because it contains the column names */
            buffer.readLine();
            String line = buffer.readLine();
            while (line != null) {
                read.add(convertCSVLineInObject(line));
                line = buffer.readLine();
            }
        } catch (IOException exception) {
            exception.printStackTrace();
        }
        return read;
    }

    default void writeObjects(T object) {
        try {
            File file = new File(getFileWriteName());
            if (file.isFile()) {
                if (file.length() < 1) {
                    FileWriter writerFirst = new FileWriter(getFileWriteName(), true);
                    switch (object.getClass().getSimpleName().toLowerCase()) {
                        case "reader" -> writerFirst.append("Last Name,First Name,Date Of Birth,CNP,Address,Phone Number\n");
                        case "librarian" -> writerFirst.append("Last Name,First Name,Date Of Birth,Address,Phone Number\n");
                        case "libraryauthor" -> writerFirst.append("Last Name,First Name,Books Title\n");
                        case "librarybook" -> writerFirst.append("Name,Number Of Pages,Year Of Publication,Language,Author Last Name,Author First Name,Section Type,Number Of Copies\n");
                        case "requiredbook" -> writerFirst.append("Name,Author Last Name,Author First Name,Year Of Publication,Number Of Requests\n");
                        case "section" -> writerFirst.append("Section Type,Book Titles\n");
                    }
                    writerFirst.close();
                }
            }
        }catch (IOException exception) { exception.printStackTrace(); }
        try (FileWriter writer = new FileWriter(getFileWriteName(), true)) {
            writer.append(convertObjectToCSVLine(object));
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    /**
     * Method to delete an object from the CSV or to update some elements
     */
    default void deleteFromCSV(T object){
        deleteObjectFromCSV(object);
    }

    /**
     * Method to change the number of books for the library books or the required books
     */
    default void updateNumberInCSV(T object, String Option){ updateNumberInObjectFromCSV(object, Option); }

    /**
     * Method to find an object in a CSV file
     **/
    default void findElement(T object){ findElementInCSV(object); }

    /**
    * When a new book is added, for the author of the book and the section,
    * the books written in CSV corresponding to the authors in the library and sections are updated.
    **/
    default void updateBooksInCSV(T object){updateBookInObjectFromCSV(object);}

    /**
     * Method to get the content of a CSV file
     **/
    default ArrayList<String> readFileContent() {
        ArrayList<String> read = new ArrayList<>();
        try (BufferedReader buffer = new BufferedReader(new FileReader(getFileWriteName()))) {
            /* it skips the first line because it contains the column names */
            buffer.readLine();
            String line = buffer.readLine();
            while (line != null) {
                read.add(line);
                line = buffer.readLine();
            }
        } catch (IOException exception) {
            exception.printStackTrace();
        }
        return read;
    }

    default ArrayList<T> readObjectsAgain() {
        ArrayList<T> read = new ArrayList<>();
        try (BufferedReader buffer = new BufferedReader(new FileReader(getFileWriteName()))) {
            /* it skips the first line because it contains the column names */
            buffer.readLine();
            String line = buffer.readLine();
            while (line != null) {
                read.add(convertCSVLineInObject(line));
                line = buffer.readLine();
            }
        } catch (IOException exception) {
            exception.printStackTrace();
        }
        return read;
    }
}


