public class Money {
    private static long startTime = System.currentTimeMillis();
    private static int moneyIncrease = 5;
    private static float needTime = 10000;

    public static void addMoney()
    {
        long endTime = System.currentTimeMillis();
        long differenceTime = endTime - startTime;
        UserData.currentUser.getEconomy().setMoney(moneyIncrease * (differenceTime / needTime));
        startTime = System.currentTimeMillis();
    }
}
