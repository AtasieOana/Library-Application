package csvManage.csvReadWrite;

import classes.Librarian;
import services.HelperService;

import java.util.Calendar;
import java.util.Date;

public class LibrarianReadWrite implements CSVReadWrite<Librarian>{

    private static LibrarianReadWrite INSTANCE = null;
    private static final String FILE_NAME_READ = "src/resources/readCSV/Librarian.csv";
    private static final String FILE_NAME_WRITE = "src/resources/writeCSV/LibrarianWrite.csv";

    @Override
    public String getFileReadName() {
        return FILE_NAME_READ;
    }

    @Override
    public String getFileWriteName() {
        return FILE_NAME_WRITE;
    }

    public static synchronized LibrarianReadWrite getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new LibrarianReadWrite();
        }
        return INSTANCE;
    }

    @Override
    public Librarian convertCSVLineInObject(String line) {
        String[] elements = line.split(SEPARATOR);
        String lastName = elements[0];
        String firstName = elements[1];
        String[] dateElements = elements[2].split("/");
        Date dateOfBirth = HelperService.makeDate(Integer.parseInt(dateElements[2]),
                Integer.parseInt(dateElements[1]), Integer.parseInt(dateElements[0]));
        String address = elements[3];
        int salary = Integer.parseInt(elements[4]);
        return new Librarian(lastName, firstName, dateOfBirth, address, salary);
    }

    @Override
    public String convertObjectToCSVLine(Librarian object) {
        String returnString = object.getLastName() + SEPARATOR + object.getFirstName() + SEPARATOR;
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(object.getDateOfBirth());
        returnString += calendar.get(Calendar.DAY_OF_MONTH) + SEPARATOR_DATE +
                calendar.get(Calendar.MONTH) + SEPARATOR_DATE + calendar.get(Calendar.YEAR)
                + SEPARATOR + object.getAddress() + SEPARATOR + object.getSalary() + "\n";
        return returnString;
    }

    @Override
    public void deleteObjectFromCSV(Librarian object) { }

    @Override
    public void updateNumberInObjectFromCSV(Librarian object, String Option) { }

    @Override
    public int findElementInCSV(Librarian object) { return 0; }

    @Override
    public void updateBookInObjectFromCSV(Librarian object) { }
}
