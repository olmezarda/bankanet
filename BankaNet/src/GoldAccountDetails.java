public class GoldAccountDetails {
    private String password;
    private double goldBalance;

    public GoldAccountDetails(String password, double goldBalance) {
        this.password = password;
        this.goldBalance = goldBalance;
    }

    public String getPassword() {
        return password;
    }

    public double getGoldBalance() {
        return goldBalance;
    }

    public void setGoldBalance(double goldBalance) {
        this.goldBalance = goldBalance;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
