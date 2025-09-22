public class Money {
    private static long startTime = System.currentTimeMillis();
    private static int moneyIncrease = 5;

    public static void addMoney()
    {
        long endTime = System.currentTimeMillis();
        if (endTime - startTime > 10000)
        {
            UserData.currentUser.getEconomy().setMoney(moneyIncrease);
            startTime = System.currentTimeMillis();
        }
    }
}
