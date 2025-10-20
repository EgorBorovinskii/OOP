public class Money {
    private static long startTime = System.currentTimeMillis();
    private static int moneyIncrease = 5;
    private static float needTime = 10000;

    public static void addMoney(String nick)
    {
        long endTime = System.currentTimeMillis();
        long differenceTime = endTime - startTime;
        UserData.list.get(nick).getEconomy().setMoney(moneyIncrease * (differenceTime / needTime));
        startTime = System.currentTimeMillis();
    }
}
