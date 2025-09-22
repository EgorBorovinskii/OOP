import java.util.Scanner;

public class Population {
    private static Scanner in = new Scanner(System.in);

    private int loyalty;
    private int needMoney;
    private int loyaltyIncrease;

    Population(int loayl, int need, int inc){
        this.loyalty = loayl;
        this.needMoney = need;
        this.loyaltyIncrease = inc;
    }

    private void IncreaseLoyalty()
    {
        if (Economy.money < needMoney)
        {
            System.out.println(Messages.notEnoughMoney);
            System.out.println(Messages.needMoney + ": " + needMoney);
        }
        else
        {
            Economy.money -= needMoney;
            loyalty += loyaltyIncrease;
        }
    }

    private void ShowLoyalty()
    {
        System.out.println(loyalty);
    }

    public void MainPopulation()
    {
        System.out.println(Messages.categoriesForPopulation);
        String message = in.nextLine().toLowerCase();
        while (!message.equals("/back")) {
            switch ((message)){
                case "1":
                {
                    message = "Увеличить лояльность";
                    break;
                }
                case "2":
                {
                    message = "Показать уровень лояльности";
                    break;
                }
            }
            switch (message) {
                case "/help": {
                    Logic.Help();
                    break;
                }
                case "/exit": {
                    Logic.Exit();
                    break;
                }
                case "Увеличить лояльность": {
                    IncreaseLoyalty();
                    break;
                }
                case "Показать уровень лояльности": {
                    ShowLoyalty();
                    break;
                }
                default: {
                    System.out.println(Messages.unknownCommand);
                    break;
                }
            }
            EventCheck.Check();
            message = in.nextLine();
        }
    }
}
