import java.util.Scanner;

public class InvestmentAccount {
    private Customer customer;
    private GoldAccount goldAccount;
    private CurrencyAccount currencyAccount;
    String space = "------------------------------------";

    public InvestmentAccount(Customer customer) {
        this.customer = customer;
        this.goldAccount = new GoldAccount(this);
        this.currencyAccount = new CurrencyAccount(this);
    }

    public void enterInvestmentAccount() {
        Scanner scanner = new Scanner(System.in);
        String investmentOptions = """
                !Geri dönmek için kullanıcı adına 0 girip ENTER a basınız.
                1- ALTIN HESABIM
                2- DÖVİZ HESABIM
                Yapmak istediğiniz işlemi seçiniz:\s""";
        System.out.println(space);
        System.out.print(investmentOptions);

        try {
            int choice = scanner.nextInt();
            switch (choice) {
                case 0:
                    customer.accountScreen();
                    break;
                case 1:
                    goldAccount.enterGoldAccount();
                    break;
                case 2:
                    currencyAccount.enterCurrencyAccount();
                    break;
                default:
                    System.out.println("Geçersiz seçenek! Lütfen 0 ile 2 arasında bir seçenek giriniz.");
                    enterInvestmentAccount();
                    break;
            }
        } catch (Exception e) {
            System.out.println("Geçersiz giriş! Lütfen geçerli bir sayı giriniz.");
            scanner.nextLine();
            enterInvestmentAccount();
        }
    }
}
