package DataModels;

import java.text.MessageFormat;

public class UserDto {
    public long id;
    public Long telegramID;
    public float money;
    public long power;
    public long loyalty;

    public UserDto(Long id, Long telegramID, float money, long power, long loyalty)
    {
        this.id = id;
        this.telegramID = telegramID;
        this.money = money;
        this.power = power;
        this.loyalty = loyalty;
    }

    public UserDto(Long telegramID, float money, long power, long loyalty)
    {
        this.telegramID = telegramID;
        this.money = money;
        this.power = power;
        this.loyalty = loyalty;
    }

    public String getResourcesText()
    {
        return MessageFormat.format("Деньги: {0}\nСила: {1}\nЛояльность: {2}",
                money, power, loyalty);
    }
}
