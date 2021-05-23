package repository;

import classes.Author;
import classes.LibraryAuthor;
import config.DatabaseConfig;

import java.sql.*;

public class AuthorRepository {

    /** Method for getting a author object from the database **/
    public Author getAuthorFromDatabase(int idAuthor) {
        String selectSql = "SELECT * FROM authors WHERE idAuthor=?";

        Connection databaseConnection = DatabaseConfig.getDatabaseConnection();
        try {
            PreparedStatement preparedStatement = databaseConnection.prepareStatement(selectSql);
            preparedStatement.setInt(1, idAuthor);
            ResultSet resultSet = preparedStatement.executeQuery();
            return mapToAuthor(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    private Author mapToAuthor(ResultSet resultSet) throws SQLException {
        if (resultSet.next()){
            return new Author(resultSet.getInt(1), resultSet.getString(2),resultSet.getString(3));
        }
        return null;
    }

    /** Method for getting a library author object from the database **/
    public Author getAuthorByName(String lastname, String firstname) {
        String selectSql = "SELECT * FROM authors WHERE lastName=? AND firstName=?";

        Connection databaseConnection = DatabaseConfig.getDatabaseConnection();
        try {
            PreparedStatement preparedStatement = databaseConnection.prepareStatement(selectSql);
            preparedStatement.setString(1, lastname);
            preparedStatement.setString(2, firstname);
            ResultSet resultSet = preparedStatement.executeQuery();
            return mapToAuthor(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /** Method for inserting a author object into the database **/
    public Author insertAuthorInDatabase(Author author) {
        String preparedSql = "{call insertAuthor(?,?,?)}";

        Connection databaseConnection = DatabaseConfig.getDatabaseConnection();
        try {
            CallableStatement callableStatement = databaseConnection.prepareCall(preparedSql);
            callableStatement.setString(2, author.getLastName());
            callableStatement.setString(3, author.getFirstName());

            callableStatement.registerOutParameter(1, Types.INTEGER);

            callableStatement.execute();
            System.out.println("The author with id:" + callableStatement.getInt(1) + " was added!");
            return new Author(callableStatement.getInt(1),author.getLastName(),
                    author.getFirstName());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

}
