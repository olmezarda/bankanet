import java.util.InputMismatchException;
import java.util.Scanner;

public class CreditAccount extends Accounts {
    private static final Scanner scanner = new Scanner(System.in);
    private Customer customer;
    String space = "------------------------------------";

    public CreditAccount(Customer customer) {
        this.customer = customer;
    }

    public void enterCreditAccount() {
        String enterMessage = """
                !Geri dönmek için hesap koduna 0 girip enter a basınız,
                Kredili hesabınıza,
                # Giriş yapmak için 1'e,
                # Hesabınız yoksa 10 000TL kredi limitli hesap oluşturmak için 2'ye,
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
                case 1 -> loginCreditAccount();
                case 2 -> createCreditAccount();
                case 3 -> updateAccountPassword();
                default -> {
                    System.out.println(exceptionMessage);
                    enterCreditAccount();
                }
            }
        } catch (InputMismatchException e) {
            System.out.println(exceptionMessage);
            scanner.nextLine();
            enterCreditAccount();
        }
    }

    public void updateAccountPassword() {
        System.out.println(space);
        System.out.print("Şifre değiştirmek için hesap kodunuzu girin: ");
        String accountCode = scanner.next();

        if (creditAccounts.containsKey(accountCode)) {
            CreditAccountDetails accountDetails = creditAccounts.get(accountCode);

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
        enterCreditAccount();
    }

    public void loginCreditAccount() {
        System.out.println(space);
        System.out.println("!Geri dönmek için hesap koduna 0 girip ENTER a basınız");
        System.out.print("Hesap kodunuz: ");
        String accountCode = scanner.nextLine();

        if (accountCode.equals("0")) {
            enterCreditAccount();
        }

        System.out.print("Şifreniz: ");
        String password = scanner.nextLine();

        CreditAccountDetails account = creditAccounts.get(accountCode);

        if (account != null && account.getPassword().equals(password)) {
            showCreditAccount(account);
        } else {
            System.out.println("Hesap kodunuz veya şifre hatalı.");
            loginCreditAccount();
        }
    }

    public void createCreditAccount() {
        System.out.println(space);
        System.out.print("!Geri dönmek için hesap koduna 0 girip ENTER a basınız\nHesap kodunuz: ");
        String accountCode = scanner.nextLine();

        if (accountCode.equals("0")) {
            enterCreditAccount();
        } else if (creditAccounts.containsKey(accountCode)) {
            System.out.println("Bu hesap kodu zaten mevcut. Lütfen başka bir hesap kodu giriniz.");
            createCreditAccount();
        } else {
            System.out.print("Şifreniz: ");
            String password = scanner.nextLine();
            CreditAccountDetails newAccount = new CreditAccountDetails(0, 10000, password);
            creditAccounts.put(accountCode, newAccount);
            System.out.println("Hesap başarıyla oluşturuldu.");
            enterCreditAccount();
        }
    }

    public void showCreditAccount(CreditAccountDetails account) {
        System.out.println(space);
        System.out.println("Bakiyeniz: " + account.getBalance() + " TL");
        System.out.println("Kredi Limitiniz: " + account.getCreditLimit() + " TL");
        System.out.println(space);
        System.out.print("""
                !Geri dönmek için hesap adına 0 girip ENTER'a
                Kredili Hesabınıza,
                Para yatırmak için 1'e,
                Para çekmek için 2'ye basınız.
                Yapmak istediğiniz işlem:\s""");

        try {
            int choice = scanner.nextInt();
            switch (choice) {
                case 0 -> customer.accountScreen();
                case 1 -> depositMoney(account);
                case 2 -> withdrawMoney(account);
                default -> {
                    System.out.println("Geçersiz seçim. Lütfen 1, 2 ya da 0 giriniz.");
                    showCreditAccount(account);
                }
            }
        } catch (InputMismatchException e) {
            System.out.println("Hatalı giriş yaptınız. Lütfen 1, 2 ya da 0 giriniz.");
            scanner.nextLine();
            showCreditAccount(account);
        }
    }

    public void depositMoney(CreditAccountDetails account) {
        System.out.println(space);
        System.out.print("!Geri dönmek için tutar kısmına 0 girip ENTER'a basınız.\nYatırmak istediğiniz tutarı giriniz: ");
        try {
            double amount = scanner.nextDouble();
            if (amount == 0) {
                showCreditAccount(account);
            } else if (amount > 0) {
                account.setBalance(account.getBalance() + amount);
                System.out.println("Başarıyla " + amount + " TL yatırıldı.");
                showCreditAccount(account);
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

    public void withdrawMoney(CreditAccountDetails account) {
        System.out.println(space);
        System.out.print("""
                !Geri dönmek için tutar kısmına 0,
                Kredi çekmek için tutar kısmına -1 girip ENTER'a basınız.
                Çekmek istediğiniz tutarı giriniz:\s""");
        try {
            double amount = scanner.nextDouble();
            if (amount == 0) {
                showCreditAccount(account);
            } else if (amount == -1) {
                withdrawCreditLimit(account);
            } else if (amount > 0 && account.getBalance() >= amount) {
                account.setBalance(account.getBalance() - amount);
                System.out.println("Başarıyla " + amount + " TL çekildi.");
                showCreditAccount(account);
            } else if (amount > 0 && account.getBalance() < amount) {
                System.out.println("Bakiyeniz ile maksimim " + account.getBalance() + " TL çekebilirsiniz.");
                showCreditAccount(account);
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

    public void withdrawCreditLimit(CreditAccountDetails account) {
        System.out.println(space);
        System.out.println("!Geri dönmek için tutar kısmına 0 giriniz.\nNe kadar kredi çekmek istersiniz (maksimum " + account.getCreditLimit() + " TL kredi çekebilirsiniz)?");

        try {
            double credit = scanner.nextDouble();
            if (credit == 0) {
                withdrawMoney(account);
            } else if (credit > 0 && credit <= account.getCreditLimit()) {
                account.setCreditLimit(account.getCreditLimit() - credit);
                account.setBalance(account.getBalance() + credit);
                System.out.println(credit + " TL kredi bakiyeye aktarıldı.");
                showCreditAccount(account);
            } else {
                System.out.println("Geçersiz işlem. Kredi limitinizden fazla bir tutar çekemezsiniz.");
                withdrawCreditLimit(account);
            }
        } catch (InputMismatchException e) {
            System.out.println("Hatalı giriş yaptınız. Lütfen geçerli bir sayısal değer giriniz.");
            scanner.nextLine();
            withdrawCreditLimit(account);
        }
    }
}
