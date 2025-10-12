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

    private String increaseLoyalty()
    {
        if (UserData.currentUser.getEconomy().getMoney() < UserData.currentUser.getEconomy().getMoneyForLoyality())
        {
            return Messages.notEnoughMoney + '\n' + Messages.needMoney + ": " + UserData.currentUser.getEconomy().getMoneyForLoyality();
        }
        else
        {
            UserData.currentUser.getEconomy().buyLoyality();
            loyalty += loyaltyIncrease;
            return "Успешно\nТекущая лояльность: " + String.valueOf(GetLoyalty());
        }
    }

    private long GetLoyalty()
    {
        return loyalty;
    }

    public String mainPopulation(String message)
    {
         Money.addMoney();
         switch (message) {
             case "/help": {
                 break;
             }
             case "/exit": {
                 Logic.exit();
                 break;
             }
             case "увеличить лояльность": {
                 return increaseLoyalty();
             }
             case "показать уровень лояльности": {
                 return String.valueOf(GetLoyalty());
             }
         }
         EventCheck.check();
         return Messages.unknownCommand;
    }
}

