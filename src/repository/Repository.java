package repository;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Repository {
    private static final Repository repository = new Repository();

    private Repository() {
    }

    public static Repository getRepository() {
        return repository;
    }

    public void executeSql(Connection connection, String sql) throws SQLException {
        Statement stmt = connection.createStatement();
        stmt.execute(sql);
    }

    public void executeUpdateSql(Connection connection, String sql) throws SQLException {
        Statement stmt = connection.createStatement();
        stmt.executeUpdate(sql);
    }

    public ResultSet executeQuerySql(Connection connection, String sql) throws SQLException {
        Statement stmt = connection.createStatement();
        return stmt.executeQuery(sql);
    }
}
