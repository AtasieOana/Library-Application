package CSVManage;
import Classes.*;
import Classes.Reader;
import Services.HelperService;

import java.io.*;
import java.util.*;

public class CSVReadWrite {

    private static CSVReadWrite instance = null;

    private CSVReadWrite() {
    }

    public static CSVReadWrite getInstance() {
        if (instance == null) {
            instance = new CSVReadWrite();
        }
        return instance;
    }

    private ArrayList<String> readCSV(String FilePath) {
        ArrayList<String> read = new ArrayList<>();
        try (BufferedReader buffer = new BufferedReader(new FileReader(FilePath))) {
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

    public <T> ArrayList<T> readObjects(String FilePath, String objectOption) {
        ArrayList<T> objects = new ArrayList<>();
        ArrayList<String> fileContent = readCSV(FilePath);
        try {
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
                case "libraryauthor" -> {
                    for (String line : fileContent) {
                        String[] elements = line.split(",");
                        String lastName = elements[0];
                        String firstName = elements[1];
                        TreeSet<LibraryBook> books = new TreeSet<>();
                        LibraryAuthor libraryAuthor = new LibraryAuthor(lastName, firstName, books);
                        objects.add((T) libraryAuthor);
                    }
                }

                case "section" -> {
                    for (String line : fileContent) {
                        String[] elements = line.split(",");
                        String sectionType = elements[0].toUpperCase();
                        Section section = HelperService.createSectionWithSectionType(sectionType);
                        objects.add((T) section);

                    }
                }
                case "librarybook" -> {
                    for (String line : fileContent) {
                        String[] elements = line.split(",");
                        String name = elements[0];
                        int numberOfPages = Integer.parseInt(elements[1]);
                        int yearOfPublication = Integer.parseInt(elements[2]);
                        String language = elements[3];
                        LibraryAuthor libraryAuthor = new LibraryAuthor(elements[4], elements[5]);
                        Section section = HelperService.createSectionWithSectionType(elements[6]);
                        int numberOfCopies = Integer.parseInt(elements[7]);
                        LibraryBook libraryBook = new LibraryBook(name, numberOfPages, yearOfPublication, language,
                                libraryAuthor, section, numberOfCopies);
                        objects.add((T) libraryBook);
                    }
                }
                case "requiredbook" -> {
                    for (String line : fileContent) {
                        String[] elements = line.split(",");
                        String name = elements[0];
                        Author author = new Author(elements[1], elements[2]);
                        int year = Integer.parseInt(elements[3]);
                        int numberOfCopiesRequest = Integer.parseInt(elements[4]);
                        RequiredBook requiredBook = new RequiredBook(name, author, year, numberOfCopiesRequest);
                        objects.add((T) requiredBook);
                    }
                }
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return objects;
    }


    public <T> void writeCSV(String FilePath, T object) {
        try{
            File file = new File(FilePath);
            if (file.isFile()) {
                if (file.length() < 1) {
                    FileWriter writerFirst = new FileWriter(FilePath, true);
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
        } catch (IOException exception) {
        exception.printStackTrace();
    }
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
                case "libraryauthor" -> {
                    LibraryAuthor libraryAuthor = (LibraryAuthor) object;
                    writer.append(libraryAuthor.getLastName());
                    writer.append(",");
                    writer.append(libraryAuthor.getFirstName());
                    writer.append(",");
                    writer.append((libraryAuthor.getBooksTitle()));
                    writer.append("\n");
                }
                case "section" -> {
                    Section section = (Section) object;
                    writer.append(section.getSectionType().toString());
                    writer.append(",");
                    String books = section.getBooksTitle();
                    if (books.equals("")) {
                        writer.append("/");
                    } else {
                        writer.append(books);
                    }
                    writer.append("\n");
                }
                case "librarybook" -> {
                    LibraryBook libraryBook = (LibraryBook) object;
                    writer.append(libraryBook.getName());
                    writer.append(",");
                    writer.append(String.valueOf(libraryBook.getNumberOfPages()));
                    writer.append(",");
                    writer.append(String.valueOf(libraryBook.getYearOfPublication()));
                    writer.append(",");
                    writer.append(libraryBook.getLanguage());
                    writer.append(",");
                    writer.append(libraryBook.getAuthor().getLastName());
                    writer.append(",");
                    writer.append(libraryBook.getAuthor().getFirstName());
                    writer.append(",");
                    writer.append(libraryBook.getSection().getSectionType().toString());
                    writer.append(",");
                    writer.append(String.valueOf(libraryBook.getNumberOfCopies()));
                    writer.append("\n");
                }

                case "requiredbook" -> {
                    RequiredBook rb = (RequiredBook) object;
                    writer.append(rb.getName());
                    writer.append(",");
                    writer.append(rb.getAuthor().getLastName());
                    writer.append(",");
                    writer.append(rb.getAuthor().getFirstName());
                    writer.append(",");
                    writer.append(String.valueOf(rb.getYearOfPublication()));
                    writer.append(",");
                    writer.append(String.valueOf(rb.getNumberOfRequests()));
                    writer.append("\n");
                }
            }
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    /**
     * Method to delete an object from the CSV or to update some elements
     */
    public <T> void deleteFromCSV(String FilePath, T object) {
        switch (object.getClass().getSimpleName().toLowerCase()) {
            case "libraryauthor" -> {
                LibraryAuthor libraryAuthor = (LibraryAuthor) object;
                int writerLine = 1;
                int numberLine = findElement(FilePath,libraryAuthor);
                ArrayList<String> fileContent = readCSV(FilePath);
                try (FileWriter writer = new FileWriter(FilePath, false)) {
                    if (numberLine > 0) {
                        writer.write("Last Name,First Name,Books Title\n");
                        for (String i : fileContent) {
                            if (numberLine != writerLine) {
                                writer.append(i);
                                writer.append("\n");
                            } else {
                                if (!libraryAuthor.getBooksTitle().equals("")) {
                                    String[] elements = i.split(",");
                                    writer.append(elements[0]);
                                    writer.append(",");
                                    writer.append(elements[1]);
                                    writer.append(",");
                                    writer.append((libraryAuthor.getBooksTitle()));
                                    writer.append("\n");
                                }
                            }
                            writerLine += 1;
                        }
                    }
                } catch (IOException exception) {
                    exception.printStackTrace();
                }
            }

            case "section" -> {
                Section section = (Section) object;
                int writerLine = 1;
                int numberLine = findElement(FilePath,section);
                ArrayList<String> fileContent = readCSV(FilePath);
                try (FileWriter writer = new FileWriter(FilePath, false)) {
                    if (numberLine > 0) {
                        writer.write("Section Type,Book Titles\n");
                        for (String i : fileContent) {
                            if (numberLine != writerLine) {
                                writer.append(i);
                            } else {
                                String[] elements = i.split(",");
                                writer.append(elements[0].toUpperCase());
                                writer.append(",");
                                writer.append((section.getBooksTitle()));
                            }
                            writer.append("\n");
                            writerLine += 1;
                        }
                    }
                } catch (IOException exception) {
                    exception.printStackTrace();
                }
            }

            case "reader" -> {
                Reader readerObj = (Reader) object;
                int writerLine = 1;
                int numberLine = findElement(FilePath,readerObj);
                ArrayList<String> fileContent = readCSV(FilePath);
                try (FileWriter writer = new FileWriter(FilePath, false)) {
                    if (numberLine > 0) {
                        writer.write("Last Name,First Name,Date Of Birth,CNP,Address,Phone Number\n");
                        for (String i : fileContent) {
                            if (numberLine != writerLine) {
                                writer.append(i);
                                writer.append("\n");
                            }
                            writerLine += 1;
                        }
                    }
                } catch (IOException exception) {
                    exception.printStackTrace();
                }

            }

            case "librarybook" -> {
                LibraryBook libraryBook = (LibraryBook) object;
                int writerLine = 1;
                int numberLine = findElement(FilePath,libraryBook);
                ArrayList<String> fileContent = readCSV(FilePath);
                try (FileWriter writer = new FileWriter(FilePath, false)) {
                    if (numberLine > 0) {
                        writer.write("Name,Number Of Pages,Year Of Publication,Language,Author Last Name,Author First Name,Section Type,Number Of Copies\n");
                        for (String i : fileContent) {
                            if (numberLine != writerLine) {
                                writer.append(i);
                                writer.append("\n");
                            }
                            writerLine += 1;
                        }
                    }
                } catch (IOException exception) {
                    exception.printStackTrace();
                }
            }
        }
    }

    /**
     * When a new book is added, for the author of the book and the section,
     * the books written in CSV corresponding to the authors in the library and sections are updated.
     */
    public <T> void updateBooksInCSV(String FilePath, T object) {
        switch (object.getClass().getSimpleName().toLowerCase()) {
            case "libraryauthor" -> {
                LibraryAuthor libraryAuthor = (LibraryAuthor) object;
                int writerLine = 1;
                int numberLine = findElement(FilePath,libraryAuthor);
                ArrayList<String> fileContent = readCSV(FilePath);
                try (FileWriter writer = new FileWriter(FilePath, false)) {
                    if (numberLine > 0) {
                        writer.write("Last Name,First Name,Books Title\n");
                        for (String i : fileContent) {
                            if (numberLine != writerLine) {
                                writer.write(i);
                            } else {
                                String[] elements = i.split(",");
                                writer.append(elements[0]);
                                writer.append(",");
                                writer.append(elements[1]);
                                writer.append(",");
                                writer.append((libraryAuthor.getBooksTitle()));
                            }
                            writer.append("\n");
                            writerLine += 1;
                        }
                    }
                } catch (IOException exception) {
                    exception.printStackTrace();
                }
            }

            case "section" -> {
                Section section = (Section) object;
                int writerLine = 1;
                int numberLine = findElement(FilePath,section);
                ArrayList<String> fileContent = readCSV(FilePath);
                try (FileWriter writer = new FileWriter(FilePath, false)) {
                    if (numberLine > 0) {
                        writer.write("Section Type,Book Titles\n");
                        for (String i : fileContent) {
                            if (numberLine != writerLine) {
                                writer.write(i);
                            } else {
                                String[] elements = i.split(",");
                                writer.append(elements[0].toUpperCase());
                                writer.append(",");
                                writer.append((section.getBooksTitle()));
                            }
                            writer.append("\n");
                            writerLine += 1;
                        }
                    }
                } catch (IOException exception) {
                    exception.printStackTrace();
                }

            }
        }

    }

    /**
     * Method to change the number of books for the library books or the required books
     */
    public <T> void updateNumberInCSV(String FilePath, T object, String Option) {
        switch (object.getClass().getSimpleName().toLowerCase()) {
            case "librarybook" -> {
                LibraryBook lb = (LibraryBook) object;
                int writerLine = 1;
                int numberLine = findElement(FilePath,lb);
                ArrayList<String> fileContent = readCSV(FilePath);
                try (FileWriter writer = new FileWriter(FilePath, false)) {
                    if (numberLine > 0) {
                        writer.write("Name,Number Of Pages,Year Of Publication,Language,Author Last Name,Author First Name,Section Type,Number Of Copies\n");
                        for (String i : fileContent) {
                            if (numberLine != writerLine) {
                                writer.write(i);
                            } else {
                                String[] elements = i.split(",");
                                writer.append(elements[0]);
                                writer.append(",");
                                writer.append(elements[1]);
                                writer.append(",");
                                writer.append(elements[2]);
                                writer.append(",");
                                writer.append(elements[3]);
                                writer.append(",");
                                writer.append(elements[4]);
                                writer.append(",");
                                writer.append(elements[5]);
                                writer.append(",");
                                writer.append(elements[6]);
                                writer.append(",");
                                if(Option.equalsIgnoreCase("add")) {
                                    writer.append(String.valueOf(Integer.parseInt(elements[7]) + 1));
                                }
                                else{
                                    writer.append(String.valueOf(Integer.parseInt(elements[7]) - 1));
                                }
                            }
                            writer.append("\n");
                            writerLine += 1;
                        }
                    }
                } catch (IOException exception) {
                    exception.printStackTrace();
                }

            }

            case "requiredbook" -> {
                RequiredBook rb = (RequiredBook) object;
                int writerLine = 1;
                int numberLine = findElement(FilePath,rb);
                ArrayList<String> fileContent = readCSV(FilePath);
                try (FileWriter writer = new FileWriter(FilePath, false)) {
                    if (numberLine > 0) {
                        writer.write("Name,Author Last Name,Author First Name,Year Of Publication,Number Of Requests\n");
                        for (String i : fileContent) {
                            if (numberLine != writerLine) {
                                writer.write(i);
                            } else {
                                String[] elements = i.split(",");
                                writer.append(elements[0]);
                                writer.append(",");
                                writer.append(elements[1]);
                                writer.append(",");
                                writer.append(elements[2]);
                                writer.append(",");
                                if(Option.equalsIgnoreCase("add")) {
                                    writer.append(String.valueOf(Integer.parseInt(elements[3]) + 1));
                                    writer.append("\n");
                                }
                            }
                            writer.append("\n");
                            writerLine += 1;
                        }
                    }
                } catch (IOException exception) {
                    exception.printStackTrace();
                }

            }
        }

    }

    /**
     * Method to find an object in a CSV file
     **/
    private <T> int findElement(String FilePath, T object) {
        int numberLine = 0;
        boolean ok = false;
        try (BufferedReader buffer = new BufferedReader(new FileReader(FilePath))) {
            /* it skips the first line because it contains the column names */
            buffer.readLine();
            switch(object.getClass().getSimpleName().toLowerCase()) {
                case "libraryauthor" -> {
                    LibraryAuthor libraryAuthor = (LibraryAuthor) object;
                    String line = buffer.readLine();
                    while (line != null) {
                        if (!ok) {
                            numberLine += 1;
                        }
                        String[] elements = line.split(",");
                        if (elements[0].equalsIgnoreCase(libraryAuthor.getLastName()) &&
                                elements[1].equalsIgnoreCase(libraryAuthor.getFirstName())) {
                            ok = true;
                        }
                        line = buffer.readLine();
                    }
                }
                case "librarybook" -> {
                    LibraryBook lb = (LibraryBook) object;
                    String line = buffer.readLine();
                    while (line != null) {
                        if (!ok) {
                            numberLine += 1;
                        }
                        String[] elements = line.split(",");
                        if (elements[0].equalsIgnoreCase(lb.getName()) && elements[4].equalsIgnoreCase(lb.getAuthor().getLastName()) &&
                                elements[5].equalsIgnoreCase(lb.getAuthor().getFirstName())) {
                            ok = true;
                        }
                        line = buffer.readLine();
                    }
                }
                case "requiredbook" -> {
                    RequiredBook rb = (RequiredBook) object;
                    String line = buffer.readLine();
                    while (line != null) {
                        if (!ok) {
                            numberLine += 1;
                        }
                        String[] elements = line.split(",");
                        if (elements[0].equalsIgnoreCase(rb.getName()) && elements[1].equalsIgnoreCase(rb.getAuthor().getLastName()) &&
                                elements[2].equalsIgnoreCase(rb.getAuthor().getFirstName())) {
                            ok = true;
                        }
                        line = buffer.readLine();
                    }
                }
                case "section" -> {
                    Section section = (Section) object;
                    String line = buffer.readLine();
                    while (line != null) {
                        if (!ok) {
                            numberLine += 1;
                        }
                        String[] elements = line.split(",");
                        if (section.getSectionType().toString().equalsIgnoreCase(elements[0])) {
                            ok = true;
                        }
                        line = buffer.readLine();
                    }
                }
                case "reader" -> {
                    Reader reader = (Reader) object;
                    String line = buffer.readLine();
                    while (line != null) {
                        if (!ok) {
                            numberLine += 1;
                        }
                        String[] elements = line.split(",");
                        if (elements[0].equalsIgnoreCase(reader.getLastName()) &&
                                elements[1].equalsIgnoreCase(reader.getFirstName()) &&
                                elements[3].equals(reader.getCNP())) {
                            ok = true;
                        }
                        line = buffer.readLine();
                    }
                }
            }
        } catch (IOException exception) {
            exception.printStackTrace();
        }
        return numberLine;

    }

}
