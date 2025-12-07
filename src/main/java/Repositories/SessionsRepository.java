package Repositories;

import DataModels.Session;
import DataModels.UserDto;

import java.sql.*;
import java.text.MessageFormat;

public class SessionsRepository {
    public static void createTableIfNotExists(DataContext dataContext) throws SQLException
    {
        String sql = "CREATE TABLE IF NOT EXISTS session (\n"
                + " id INTEGER PRIMARY KEY AUTOINCREMENT, \n"
                + " playerOneID INTEGER, \n"
                + " playerTwoID INTEGER\n"
                + ");";
        dataContext.execute(sql);
    }

    public static void add(DataContext dataContext, Session session) throws SQLException
    {
        String sql = MessageFormat.format("INSERT INTO session (playerOneID, playerTwoID) VALUES ({0}, {1})",
                String.valueOf(session.playerOneID), String.valueOf(session.playerTwoID));

        session.id = dataContext.insert(sql);
    }

    public static void update(DataContext dataContext, Session session) throws SQLException
    {
        String sql = MessageFormat.format("UPDATE session SET playerOneID = {0}, playerTwoID = {1} WHERE id = {2}",
                String.valueOf(session.playerOneID), String.valueOf(session.playerTwoID), String.valueOf(session.id));

        dataContext.execute(sql);
    }

    public static Session get(DataContext dataContext, long id) throws SQLException
    {
        String sql = MessageFormat.format("SELECT * FROM session WHERE id = {0}",
                String.valueOf(id));
        ResultSet resultSet = dataContext.select(sql);
        if (resultSet.getLong("id") == 0)
            return null;

        return new Session(
                resultSet.getLong("id"),
                resultSet.getLong("playerOneID"),
                resultSet.getLong("playerTwoID")
        );
    }

    public static void delete(DataContext dataContext, Long id) throws SQLException
    {
        String sql = MessageFormat.format("DELETE FROM session WHERE id = {0}", String.valueOf(id));
        dataContext.execute(sql);
    }
    public static Long getSessionIDbyUser(DataContext dataContext, UserDto user) throws SQLException{
        String sql = MessageFormat.format("SELECT id FROM session WHERE playerOneID = {0} OR playerTwoID = {0}", String.valueOf(user.telegramID));
        ResultSet id = dataContext.select(sql);
        return id.getLong("id");
    }
}
