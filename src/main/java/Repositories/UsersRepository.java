package Repositories;

import DataModels.UserDto;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.MessageFormat;

public class UsersRepository {
    public static void createTableIfNotExists(DataContext dataContext) throws SQLException
    {
        String sql = "CREATE TABLE IF NOT EXISTS user (\n"
                + " id INTEGER PRIMARY KEY AUTOINCREMENT, \n"
                + " telegramID INTEGER, \n"
                + " money REAL, \n"
                + " power INTEGER, \n"
                + " loyalty INTEGER\n"
                + ");";
        dataContext.execute(sql);
    }

    public static void add(DataContext dataContext, UserDto userDto) throws SQLException
    {
        String sql = MessageFormat.format("INSERT INTO user (telegramID, money, power, loyalty) VALUES ({0}, {1}, {2}, {3})",
                String.valueOf(userDto.telegramID), String.valueOf(userDto.money), String.valueOf(userDto.power), String.valueOf(userDto.loyalty));
        userDto.id = dataContext.insert(sql);
    }

    public static void updateByTelegramID(DataContext dataContext, UserDto userDto) throws SQLException
    {
        String sql = MessageFormat.format("UPDATE user SET money = {0}, power = {1}, loyalty = {2} WHERE telegramID = {3}",
                String.valueOf(userDto.money), String.valueOf(userDto.power), String.valueOf(userDto.loyalty), String.valueOf(userDto.telegramID));
        dataContext.execute(sql);
    }

    public static UserDto getByTelegramID(DataContext dataContext, Long id) throws SQLException
    {
        String sql = MessageFormat.format("SELECT * FROM user WHERE telegramID = {0}", String.valueOf(id));
        ResultSet resultSet = dataContext.select(sql);
        if (resultSet.getLong("id") == 0)
            return null;

        return new UserDto(
                resultSet.getLong("id"),
                resultSet.getLong("telegramID"),
                resultSet.getFloat("money"),
                resultSet.getLong("power"),
                resultSet.getLong("loyalty"));
    }

    public static void deleteByTelegramID(DataContext dataContext, Long id) throws SQLException
    {
        String sql = MessageFormat.format("DELETE FROM user WHERE telegramID = {0}", String.valueOf(id));
        dataContext.execute(sql);
    }
}
