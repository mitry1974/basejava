package sql;

import exception.ExistStorageException;
import exception.StorageException;

import java.sql.*;


public class SqlHelper {
    private ConnectionFactory connectionFactory;

    public SqlHelper(String dbUrl, String dbUser, String dbPassword) {
        connectionFactory = () -> DriverManager.getConnection(dbUrl, dbUser, dbPassword);
    }

    public interface PrepareStatement {
        void prepare(PreparedStatement ps) throws SQLException;
    }

    public interface ProceedResults {
        void proceed(ResultSet resultSet) throws SQLException;
    }

    public void runSql(String sql, PrepareStatement prepareStatement) {
        try (Connection conn = connectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            prepareStatement.prepare(ps);

        } catch (SQLException e) {
            if (e.getSQLState().equals("23505")) {
                throw new ExistStorageException("Resume exists");
            } else {
                throw new StorageException(e);
            }
        }
    }
}
