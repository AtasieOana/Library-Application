package repository;

import classes.*;
import config.DatabaseConfig;
import services.HelperService;

import java.sql.*;

public class LoanRepository {

    /** Method for inserting a loan object into the database **/
    public void insertLoanInDatabase(Loan loan) {
        String preparedSql = "{call insertLoan(?,?,?,?,?,?)}";

        Connection databaseConnection = DatabaseConfig.getDatabaseConnection();
        try {
            CallableStatement callableStatement = databaseConnection.prepareCall(preparedSql);
            callableStatement.setInt(2, loan.getBook().getIdLibraryBook());
            callableStatement.setString(3, loan.getReader().getCNP());
            callableStatement.setString(4, loan.getLibrarian().getCNP());
            callableStatement.setDate(5, HelperService.fromDateUtilToDateSql(loan.getLoanDate()));
            callableStatement.setInt(6, Loan.getLoanDays());
            callableStatement.registerOutParameter(1, Types.INTEGER);
            callableStatement.execute();
            System.out.println("The loan with id:" + callableStatement.getInt(1) + " was added!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /** Method for getting a loan object from the database **/
    public Loan getLoanFromDatabase(int idLoan) {
        String selectSql = "SELECT * FROM loans WHERE idLoan=?";
        Connection databaseConnection = DatabaseConfig.getDatabaseConnection();
        try {
            PreparedStatement preparedStatement = databaseConnection.prepareStatement(selectSql);
            preparedStatement.setInt(1, idLoan);
            ResultSet resultSet = preparedStatement.executeQuery();
            return mapToLoan(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    public Loan mapToLoan(ResultSet resultSet) throws SQLException {
        LibraryBookRepository libraryBookRepository = new LibraryBookRepository();
        ReaderRepository readerRepository = new ReaderRepository();
        LibrarianRepository librarianRepository = new LibrarianRepository();

        if (resultSet.next()){
            LibraryBook libraryBook = libraryBookRepository.getLibraryBookFromDatabase(resultSet.getInt(2));
            Reader reader = readerRepository.getReaderFromDatabase(resultSet.getString(3));
            Librarian librarian = librarianRepository.getLibrarianFromDatabase(resultSet.getString(4));
            return new Loan(resultSet.getInt(1), libraryBook, reader, librarian,resultSet.getDate(5));
        }
        return null;
    }

    /** Method for removing a loan object from the database **/
    public void removeLoanFromDatabase(int  idLoan) {
        String deleteSql = "DELETE FROM loans WHERE idLoan=?";
        Connection databaseConnection = DatabaseConfig.getDatabaseConnection();
        try {
            Loan loan = getLoanFromDatabase(idLoan);
            if(loan != null) {
                PreparedStatement preparedStatement = databaseConnection.prepareStatement(deleteSql);
                preparedStatement.setInt(1, idLoan);
                int resultSet = preparedStatement.executeUpdate();
                System.out.println("The loan with id:" + idLoan + " was removed!");
            }
            else{
                System.out.println("The loan with id:" + idLoan + " doesn't exist in library!!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /** Method for getting a loan object from the database **/
    public Loan getLoanFromDatabaseByParameters(int idBook, String CNP) {
        String selectSql = "SELECT * FROM loans WHERE idLibraryBook=? and idReader=?";
        Connection databaseConnection = DatabaseConfig.getDatabaseConnection();
        try {
            PreparedStatement preparedStatement = databaseConnection.prepareStatement(selectSql);
            preparedStatement.setInt(1, idBook);
            preparedStatement.setString(2, CNP);
            ResultSet resultSet = preparedStatement.executeQuery();
            return mapToLoan(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
