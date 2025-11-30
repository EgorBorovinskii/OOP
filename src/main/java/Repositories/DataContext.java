package Repositories;

import Configurations.AppSettings;

import java.sql.*;

public class DataContext {
    private static final String connectionUrl = AppSettings.getInstance().databaseConnectionUrl;
    private static Connection connection;

    public DataContext() {}

    public static void obtainDatabase() throws SQLException {
        connection = DriverManager.getConnection(connectionUrl);
        DataContext dataContext = new DataContext();
        UsersRepository.createTableIfNotExists(dataContext);
        SessionsRepository.createTableIfNotExists(dataContext);
    }

    public void execute(String sql) throws SQLException
    {
        Statement stmt = connection.createStatement();

        stmt.execute(sql);
    }

    public long insert(String sql) throws SQLException
    {
        PreparedStatement stmt = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
        int rows = stmt.executeUpdate();
        ResultSet resultSet = stmt.getGeneratedKeys();
        return resultSet.getLong(1);
    }

    public ResultSet select(String sql) throws SQLException
    {
        Statement stmt = connection.createStatement();
        return stmt.executeQuery(sql);
    }
}
