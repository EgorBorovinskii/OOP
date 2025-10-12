import java.util.Scanner;

public class Army {
    private Long power;
    private int powerIncrease;

    Army(Long _power, int power_Increase)
    {
        this.power = _power;
        this.powerIncrease = power_Increase;
    }

    public void setPower(Long powerAdd)
    {
        this.power += powerAdd;
    }

    private String increasePower()
    {
        if (UserData.currentUser.getEconomy().getMoney() < UserData.currentUser.getEconomy().getMoneyForPower())
        {
            return Messages.notEnoughMoney + '\n' + Messages.needMoney + ": " + UserData.currentUser.getEconomy().getMoneyForPower();
        }
        else
        {
            UserData.currentUser.getEconomy().buyPower();
            power += powerIncrease;
            return "Успешно\nТекущая сила:" + String.valueOf(GetPower());
        }
    }

    private long GetPower()
    {
        return power;
    }

    public String mainArmy(String message)
    {
        Money.addMoney();
        switch (message)
        {
            case "/help":
            {
                break;
            }
            case "/exit":
            {
                Logic.exit();
                break;
            }
            case "увеличить силу": {
                return increasePower();
            }
            case "показать уровень силы":
            {
                return String.valueOf(GetPower());
            }
        }
        EventCheck.check();
        return Messages.unknownCommand;
    }
}

