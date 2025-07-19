public class CurrencyAccountDetails {
    private String password;
    private double euroBalance;

    public CurrencyAccountDetails(String password, double euroBalance) {
        this.password = password;
        this.euroBalance = euroBalance;
    }

    public String getPassword() {
        return password;
    }

    public double getEuroBalance() {
        return euroBalance;
    }

    public void setEuroBalance(double euroBalance) {
        this.euroBalance = euroBalance;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
