public class CurrentAccountDetails {
    private String password;
    private double balance;

    public CurrentAccountDetails(String password, double balance) {
        this.password = password;
        this.balance = balance;
    }

    public String getPassword() {
        return password;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
