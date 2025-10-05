import java.util.Scanner;

public class Population {
    private static Scanner in = new Scanner(System.in);

    private Long loyalty;
    private int loyaltyIncrease;

    Population(Long _loyalty, int loyalty_Increase){
        this.loyalty = _loyalty;
        this.loyaltyIncrease = loyalty_Increase;
    }

    public void setLoyalty(Long loyaltyAdd)
    {
        this.loyalty += loyaltyAdd;
    }

    private void increaseLoyalty()
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

    private void showLoyalty()
    {
        System.out.println(loyalty);
    }

    public void mainPopulation()
    {
        System.out.println(Messages.categoriesForPopulation);
        String message = in.nextLine().toLowerCase();
        while (!message.equals("/back")) {
            Money.addMoney();
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
                    Logic.help();
                    break;
                }
                case "/exit": {
                    Logic.exit();
                    break;
                }
                case "Увеличить лояльность": {
                    increaseLoyalty();
                    break;
                }
                case "Показать уровень лояльности": {
                    showLoyalty();
                    break;
                }
                default: {
                    System.out.println(Messages.unknownCommand);
                    break;
                }
            }
            EventCheck.check();
            message = in.nextLine();
        }
    }
}
