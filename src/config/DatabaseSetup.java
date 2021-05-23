package config;
import repository.Repository;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DatabaseSetup {

    public void setUp() {
        String createTableLibrariansSql = "CREATE TABLE IF NOT EXISTS librarians" +
                "(cnp varchar(13) PRIMARY KEY, lastName varchar(30)," +
                "firstName varchar(30), dateOfBirth date, address varchar(50), salary int)";
        String createTableReadersSql = "CREATE TABLE IF NOT EXISTS readers" +
                "(cnp varchar(13) PRIMARY KEY, lastName varchar(30)," +
                "firstName varchar(30), dateOfBirth date, address varchar(50), phoneNumber varchar(10))";
        String createTableAuthorSql = "CREATE TABLE IF NOT EXISTS authors" +
                "(idAuthor int PRIMARY KEY AUTO_INCREMENT, lastName varchar(30)," +
                "firstName varchar(30))";
        String createTableLibraryAuthorSql = "CREATE TABLE IF NOT EXISTS libraryAuthors" +
                "(idLibraryAuthor int PRIMARY KEY AUTO_INCREMENT, lastName varchar(30)," +
                "firstName varchar(30))";
        String createTableSectionSql = "CREATE TABLE IF NOT EXISTS sections" +
                "(sectionType varchar(20) PRIMARY KEY)";
        String createTableLibraryBookSql = "CREATE TABLE IF NOT EXISTS libraryBooks" +
                "(idLibraryBook int PRIMARY KEY AUTO_INCREMENT, name varchar(50), numberOfPages int, " +
                "yearOfPublication int, language varchar(30), idAuthor int, FOREIGN KEY (idAuthor) REFERENCES libraryAuthors(idLibraryAuthor)," +
                "sectionType varchar(20), FOREIGN KEY (sectionType) REFERENCES sections(sectionType),numberOfCopies int)";
        String createTableRequiredBookSql = "CREATE TABLE IF NOT EXISTS requiredBooks" +
                "(idRequiredBook int PRIMARY KEY AUTO_INCREMENT, name varchar(50), numberOfPages int, " +
                "yearOfPublication int, language varchar(30),idAuthor int, FOREIGN KEY (idAuthor) REFERENCES authors(idAuthor)" +
                ",numberOfRequests int)";
        String createTableLoanSql = "CREATE TABLE IF NOT EXISTS loans" +
                "(idLoan int PRIMARY KEY AUTO_INCREMENT, idLibraryBook int, FOREIGN KEY (idLibraryBook) REFERENCES" +
                " libraryBooks(idLibraryBook), idReader varchar(13), FOREIGN KEY (idReader) REFERENCES readers(cnp)," +
                "idLibrarian varchar(13), FOREIGN KEY (idLibrarian) REFERENCES librarians(cnp), loanDate date, loanDays int)";

        Connection databaseConnection = DatabaseConfig.getDatabaseConnection();
        Repository repository = Repository.getRepository();

        try {
            repository.executeSql(databaseConnection, createTableLibrariansSql);
            repository.executeSql(databaseConnection, createTableReadersSql);
            repository.executeSql(databaseConnection, createTableAuthorSql);
            repository.executeSql(databaseConnection, createTableLibraryAuthorSql);
            repository.executeSql(databaseConnection, createTableSectionSql);
            repository.executeSql(databaseConnection, createTableLibraryBookSql);
            repository.executeSql(databaseConnection, createTableRequiredBookSql);
            repository.executeSql(databaseConnection, createTableLoanSql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
