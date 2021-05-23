package repository;

import classes.Reader;
import config.DatabaseConfig;
import services.HelperService;

import java.sql.*;

public class ReaderRepository {

    /** Method for inserting a reader object into the database **/
    public void insertReaderInDatabase(Reader reader) {
        if(existence(reader) == 1) {
            System.out.println("CNP already exist in database!");
            return;
        }

        String preparedSql = "call insertReader(?,?,?,?,?,?,?)";

        Connection databaseConnection = DatabaseConfig.getDatabaseConnection();
        try {
            CallableStatement callableStatement = databaseConnection.prepareCall(preparedSql);
            callableStatement.setString(2, reader.getCNP());
            callableStatement.setString(3, reader.getLastName());
            callableStatement.setString(4, reader.getFirstName());
            callableStatement.setDate(5, HelperService.fromDateUtilToDateSql(reader.getDateOfBirth()));
            callableStatement.setString(6, reader.getAddress());
            callableStatement.setString(7, reader.getPhoneNumber());
            callableStatement.registerOutParameter(1, Types.VARCHAR);
            callableStatement.execute();
            System.out.println("The reader with CNP: " + reader.getCNP() + " was added!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /** Method for getting a reader object from the database **/
    public Reader getReaderFromDatabase(String  cnp) {
        String selectSql = "SELECT * FROM readers WHERE cnp=?";

        Connection databaseConnection = DatabaseConfig.getDatabaseConnection();
        try {
            PreparedStatement preparedStatement = databaseConnection.prepareStatement(selectSql);
            preparedStatement.setString(1, cnp);
            ResultSet resultSet = preparedStatement.executeQuery();
            return mapToReader(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /** Method for removing a reader object from the database **/
    public void removeReaderFromDatabase(String  cnp) {
        String deleteSql = "DELETE FROM readers WHERE cnp=?";

        Connection databaseConnection = DatabaseConfig.getDatabaseConnection();
        try {
            if(getReaderFromDatabase(cnp) != null) {
                PreparedStatement preparedStatement = databaseConnection.prepareStatement(deleteSql);
                preparedStatement.setString(1, cnp);
                int resultSet = preparedStatement.executeUpdate();
                System.out.println("The reader with CNP: " + cnp + " was removed!");
            }
            else{
                System.out.println("The reader with CNP: " + cnp + " doesn't exist in library!!");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



    private Reader mapToReader(ResultSet resultSet) throws SQLException {
        if (resultSet.next()){
            return new Reader(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3),
                    resultSet.getDate(4), resultSet.getString(5),resultSet.getString(6));
        }
        return null;
    }

    private int existence(Reader reader){
        if(getReaderFromDatabase(reader.getCNP()) == null){
            return 0;
        }
        else{
            return 1;
        }
    }

}
