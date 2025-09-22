import java.util.Scanner;

public class Population {
    private static Scanner in = new Scanner(System.in);

    private int loyalty;
    private int loyaltyIncrease;

    Population(int _loyalty, int loyalty_Increase){
        this.loyalty = _loyalty;
        this.loyaltyIncrease = loyalty_Increase;
    }

    private void IncreaseLoyalty()
    {
        if (UserData.currentUser.getEconomy().getMoney() < UserData.currentUser.getEconomy().getMoneyForLoyality())
        {
            System.out.println(Messages.notEnoughMoney);
            System.out.println(Messages.needMoney + ": " + UserData.currentUser.getEconomy().getMoneyForLoyality());
        }
        else
        {
            UserData.currentUser.getEconomy().buyLoyality();
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
