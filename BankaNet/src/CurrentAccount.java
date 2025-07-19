import java.util.InputMismatchException;
import java.util.Scanner;

public class CurrentAccount extends Accounts {
    private static final Scanner scanner = new Scanner(System.in);
    private Customer customer;
    String space = "------------------------------------";

    public CurrentAccount(Customer customer) {
        this.customer = customer;
    }

    public void enterCurrentAccount() {
        String enterMessage = """
                !Geri dönmek için hesap koduna 0 girip enter a basınız,
                Vadesiz hesabınıza,
                # Giriş yapmak için 1'e,
                # Hesabınız yoksa hesap oluşturmak için 2'ye,
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
                case 1 -> loginCurrentAccount();
                case 2 -> createCurrentAccount();
                case 3 -> updateAccountPassword();
                default -> {
                    System.out.println(exceptionMessage);
                    enterCurrentAccount();
                }
            }
        } catch (InputMismatchException e) {
            System.out.println(exceptionMessage);
            scanner.nextLine();
            enterCurrentAccount();
        }
    }

    public void updateAccountPassword() {
        System.out.println(space);
        System.out.print("Şifre değiştirmek için hesap kodunuzu girin: ");
        String accountCode = scanner.next();

        if (currentAccounts.containsKey(accountCode)) {
            CurrentAccountDetails accountDetails = currentAccounts.get(accountCode);

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
        enterCurrentAccount();
    }

    public void loginCurrentAccount() {
        System.out.println(space);
        System.out.println("!Geri dönmek için hesap koduna 0 girip ENTER a basınız");
        System.out.print("Hesap kodunuz: ");
        String accountCode = scanner.nextLine();

        if (accountCode.equals("0")) {
            enterCurrentAccount();
        }

        System.out.print("Şifreniz: ");
        String password = scanner.nextLine();

        if (currentAccounts.containsKey(accountCode) && currentAccounts.get(accountCode).getPassword().equals(password)) {
            CurrentAccountDetails account = currentAccounts.get(accountCode);
            showCurrentAccount(account);
        } else {
            System.out.println("Hesap kodunuz veya şifre hatalı.\n");
            loginCurrentAccount();
        }
    }

    public void createCurrentAccount() {
        System.out.println(space);
        System.out.print("!Geri dönmek için hesap koduna 0 girip ENTER a basınız\nHesap kodunuz: ");
        String accountCode = scanner.nextLine();

        if (accountCode.equals("0")) {
            enterCurrentAccount();
        } else if (currentAccounts.containsKey(accountCode)) {
            System.out.println("Bu hesap kodu zaten mevcut. Lütfen başka bir hesap kodu giriniz.");
            createCurrentAccount();
        } else {
            System.out.print("Şifreniz: ");
            String password = scanner.nextLine();
            CurrentAccountDetails newAccount = new CurrentAccountDetails(password, 0);
            currentAccounts.put(accountCode, newAccount);
            System.out.println("Hesap başarıyla oluşturuldu.");
            enterCurrentAccount();
        }
    }

    public void showCurrentAccount(CurrentAccountDetails account) {
        System.out.println(space);
        System.out.println("Bakiyeniz: " + account.getBalance() + "TL");
        System.out.println(space);
        System.out.print("""
                !Geri dönmek için hesap adına 0 girip ENTER'a
                Vadesiz Hesabınıza,
                Para yatırmak için 1'e,
                Para çekmek için 2'ye basınız.
                Yapmak istediğiniz işlem:\s""");

        try {
            int choice = scanner.nextInt();
            switch (choice) {
                case 1 -> depositMoney(account);
                case 2 -> withdrawMoney(account);
                case 0 -> customer.accountScreen();
                default -> {
                    System.out.println("Geçersiz seçim. Lütfen tekrar deneyiniz.");
                    showCurrentAccount(account);
                }
            }
        } catch (InputMismatchException e) {
            System.out.println("Hatalı giriş yaptınız. Lütfen tekrar deneyiniz.");
            scanner.nextLine();
            showCurrentAccount(account);
        }
    }

    public void depositMoney(CurrentAccountDetails account) {
        System.out.println(space);
        System.out.print("!Geri dönmek için tutar kısmına 0 girip ENTER'a basınız.\nYatırmak istediğiniz tutarı giriniz: ");
        try {
            double amount = scanner.nextDouble();
            if (amount == 0) {
                showCurrentAccount(account);
            } else if (amount > 0) {
                account.setBalance(account.getBalance() + amount);
                System.out.println("Başarıyla " + amount + " TL yatırıldı.");
                showCurrentAccount(account);
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

    public void withdrawMoney(CurrentAccountDetails account) {
        System.out.println(space);
        System.out.print("!Geri dönmek için tutar kısmına 0 girip ENTER'a basınız.\nÇekmek istediğiniz tutarı giriniz: ");
        try {
            double amount = scanner.nextDouble();
            if (amount == 0) {
                showCurrentAccount(account);
            } else if (amount > 0 && account.getBalance() >= amount) {
                account.setBalance(account.getBalance() - amount);
                System.out.println("Başarıyla " + amount + " TL çekildi.");
            } else if (amount > 0 && account.getBalance() < amount) {
                System.out.println("Bakiyeniz ile maksimim " + account.getBalance() + " TL çekebilirsiniz.");
                showCurrentAccount(account);
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
}
