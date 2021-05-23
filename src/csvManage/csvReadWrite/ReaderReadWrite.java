package csvManage.csvReadWrite;

import classes.Reader;
import services.HelperService;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class ReaderReadWrite implements CSVReadWrite<Reader>{
    private static ReaderReadWrite INSTANCE = null;
    private static final String FILE_NAME_READ = "src/resources/readCSV/Reader.csv";
    private static final String FILE_NAME_WRITE = "src/resources/writeCSV/ReaderWrite.csv";

    @Override
    public String getFileReadName() {
        return FILE_NAME_READ;
    }

    @Override
    public String getFileWriteName() {
        return FILE_NAME_WRITE;
    }

    public static synchronized ReaderReadWrite getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ReaderReadWrite();
        }
        return INSTANCE;
    }

    @Override
    public Reader convertCSVLineInObject(String line) {
        String[] elements = line.split(",");
        String lastName = elements[0];
        String firstName = elements[1];
        String[] dateElements = elements[2].split("/");
        Date dateOfBirth = HelperService.makeDate(Integer.parseInt(dateElements[2]),
                Integer.parseInt(dateElements[1]), Integer.parseInt(dateElements[0]));
        String CNP = elements[3];
        String address = elements[4];
        String phoneNumber = elements[5];
        return new Reader(CNP,lastName, firstName, dateOfBirth,  address, phoneNumber);
    }

    @Override
    public String convertObjectToCSVLine(Reader object) {
        String returnString = object.getLastName() + SEPARATOR + object.getFirstName() + SEPARATOR;
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(object.getDateOfBirth());
        returnString += calendar.get(Calendar.DAY_OF_MONTH) + SEPARATOR_DATE + calendar.get(Calendar.MONTH) +
                SEPARATOR_DATE + calendar.get(Calendar.YEAR) + SEPARATOR + object.getCNP() + SEPARATOR +
                object.getAddress() + SEPARATOR + object.getPhoneNumber() + "\n";
        return returnString;
    }

    @Override
    public void deleteObjectFromCSV(Reader object) {
        int writerLine = 1;
        int numberLine = findElementInCSV(object);
        ArrayList<String> fileContent = readFileContent();
        try (FileWriter writer = new FileWriter(FILE_NAME_WRITE, false)){
            if (numberLine > 0) {
                writer.write("Last Name,First Name,Date Of Birth,CNP,Address,Phone Number\n");
                for (String i : fileContent) {
                    if (numberLine != writerLine) {
                        writer.write(i + "\n");
                    }
                    writerLine += 1;
                }
            }
        } catch (IOException exception) {
            exception.printStackTrace();
        }}

    @Override
    public void updateNumberInObjectFromCSV(Reader object, String Option) { }

    @Override
    public int findElementInCSV(Reader object) {
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
                        elements[1].equalsIgnoreCase(object.getFirstName()) && elements[3].equals(object.getCNP())) {
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
    public void updateBookInObjectFromCSV(Reader object) { }
}
