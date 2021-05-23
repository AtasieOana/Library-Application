package repository;

import classes.*;
import config.DatabaseConfig;

import java.sql.*;

public class RequiredBookRepository {

    /** Method for inserting a required book object into the database **/
    public void insertRequiredBookInDatabase(RequiredBook requiredBook) {
        String preparedSql = "{call insertRequiredBook(?,?,?,?,?,?,?)}";

        Connection databaseConnection = DatabaseConfig.getDatabaseConnection();
        try {
            CallableStatement callableStatement = databaseConnection.prepareCall(preparedSql);
            callableStatement.setString(2, requiredBook.getName());
            callableStatement.setInt(3, requiredBook.getNumberOfPages());

            callableStatement.setInt(4, requiredBook.getYearOfPublication());
            callableStatement.setString(5, requiredBook.getLanguage());
            callableStatement.setInt(6, requiredBook.getAuthor().getIdAuthor());
            callableStatement.setInt(7, requiredBook.getNumberOfRequests());

            callableStatement.registerOutParameter(1, Types.INTEGER);

            callableStatement.execute();
            System.out.println("The required book with id:" + callableStatement.getInt(1) + " was added!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /** Method for getting a required book object from the database **/
    public RequiredBook getRequiredBookFromDatabase(int idRequiredBook) {
        String selectSql = "SELECT * FROM requiredBooks WHERE idRequiredBook=?";

        Connection databaseConnection = DatabaseConfig.getDatabaseConnection();
        try {
            PreparedStatement preparedStatement = databaseConnection.prepareStatement(selectSql);
            preparedStatement.setInt(1, idRequiredBook);
            ResultSet resultSet = preparedStatement.executeQuery();
            return mapToRequiredBook(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    public RequiredBook mapToRequiredBook(ResultSet resultSet) throws SQLException {
        AuthorRepository authorRepository = new AuthorRepository();
        if (resultSet.next()){
            Author author = authorRepository.getAuthorFromDatabase(resultSet.getInt(6));
            return new RequiredBook(resultSet.getInt(1), resultSet.getString(2),resultSet.getInt(3), resultSet.getInt(5),
                    resultSet.getString(6),author,resultSet.getInt(7));
        }
        return null;
    }

    /** Method for removing a required book object from the database **/
    public void removeRequiredBookFromDatabase(int  id) {
        String deleteSql = "DELETE FROM requiredBooks WHERE idRequiredBook=?";
        Connection databaseConnection = DatabaseConfig.getDatabaseConnection();
        try {
            RequiredBook requiredBook = getRequiredBookFromDatabase(id);
            if(requiredBook != null) {
                PreparedStatement preparedStatement = databaseConnection.prepareStatement(deleteSql);
                preparedStatement.setInt(1, id);
                ResultSet resultSet = preparedStatement.executeQuery();
                System.out.println("The required book with id:" + id + " was removed!");
            }
            else{
                System.out.println("The required book with id:" + id + " doesn't exist in library!!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /** Method for increasing/decreasing the number of requests of required book **/
    public void updateNumberOfRequests(int numberOfRequest, int id) {
        String updateCopiesSql = "UPDATE requiredBooks SET numberOfRequests=? WHERE idRequiredBook=?";

        Connection databaseConnection = DatabaseConfig.getDatabaseConnection();
        try {
            PreparedStatement preparedStatement = databaseConnection.prepareStatement(updateCopiesSql);
            preparedStatement.setInt(1, numberOfRequest);
            preparedStatement.setInt(2, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /** Method for getting the most required book**/
    public RequiredBook getMostRequiredBook() {
        String selectSql = "SELECT * FROM requiredBooks WHERE numberOfRequests = (SELECT max(numberOfRequests)"+
                           "FROM requiredBooks)";

        Connection databaseConnection = DatabaseConfig.getDatabaseConnection();
        try {
            PreparedStatement preparedStatement = databaseConnection.prepareStatement(selectSql);
            ResultSet resultSet = preparedStatement.executeQuery();
            return mapToRequiredBook(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /** Method for getting a required book object from the database **/
    public RequiredBook getRequiredBookByName(String name) {
        String selectSql = "SELECT * FROM requiredBooks WHERE name=?";

        Connection databaseConnection = DatabaseConfig.getDatabaseConnection();
        try {
            PreparedStatement preparedStatement = databaseConnection.prepareStatement(selectSql);
            preparedStatement.setString(1, name);
            ResultSet resultSet = preparedStatement.executeQuery();
            return mapToRequiredBook(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
