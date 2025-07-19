import java.util.InputMismatchException;
import java.util.Scanner;

public class DepositAccount extends Accounts {
    private static final Scanner scanner = new Scanner(System.in);
    private Customer customer;
    String space = "------------------------------------";

    public DepositAccount(Customer customer) {
        this.customer = customer;
    }

    public void enterDepositAccount() {
        String enterMessage = """
                !Geri dönmek için hesap koduna 0 girip enter a basınız,
                Vadeli hesabınıza,
                # Giriş yapmak için 1'e,
                # Hesabınız yoksa %5 faiz oranıyla hesap oluşturmak için 2'ye,
                # Hesabınızın şifresini yenilemek için 3'e basınız.
                Yapmak istediğiniz işlemi seçiniz:\s""";
        String exceptionMessage = "Geçersiz bir değer girdiniz. Lütfen 0, 1, 2 ya da 3'ü seçin.";
        System.out.println(space);
        System.out.print(enterMessage);
        try {
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 0 -> customer.accountScreen();
                case 1 -> loginDepositAccount();
                case 2 -> createDepositAccount();
                case 3 -> updateAccountPassword();
                default -> {
                    System.out.println(exceptionMessage);
                    enterDepositAccount();
                }
            }
        } catch (Exception e) {
            System.out.println(exceptionMessage);
            scanner.nextLine();
            enterDepositAccount();
        }
    }

    public void updateAccountPassword() {
        System.out.println(space);
        System.out.print("Şifre değiştirmek için hesap kodunuzu girin: ");
        String accountCode = scanner.next();

        if (depositAccounts.containsKey(accountCode)) {
            DepositAccountDetails accountDetails = depositAccounts.get(accountCode);

            System.out.print("Mevcut şifrenizi girin: ");
            String currentPassword = scanner.next();

            if (accountDetails.getPassword().equals(currentPassword)) {
                String newPassword;
                while (true) {
                    System.out.print("Yeni şifrenizi girin: ");
                    newPassword = scanner.next();
                    if (newPassword.isEmpty()) {
                        System.out.println("Şifre boş bırakılamaz. Lütfen geçerli bir şifre giriniz.");
                    } else {
                        break;
                    }
                }

                accountDetails.setPassword(newPassword);
                System.out.println("Şifreniz başarıyla güncellendi!");
            } else {
                System.out.println("Geçersiz şifre! Şifreyi tekrar kontrol edin.");
            }
        } else {
            System.out.println("Hesap kodu bulunamadı.");
        }
        enterDepositAccount();
    }

    public void loginDepositAccount() {
        System.out.println(space);
        System.out.println("!Geri dönmek için hesap koduna 0 girip ENTER a basınız");
        System.out.print("Hesap kodunuz: ");
        String accountCode = scanner.nextLine();

        if (accountCode.equals("0")) {
            enterDepositAccount();
        }

        System.out.print("Şifreniz: ");
        String password = scanner.nextLine();

        DepositAccountDetails account = depositAccounts.get(accountCode);

        if (account != null && account.getPassword().equals(password)) {
            showDepositAccount(account);
        } else {
            System.out.println("Hesap kodunuz veya şifre hatalı.");
            loginDepositAccount();
        }
    }

    public void createDepositAccount() {
        String accountCode;

        while (true) {
            System.out.println(space);
            System.out.print("!Geri dönmek için hesap koduna 0 girip ENTER a basınız\n" +
                    "Hesap kodunuz: ");
            accountCode = scanner.nextLine();
            if (accountCode.equals("0")) {
                enterDepositAccount();
            } else if (accountCode.isEmpty()) {
                System.out.println("Hesap kodu boş bırakılamaz. Lütfen geçerli bir hesap kodu girin.");
            } else if (depositAccounts.containsKey(accountCode)) {
                System.out.println("Bu hesap kodu zaten mevcut. Lütfen başka bir hesap kodu giriniz.");
            } else {
                break;
            }
        }

        String password;
        while (true) {
            System.out.print("Şifreniz: ");
            password = scanner.nextLine();
            if (password.isEmpty()) {
                System.out.println("Şifre boş bırakılamaz. Lütfen geçerli bir şifre girin.");
            } else {
                System.out.println("Hesap başarıyla oluşturuldu.");
                break;
            }
        }

        DepositAccountDetails details = new DepositAccountDetails(0, 5, password);
        depositAccounts.put(accountCode, details);
        enterDepositAccount();
    }

    public void showDepositAccount(DepositAccountDetails account) {
        System.out.println(space);
        System.out.println("Bakiyeniz: " + account.getBalance() + " TL" +
                "\nFaiz oranınız: %" + account.getInterestRate() +
                "\n1 yıl sonunda alacağınız faiz: " + account.calculateInterest() + " TL" +
                "\n1 yıl sonunda faizle birlikte toplam paranız: " + account.calculateTotalAmount() + " TL"
        );
        System.out.println(space);
        System.out.print("""
                !Geri dönmek için hesap adına 0 girip ENTER'a,
                Vadeli Hesabınıza,
                Para yatırmak için 1'e,
                Para çekmek için 2'ye,
                Paranızın faiz hesabını yapmak için 3'e basınız.
                Yapmak istediğiniz işlem:\s""");

        try {
            int choice = scanner.nextInt();

            switch (choice) {
                case 0 -> customer.accountScreen();
                case 1 -> depositMoney(account);
                case 2 -> withdrawMoney(account);
                case 3 -> calculateInterest(account);
                default -> {
                    System.out.println("Geçersiz seçim. Lütfen 0, 1, 2 ya da 3 giriniz.");
                    showDepositAccount(account);
                }
            }
        } catch (InputMismatchException e) {
            System.out.println("Hatalı giriş yaptınız. Lütfen 1, 2 ya da 0 giriniz.");
            scanner.nextLine();
            showDepositAccount(account);
        }
    }

    public void depositMoney(DepositAccountDetails account) {
        System.out.println(space);
        System.out.print("!Geri dönmek için tutar kısmına 0 girip ENTER'a basınız.\nYatırmak istediğiniz tutarı giriniz: ");
        try {
            double amount = scanner.nextDouble();
            if (amount == 0) {
                showDepositAccount(account);
            } else if (amount > 0) {
                account.setBalance(account.getBalance() + amount);
                System.out.println("Başarıyla " + amount + " TL yatırıldı.");
                showDepositAccount(account);
            } else {
                System.out.println("Geçersiz tutar. Lütfen pozitif bir değer giriniz.");
                depositMoney(account);
            }
        } catch (InputMismatchException e) {
            System.out.println("Hatalı giriş yaptınız. Lütfen sayısal bir değer giriniz.");
            scanner.nextLine();
            depositMoney(account);
        }
    }

    public void withdrawMoney(DepositAccountDetails account) {
        System.out.println(space);
        System.out.print("!Geri dönmek için tutar kısmına 0 girip ENTER'a basınız.\nÇekmek istediğiniz tutarı giriniz: ");
        try {
            double amount = scanner.nextDouble();
            if (amount == 0) {
                showDepositAccount(account);
            } else if (amount > 0 && account.getBalance() >= amount) {
                account.setBalance(account.getBalance() - amount);
                System.out.println("Başarıyla " + amount + " TL çekildi.");
                showDepositAccount(account);
            } else if (amount > 0 && account.getBalance() < amount) {
                System.out.println("Bakiyeniz ile maksimim " + account.getBalance() + " TL çekebilirsiniz.");
                showDepositAccount(account);
            } else {
                System.out.println("Geçersiz işlem. Lütfen geçerli bir tutar giriniz.");
                withdrawMoney(account);
            }
        } catch (InputMismatchException e) {
            System.out.println("Hatalı giriş yaptınız. Lütfen sayısal bir değer giriniz.");
            scanner.nextLine();
            withdrawMoney(account);
        }
    }

    public void calculateInterest(DepositAccountDetails account) {
        System.out.println(space);
        System.out.print("""
                !Geri dönmek için tutar kısmına 0 girip ENTER a basınız.,
                Hesaplamak istediğiniz tutarı giriniz:\s""");
        try {
            double amount = scanner.nextDouble();
            if (amount == 0) {
                showDepositAccount(account);
            } else if (amount > 0) {
                double yield = amount * 5 / 100;
                System.out.println(space);
                System.out.println("Faizin getireceği tutar: " + yield + " TL.");
                System.out.println("Tekrar faiz getirisi hesaplamak için -1,\nGeri dönmek için 0 girip ENTER'a basınız.");
                int choice = scanner.nextInt();
                switch (choice) {
                    case -1 -> calculateInterest(account);
                    case 0 -> showDepositAccount(account);
                    default -> {
                        System.out.println("Geçersiz seçim. Lütfen -1 veya 0 giriniz.");
                        calculateInterest(account);
                    }
                }
            } else {
                System.out.println("Geçersiz tutar. Lütfen pozitif bir değer giriniz.");
                calculateInterest(account);
            }
        } catch (InputMismatchException e) {
            System.out.println("Hatalı giriş yaptınız. Lütfen sayısal bir değer giriniz.");
            scanner.nextLine();
            calculateInterest(account);
        }
    }
}
