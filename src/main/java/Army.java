import java.util.Scanner;

public class Army {
    private int power;
    private int needMoney;
    private int powerIncrease;

    Army(int _power, int need_Money, int power_Increase)
    {
        this.power = _power;
        this.needMoney = need_Money;
        this.powerIncrease = power_Increase;
    }

    private void IncreasePower()
    {
        if (Economy.money < needMoney)
        {
            System.out.println(Messages.notEnoughMoney);
            System.out.println(Messages.needMoney + ": " + needMoney);
        }
        else
        {
            Economy.money -= needMoney;
            power += powerIncrease;
        }
    }

    private void ShowPower()
    {
        System.out.println(power);
    }

    private MainArmy()
    {
        Scanner in = new Scanner(System.in);
        System.out.println(Messages.categoriesForArmy);
        String message = in.nextLine().toLowerCase();
        while (!message.equals("/back"))
        {
            switch ((message)){
                case "1":
                {
                    message = "Увеличить силу";
                    break;
                }
                case "2":
                {
                    message = "Показать уровень силы";
                    break;
                }
            }
            switch (message)
            {
                case "/help":
                {
                    Logic.Help();
                    break;
                }
                case "/exit":
                {
                    Logic.Exit();
                    break;
                }
                case "Увеличить силу":
                {
                    IncreasePower();
                    break;
                }
                case "Показать уровень силы":
                {
                    ShowPower();
                    break;
                }
                default:
                {
                    System.out.println(Messages.unknownCommand);
                    break;
                }
            }
            EventCheck.Check();
            message = in.nextLine();
        }
    }
}
