package repository;

import classes.LibraryAuthor;
import classes.LibraryBook;
import classes.Section;
import config.DatabaseConfig;

import java.sql.*;
import java.util.TreeSet;

public class LibraryAuthorRepository {

    /** Method for inserting a library author object into the database **/
    public LibraryAuthor insertLibraryAuthorInDatabase(LibraryAuthor libraryAuthor) {
        String preparedSql = "{call insertLibraryAuthor(?,?,?)}";

        Connection databaseConnection = DatabaseConfig.getDatabaseConnection();
        try {
            CallableStatement callableStatement = databaseConnection.prepareCall(preparedSql);
            callableStatement.setString(2, libraryAuthor.getLastName());
            callableStatement.setString(3, libraryAuthor.getFirstName());

            callableStatement.registerOutParameter(1, Types.INTEGER);

            callableStatement.execute();
            System.out.println("The library author with id: " + callableStatement.getInt(1) + " was added!");

            return new LibraryAuthor(callableStatement.getInt(1),libraryAuthor.getLastName(),
                    libraryAuthor.getFirstName());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /** Method for getting a library author object from the database **/
    public LibraryAuthor getLibraryAuthorFromDatabase(int idLibraryAuthor) {
        String selectSql = "SELECT * FROM libraryAuthors WHERE idLibraryAuthor=?";

        Connection databaseConnection = DatabaseConfig.getDatabaseConnection();
        try {
            PreparedStatement preparedStatement = databaseConnection.prepareStatement(selectSql);
            preparedStatement.setInt(1, idLibraryAuthor);
            ResultSet resultSet = preparedStatement.executeQuery();
            return mapToLibraryAuthor(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    private LibraryAuthor mapToLibraryAuthor(ResultSet resultSet) throws SQLException {
        if (resultSet.next()){
            return new LibraryAuthor(resultSet.getInt(1), resultSet.getString(2),resultSet.getString(3));
        }
        return null;
    }

    /** Method for removing a library author object from the database **/
    public void removeLibraryAuthorFromDatabase(int  id) {
        String deleteSql = "DELETE FROM libraryAuthors WHERE idLibraryAuthor=?";

        Connection databaseConnection = DatabaseConfig.getDatabaseConnection();
        try {
            if(getLibraryAuthorFromDatabase(id) != null) {
                PreparedStatement preparedStatement = databaseConnection.prepareStatement(deleteSql);
                preparedStatement.setInt(1, id);
                int resultSet = preparedStatement.executeUpdate();
                System.out.println("The library author with id:" + id + " was removed!");
            }
            else{
                System.out.println("The library author with id:" + id + " doesn't exist in library!!");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /** Method for finding all books written by library author**/
    public TreeSet<LibraryBook> findAllBookFromAuthor(int id){
        String selectSql = "SELECT * FROM libraryBooks WHERE idAuthor=?";
        TreeSet<LibraryBook> libraryBooksList = new TreeSet<>();
        Connection databaseConnection = DatabaseConfig.getDatabaseConnection();
        try {
            PreparedStatement preparedStatement = databaseConnection.prepareStatement(selectSql);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                LibraryAuthor libraryAuthor = getLibraryAuthorFromDatabase(resultSet.getInt(6));
                SectionRepository sectionRepository = new SectionRepository();
                Section section = sectionRepository.getSectionFromDatabase(resultSet.getString(7));
                libraryBooksList.add(new LibraryBook(resultSet.getInt(1), resultSet.getString(2),resultSet.getInt(3), resultSet.getInt(4),
                        resultSet.getString(5),libraryAuthor,section,resultSet.getInt(8)));
            }
            return libraryBooksList;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /** Method for getting a library author object from the database **/
    public LibraryAuthor getLibraryAuthorByName(String lastname, String firstname) {
        String selectSql = "SELECT * FROM libraryAuthors WHERE lastName=? AND firstName=?";

        Connection databaseConnection = DatabaseConfig.getDatabaseConnection();
        try {
            PreparedStatement preparedStatement = databaseConnection.prepareStatement(selectSql);
            preparedStatement.setString(1, lastname);
            preparedStatement.setString(2, firstname);
            ResultSet resultSet = preparedStatement.executeQuery();
            return mapToLibraryAuthor(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

}
