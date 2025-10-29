public class Money {
    private long startTime = System.currentTimeMillis();
    private final int moneyIncrease;
    private final float needTime;

    Money(int inc, int time){
        moneyIncrease = inc;
        needTime = time;
    }

    public void addMoney(String nick)
    {
        long endTime = System.currentTimeMillis();
        long differenceTime = endTime - startTime;
        UserData.list.get(nick).getEconomy().setMoney(moneyIncrease * (differenceTime / needTime));
        startTime = System.currentTimeMillis();
    }
}
