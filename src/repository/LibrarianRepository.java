package repository;

import classes.Librarian;
import classes.Reader;
import config.DatabaseConfig;
import services.HelperService;

import java.sql.*;

public class LibrarianRepository {

    /** Method for inserting a librarian object into the database **/
    public void insertLibrarianInDatabase(Librarian librarian) {
        if(existence(librarian) == 1) {
            System.out.println("CNP already exist in database!");
            return;
        }

        String preparedSql = "call insertLibrarian(?,?,?,?,?,?,?)";

        Connection databaseConnection = DatabaseConfig.getDatabaseConnection();
        try {
            CallableStatement callableStatement = databaseConnection.prepareCall(preparedSql);
            callableStatement.setString(2, librarian.getCNP());
            callableStatement.setString(3, librarian.getLastName());
            callableStatement.setString(4, librarian.getFirstName());
            callableStatement.setDate(5, HelperService.fromDateUtilToDateSql(librarian.getDateOfBirth()));
            callableStatement.setString(6, librarian.getAddress());
            callableStatement.setInt(7, librarian.getSalary());
            callableStatement.registerOutParameter(1, Types.VARCHAR);
            callableStatement.execute();
            System.out.println("The librarian with CNP: " + callableStatement.getString(2) + " was added!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /** Method for getting a librarian object from the database **/
    public Librarian getLibrarianFromDatabase(String  cnp) {
        String selectSql = "SELECT * FROM librarians WHERE cnp=?";

        Connection databaseConnection = DatabaseConfig.getDatabaseConnection();
        try {
            PreparedStatement preparedStatement = databaseConnection.prepareStatement(selectSql);
            preparedStatement.setString(1, cnp);
            ResultSet resultSet = preparedStatement.executeQuery();
            return mapToLibrarian(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    private Librarian mapToLibrarian(ResultSet resultSet) throws SQLException {
        if (resultSet.next()){
            return new Librarian(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3),
                    resultSet.getDate(4), resultSet.getString(5),resultSet.getInt(6));
        }
        return null;
    }

    /** Method for modifying the last name of a librarian **/
    public void updateLastName(String newLastName, String cnp) {
        String updateCopiesSql = "UPDATE librarians SET lastName=? WHERE cnp=?";

        Connection databaseConnection = DatabaseConfig.getDatabaseConnection();
        try {
            PreparedStatement preparedStatement = databaseConnection.prepareStatement(updateCopiesSql);
            preparedStatement.setString(1, newLastName);
            preparedStatement.setString(2, cnp);
            preparedStatement.executeUpdate();
            System.out.println("Librarian with CNP: " + cnp + " was modified!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /** Method for removing a reader object from the database **/
    public void removeLibrarianFromDatabase(String  cnp) {
        String deleteSql = "DELETE FROM librarians WHERE cnp=?";

        Connection databaseConnection = DatabaseConfig.getDatabaseConnection();
        try {
            if(getLibrarianFromDatabase(cnp) != null) {
                PreparedStatement preparedStatement = databaseConnection.prepareStatement(deleteSql);
                preparedStatement.setString(1, cnp);
                int resultSet = preparedStatement.executeUpdate();
                System.out.println("The librarian with CNP: " + cnp + " was removed!");
            }
            else{
                System.out.println("The librarian with CNP: " + cnp + " doesn't exist in library!!");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private int existence(Librarian librarian){
        if(getLibrarianFromDatabase(librarian.getCNP()) == null){
            return 0;
        }
        else{
            return 1;
        }
    }


}
