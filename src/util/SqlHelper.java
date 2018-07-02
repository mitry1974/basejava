package util;

import exception.ExistStorageException;
import exception.NotExistStorageException;
import exception.StorageException;
import sql.ConnectionFactory;
import storage.SqlStorage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class SqlHelper {
    public interface PrepareStatement {
        void prepare(PreparedStatement ps) throws SQLException;
    }

    public interface ProceedResults {
        void proceed(ResultSet resultSet) throws SQLException;
    }

    public static boolean runSqlNoResults(ConnectionFactory cfactory, String sql) {
        return runSqlNoResults(cfactory, sql, null);
    }

    public static boolean runSqlNoResults(ConnectionFactory cfactory, String sql, PrepareStatement prepareStatement) {
        try (Connection conn = cfactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            if (prepareStatement != null) {
                prepareStatement.prepare(ps);
            }
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            if(e.getSQLState().equals("23505")) {
                throw new ExistStorageException("Resume exists");
            }
            else {
                throw new StorageException(e);
            }
        }
    }

    public static boolean runSql(ConnectionFactory cfactory, String sql, ProceedResults proceedResults) {
        return runSql(cfactory, sql, null, proceedResults);
    }
    public static boolean runSql(ConnectionFactory cfactory, String sql, PrepareStatement prepareStatement, ProceedResults proceedResults) {
        try (Connection conn = cfactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            if(prepareStatement!=null){
                prepareStatement.prepare(ps);
            }

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                proceedResults.proceed(rs);
            }

        } catch (SQLException e) {
            throw new StorageException(e);
        }

        return true;
    }
}
