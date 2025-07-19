public class CreditAccountDetails {
    private double balance;
    private double creditLimit;
    private String password;

    public CreditAccountDetails(double balance, double creditLimit, String password) {
        this.balance = balance;
        this.creditLimit = creditLimit;
        this.password = password;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public double getCreditLimit() {
        return creditLimit;
    }

    public void setCreditLimit(double creditLimit) {
        this.creditLimit = creditLimit;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
