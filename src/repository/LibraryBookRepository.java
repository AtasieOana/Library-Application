package repository;

import classes.LibraryAuthor;
import classes.LibraryBook;
import config.DatabaseConfig;

import java.sql.*;

public class LibraryBookRepository {

    private final LibraryAuthorRepository libraryAuthorRepository = new LibraryAuthorRepository();


    /** Method for inserting a library book object into the database **/
    public void insertLibraryBookInDatabase(LibraryBook libraryBook) {
        String preparedSql = "{call insertLibraryBook(?,?,?,?,?,?,?,?)}";

        Connection databaseConnection = DatabaseConfig.getDatabaseConnection();
        try {
            CallableStatement callableStatement = databaseConnection.prepareCall(preparedSql);
            callableStatement.setString(2, libraryBook.getName());
            callableStatement.setInt(3, libraryBook.getNumberOfPages());

            callableStatement.setInt(4, libraryBook.getYearOfPublication());
            callableStatement.setString(5, libraryBook.getLanguage());
            callableStatement.setInt(6, libraryBook.getAuthor().getIdAuthor());
            callableStatement.setString(7, libraryBook.getSection().getSectionType().toString());
            callableStatement.setInt(8, libraryBook.getNumberOfCopies());

            callableStatement.registerOutParameter(1, Types.INTEGER);

            callableStatement.execute();
            System.out.println("The library book with id:" + callableStatement.getInt(1) + " was added!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /** Method for getting a library book object from the database **/
    public LibraryBook getLibraryBookFromDatabase(int idLibraryBook) {
        String selectSql = "SELECT * FROM libraryBooks WHERE idLibraryBook=?";

        Connection databaseConnection = DatabaseConfig.getDatabaseConnection();
        try {
            PreparedStatement preparedStatement = databaseConnection.prepareStatement(selectSql);
            preparedStatement.setInt(1, idLibraryBook);
            ResultSet resultSet = preparedStatement.executeQuery();
            return mapToLibraryBook(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    public LibraryBook mapToLibraryBook(ResultSet resultSet) throws SQLException {
        if (resultSet.next()){
            LibraryAuthorRepository libraryAuthorRepository = new LibraryAuthorRepository();
            SectionRepository sectionRepository = new SectionRepository();
            return new LibraryBook(resultSet.getInt(1), resultSet.getString(2),resultSet.getInt(3),
                    resultSet.getInt(4), resultSet.getString(5),
                    libraryAuthorRepository.getLibraryAuthorFromDatabase(resultSet.getInt(6)),
                    sectionRepository.getSectionFromDatabase(resultSet.getString(7)),resultSet.getInt(8));
        }
        return null;
    }

    /** Method for removing a library book object from the database **/
    public void removeLibraryBookFromDatabase(int  id) {
        String deleteSql = "DELETE FROM libraryBooks WHERE idLibraryBook=?";
        Connection databaseConnection = DatabaseConfig.getDatabaseConnection();
        try {
            LibraryBook libraryBook = getLibraryBookFromDatabase(id);
            if(libraryBook != null) {
                LibraryAuthor libraryAuthor = libraryAuthorRepository.getLibraryAuthorFromDatabase(libraryBook.getAuthor().getIdAuthor());
                PreparedStatement preparedStatement = databaseConnection.prepareStatement(deleteSql);
                preparedStatement.setInt(1, id);
                int resultSet = preparedStatement.executeUpdate();
                System.out.println("The library book with id: " + id + " was removed!");
                if(libraryAuthorRepository.findAllBookFromAuthor(libraryAuthor.getIdAuthor())==null) {
                    libraryAuthorRepository.removeLibraryAuthorFromDatabase(libraryAuthor.getIdAuthor());
                }
            }
            else{
                System.out.println("The library book with id: " + id + " doesn't exist in library!!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /** Method for increasing/decreasing the number of copies of library book **/
    public void updateNumberOfCopies(int numberOfCopies, int id) {
        String updateCopiesSql = "UPDATE libraryBooks SET numberOfCopies=? WHERE idLibraryBook=?";

        Connection databaseConnection = DatabaseConfig.getDatabaseConnection();
        try {
            PreparedStatement preparedStatement = databaseConnection.prepareStatement(updateCopiesSql);
            preparedStatement.setInt(1, numberOfCopies);
            preparedStatement.setInt(2, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /** Method for getting a library book object from the database **/
    public LibraryBook getLibraryBookByName(String name, String lastName, String firstName, int year) {
        String selectSql = "SELECT * FROM libraryBooks WHERE name=? AND idAuthor=? AND yearOfPublication=?";

        Connection databaseConnection = DatabaseConfig.getDatabaseConnection();
        try {
            PreparedStatement preparedStatement = databaseConnection.prepareStatement(selectSql);
            preparedStatement.setString(1, name);
            LibraryAuthor la = libraryAuthorRepository.getLibraryAuthorByName(lastName,firstName);
            if(la == null){
                System.out.println("The book doesn't exist in the library!");
                return null;
            }
            preparedStatement.setInt(2, la.getIdAuthor());
            preparedStatement.setInt(3, year);
            ResultSet resultSet = preparedStatement.executeQuery();
            return mapToLibraryBook(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /** Method for displaying all library books */
    public void displayAllBooks() {
        String selectSql = "SELECT * FROM libraryBooks";

        Connection databaseConnection = DatabaseConfig.getDatabaseConnection();

        try {
            PreparedStatement preparedStatement = databaseConnection.prepareStatement(selectSql);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet == null){
                System.out.println("There are no books in the library!");
            }
            while (resultSet.next()) {
                LibraryBook lb = getLibraryBookFromDatabase(resultSet.getInt(1));
                System.out.println(lb);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
