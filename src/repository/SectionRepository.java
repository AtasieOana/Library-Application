package repository;

import classes.LibraryAuthor;
import classes.LibraryBook;
import classes.Section;
import config.DatabaseConfig;
import services.HelperService;

import java.sql.*;
import java.util.TreeSet;

public class SectionRepository {

    /** Method for inserting a section object into the database **/
    public void insertSectionInDatabase(Section section) {
        if(existence(section) == 1) {
            return;
        }
        String preparedSql = "{call insertSection(?,?)}";

        Connection databaseConnection = DatabaseConfig.getDatabaseConnection();
        try {
            CallableStatement callableStatement = databaseConnection.prepareCall(preparedSql);
            callableStatement.setString(2, section.getSectionType().toString());
            callableStatement.registerOutParameter(1, Types.VARCHAR);

            callableStatement.execute();
            System.out.println("The Section with type: " + section.getSectionType().toString() + " was added!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /** Method for getting a section object from the database **/
    public Section getSectionFromDatabase(String  sectionType) {
        String selectSql = "SELECT * FROM sections WHERE sectionType=?";

        Connection databaseConnection = DatabaseConfig.getDatabaseConnection();
        try {
            PreparedStatement preparedStatement = databaseConnection.prepareStatement(selectSql);
            preparedStatement.setString(1, sectionType);
            ResultSet resultSet = preparedStatement.executeQuery();
            return mapToSection(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    private Section mapToSection(ResultSet resultSet) throws SQLException {
        if (resultSet.next()){
            return HelperService.createSectionWithSectionType(resultSet.getString(1));
        }
        return null;
    }

    /** Method for finding all books from a section **/
    public TreeSet<LibraryBook> findAllBookFromSection(String sectionType){
        String selectSql = "SELECT * FROM libraryBooks WHERE sectionType=?";
        TreeSet<LibraryBook> libraryBooksList = new TreeSet<>();
        Connection databaseConnection = DatabaseConfig.getDatabaseConnection();
        try {
            PreparedStatement preparedStatement = databaseConnection.prepareStatement(selectSql);
            preparedStatement.setString(1, sectionType);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                LibraryAuthorRepository lar = new LibraryAuthorRepository();
                LibraryAuthor libraryAuthor = lar.getLibraryAuthorFromDatabase(resultSet.getInt(6));
                Section section = getSectionFromDatabase(resultSet.getString(7));
                libraryBooksList.add(new LibraryBook(resultSet.getInt(1), resultSet.getString(2),resultSet.getInt(3), resultSet.getInt(4),
                        resultSet.getString(5),libraryAuthor,section,resultSet.getInt(8)));
            }
            return libraryBooksList;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private int existence(Section section){
        if(getSectionFromDatabase(section.getSectionType().toString()) == null){
            return 0;
        }
        else{
            return 1;
        }
    }

}
