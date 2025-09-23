public class Money {
    private static long startTime = System.currentTimeMillis();
    private static int moneyIncrease = 5;
    private static long needTime = 10000;

    public static void addMoney()
    {
        long endTime = System.currentTimeMillis();
        long differenceTime = endTime - startTime;
        if (differenceTime > needTime)
        {
            UserData.currentUser.getEconomy().setMoney(moneyIncrease * (int)(differenceTime / needTime));
            startTime = System.currentTimeMillis() + (differenceTime % needTime);
        }
    }
}
