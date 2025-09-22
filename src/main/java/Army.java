import java.util.Scanner;

public class Army {
    private int power;
    private int powerIncrease;

    Army(int _power, int power_Increase)
    {
        this.power = _power;
        this.powerIncrease = power_Increase;
    }

    private void IncreasePower()
    {
        if (UserData.currentUser.getEconomy().getMoney() < UserData.currentUser.getEconomy().getMoneyForPower())
        {
            System.out.println(Messages.notEnoughMoney);
            System.out.println(Messages.needMoney + ": " + UserData.currentUser.getEconomy().getMoneyForPower());
        }
        else
        {
            UserData.currentUser.getEconomy().buyPower();
            power += powerIncrease;
        }
    }

    private void ShowPower()
    {
        System.out.println(power);
    }

    public void MainArmy()
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
