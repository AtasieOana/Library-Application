package CSVManage;
import Classes.Librarian;
import Classes.Reader;
import Classes.RequiredBook;
import Services.HelperService;

import java.io.*;
import java.util.*;

public final class CSVReadWrite {

    private static CSVReadWrite instance = null;

    private CSVReadWrite(){
    }

    public static CSVReadWrite getInstance(){
        if(instance == null){
            instance = new CSVReadWrite();
        }
        return instance;
    }

    private static ArrayList<String> readCSV(String FilePath) {
        ArrayList<String> read = new ArrayList<>();
        try (BufferedReader buffer = new BufferedReader(new FileReader(FilePath))) {
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

    public static <T> ArrayList<T> readObjects(String FilePath, String objectOption){
        ArrayList<T> objects = new ArrayList<T>();
        ArrayList<String> fileContent = readCSV(FilePath);
        try{
            switch (objectOption.toLowerCase()) {
                case "reader" -> {
                    for (String line : fileContent) {
                        String[] elements = line.split(",");
                        String lastName = elements[0];
                        String firstName = elements[1];
                        String[] dateElements = elements[2].split("/");
                        Date dateOfBirth = HelperService.makeDate(Integer.parseInt(dateElements[2]),
                                Integer.parseInt(dateElements[1]), Integer.parseInt(dateElements[0]));
                        String CNP = elements[3];
                        String address = elements[4];
                        String phoneNumber = elements[5];
                        Reader reader = new Reader(lastName, firstName, dateOfBirth, CNP, address, phoneNumber);
                        objects.add((T) reader);
                    }
                }
                case "librarian" -> {
                    for (String line : fileContent) {
                        String[] elements = line.split(",");
                        String lastName = elements[0];
                        String firstName = elements[1];
                        String[] dateElements = elements[2].split("/");
                        Date dateOfBirth = HelperService.makeDate(Integer.parseInt(dateElements[2]),
                                Integer.parseInt(dateElements[1]), Integer.parseInt(dateElements[0]));
                        String address = elements[3];
                        int salary = Integer.parseInt(elements[4]);
                        Librarian librarian = new Librarian(lastName, firstName, dateOfBirth, address, salary);
                        objects.add((T) librarian);
                    }
                }
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return objects;
    }


    public static <T> void writeCSV(String FilePath, T object) {
        try (FileWriter writer = new FileWriter(FilePath, true)) {
            switch (object.getClass().getSimpleName().toLowerCase()) {
                case "reader" -> {
                    Reader reader = (Reader) object;
                    writer.append(reader.getLastName());
                    writer.append(",");
                    writer.append(reader.getFirstName());
                    writer.append(",");
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(reader.getDateOfBirth());
                    writer.append(String.valueOf(calendar.get(Calendar.DAY_OF_MONTH)));
                    writer.append("/");
                    writer.append(String.valueOf(calendar.get(Calendar.MONTH)));
                    writer.append("/");
                    writer.append(String.valueOf(calendar.get(Calendar.YEAR)));
                    writer.append(",");
                    writer.append(reader.getCNP());
                    writer.append(",");
                    writer.append(reader.getAddress());
                    writer.append(",");
                    writer.append(reader.getPhoneNumber());
                    writer.append("\n");
                }
                case "librarian" -> {
                    Librarian librarian = (Librarian) object;
                    writer.append(librarian.getLastName());
                    writer.append(",");
                    writer.append(librarian.getFirstName());
                    writer.append(",");
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(librarian.getDateOfBirth());
                    writer.append(String.valueOf(calendar.get(Calendar.DAY_OF_MONTH)));
                    writer.append("/");
                    writer.append(String.valueOf(calendar.get(Calendar.MONTH)));
                    writer.append("/");
                    writer.append(String.valueOf(calendar.get(Calendar.YEAR)));
                    writer.append(",");
                    writer.append(librarian.getAddress());
                    writer.append(",");
                    writer.append(String.valueOf((librarian.getSalary())));
                    writer.append("\n");
                }
            }
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

}
