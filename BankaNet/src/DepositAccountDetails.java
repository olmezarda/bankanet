public class DepositAccountDetails {
    private double balance;
    private double interestRate;
    private String password;

    public DepositAccountDetails(double balance, double interestRate, String password) {
        this.balance = balance;
        this.interestRate = interestRate;
        this.password = password;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public double getInterestRate() {
        return 5;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public double calculateInterest() {
        return balance * interestRate / 100;
    }

    public double calculateTotalAmount() {
        return balance + calculateInterest();
    }
}
