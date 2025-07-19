import java.util.Scanner;

public class Customer extends User {
    private DepositAccount depositAccount;
    private CreditAccount creditAccount;
    private CurrentAccount currentAccount;
    private InvestmentAccount investmentAccount;
    private Campaign campaign;
    private Contact contact;
    private Support support;
    private Feedback feedback;
    private CustomerSettings customerSettings;

    String space = "------------------------------------";

    public Customer(String username, String password) {
        super(username, password);
        this.customerSettings = new CustomerSettings(this);
        this.depositAccount = new DepositAccount(this);
        this.creditAccount = new CreditAccount(this);
        this.currentAccount = new CurrentAccount(this);
        this.investmentAccount = new InvestmentAccount(this);
        this.campaign = new Campaign(this);
        this.contact = new Contact(this);
        this.support = new Support(this);
        this.feedback = new Feedback(this);
    }

    public void displayScreen() {
        String options = """
                1- AYARLAR
                2- HESAPLARIM
                3- KAMPANYALAR
                4- İLETİŞİM
                5- DESTEK
                6- GERİ BİLDİRİM
                0- ÇIKIŞ
                Yapmak istediğiniz işlemi seçiniz:\s""";

        System.out.println(space);
        System.out.print(options);
        Scanner scanner = new Scanner(System.in);

        try {
            int choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    customerSettings.displaySettings();
                    break;
                case 2:
                    accountScreen();
                    break;
                case 3:
                    campaign.showCurrentCampaigns();
                    break;
                case 4:
                    System.out.println(space);
                    contact.contactInfo();
                    break;
                case 5:
                    support.help();
                    break;
                case 6:
                    System.out.println(space);
                    feedback.sendMessage();
                    break;
                case 0:
                    System.out.println(space);
                    System.out.println("Uygulamadan çıkılıyor...");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Geçersiz seçenek! Lütfen 0 ile 6 arasında bir seçenek giriniz.");
                    displayScreen();
                    break;
            }
        } catch (Exception e) {
            System.out.println("Geçersiz giriş! Lütfen geçerli bir sayı giriniz.");
            scanner.nextLine();
            displayScreen();
        }
    }

    public void accountScreen() {
        Scanner scanner = new Scanner(System.in);
        String accountOptions = """
                !Geri dönmek için kullanıcı adına 0 girip ENTER a basınız.
                1- VADELİ HESABIM
                2- KREDİLİ HESABIM
                3- VADESİZ HESABIM
                4- YATIRIM HESABIM
                Yapmak istediğiniz işlemi seçiniz:\s""";

        System.out.println(space);
        System.out.print(accountOptions);
        try {
            int choice = scanner.nextInt();
            switch (choice) {
                case 0:
                    displayScreen();
                    break;
                case 1:
                    depositAccount.enterDepositAccount();
                    break;
                case 2:
                    creditAccount.enterCreditAccount();
                    break;
                case 3:
                    currentAccount.enterCurrentAccount();
                    break;
                case 4:
                    investmentAccount.enterInvestmentAccount();
                    break;
                default:
                    System.out.println(space);
                    System.out.println("Geçersiz seçenek! Lütfen 0 ile 3 arasında bir seçenek giriniz.");
                    accountScreen();
                    break;
            }
        } catch (Exception e) {
            System.out.println("Geçersiz giriş! Lütfen geçerli bir sayı giriniz.");
            scanner.nextLine();
            accountScreen();
        }
    }

    public void turnBack(Scanner scanner) {
        System.out.println("!Geri dönmek için 0 girip ENTER a basınız.");

        try {
            int back = scanner.nextInt();
            if (back == 0) {
                displayScreen();
            } else {
                System.out.println("Ekranda kalınıyor");
                turnBack(scanner);
            }
        } catch (Exception e) {
            System.out.println("Geçersiz bir giriş yaptınız. Lütfen bir sayı girin.");
            scanner.nextLine();
            turnBack(scanner);
        }
    }

}
