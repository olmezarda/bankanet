import java.util.Scanner;

public class CurrencyAccount extends Accounts {
    private static final Scanner scanner = new Scanner(System.in);
    private InvestmentAccount investmentAccount;

    String space = "------------------------------------";

    public CurrencyAccount(InvestmentAccount investmentAccount) {
        this.investmentAccount = investmentAccount;
    }

    public void enterCurrencyAccount() {
        String enterMessage = """
                !Geri dönmek için hesap koduna 0 girip enter a basınız,
                Döviz hesabınıza,
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
                case 0 -> investmentAccount.enterInvestmentAccount();
                case 1 -> loginCurrencyAccount();
                case 2 -> createCurrencyAccount();
                case 3 -> updateAccountPassword();
                default -> {
                    System.out.println(exceptionMessage);
                    enterCurrencyAccount();
                }
            }
        } catch (Exception e) {
            System.out.println(exceptionMessage);
            scanner.nextLine();
            enterCurrencyAccount();
        }
    }

    public void updateAccountPassword() {
        System.out.println(space);
        System.out.print("Şifre değiştirmek için hesap kodunuzu girin: ");
        String accountCode = scanner.next();

        if (currencyAccounts.containsKey(accountCode)) {
            CurrencyAccountDetails accountDetails = currencyAccounts.get(accountCode);

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
        enterCurrencyAccount();
    }

    public void loginCurrencyAccount() {
        System.out.println(space);
        System.out.println("!Geri dönmek için hesap koduna 0 girip ENTER a basın.");
        System.out.print("Hesap kodunu giriniz: ");
        String accountCode = scanner.nextLine();

        if (accountCode.equals("0")) {
            enterCurrencyAccount();
            return;
        }

        System.out.println("Geri dönmek için 0'a basın.");
        System.out.print("Şifrenizi giriniz: ");
        String password = scanner.nextLine();

        if (password.equals("0")) {
            enterCurrencyAccount();
            return;
        }

        if (Accounts.currencyAccounts.containsKey(accountCode) &&
                Accounts.currencyAccounts.get(accountCode).getPassword().equals(password)) {
            System.out.println("Başarılı bir şekilde giriş yapıldı.");
            showCurrencyAccount(Accounts.currencyAccounts.get(accountCode));
        } else {
            System.out.println("Hatalı hesap kodu veya şifre. Tekrar deneyiniz.");
            loginCurrencyAccount();
        }
    }

    public void createCurrencyAccount() {
        System.out.println(space);
        System.out.println("!Geri dönmek için hesap koduna 0 girip ENTER a basın.");
        System.out.print("Hesap kodu oluşturun: ");
        String accountCode = scanner.nextLine();

        if (accountCode.equals("0")) {
            enterCurrencyAccount();
            return;
        }

        if (Accounts.currencyAccounts.containsKey(accountCode)) {
            System.out.println("Bu hesap kodu zaten mevcut. Lütfen başka bir kod deneyin.");
            createCurrencyAccount();
        } else {
            System.out.println("Geri dönmek için 0'a basın.");
            System.out.print("Şifrenizi oluşturun: ");
            String password = scanner.nextLine();

            if (password.equals("0")) {
                enterCurrencyAccount();
                return;
            }

            Accounts.currencyAccounts.put(accountCode, new CurrencyAccountDetails(password, 0));
            System.out.println("Hesabınız başarıyla oluşturuldu. Şimdi giriş yapabilirsiniz.");
            loginCurrencyAccount();
        }
    }

    private void showCurrencyAccount(CurrencyAccountDetails currencyAccount) {
        String account = "EURO/TL = 40,00€" +
                "Sahip olunan Euro: " + currencyAccount.getEuroBalance() + " €";
        String options = """
                !Geri dönmek için 0'a basınız.
                Döviz almak için 1'e, satmak için 2'ye basınız.
                Yapmak istediğiniz işlem:\s""";
        System.out.println(space);
        System.out.println(account);
        System.out.println(space);
        System.out.print(options);

        try {
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 0 -> enterCurrencyAccount();
                case 1 -> buyCurrency(currencyAccount);
                case 2 -> sellCurrency(currencyAccount);
                default -> {
                    System.out.println("Geçersiz seçenek. Lütfen 0, 1 ya da 2'yi seçin.");
                    showCurrencyAccount(currencyAccount);
                }
            }
        } catch (Exception e) {
            System.out.println("Geçersiz giriş. Lütfen tekrar deneyiniz.");
            scanner.nextLine();
            showCurrencyAccount(currencyAccount);
        }
    }

    private void buyCurrency(CurrencyAccountDetails currencyAccount) {
        final double EURO_PRICE = 40.0;
        System.out.println(space);
        System.out.println("Geri dönmek için 0'a basın.");
        System.out.print("Kaç Euro almak istiyorsunuz? ");

        try {
            double eurosToBuy = scanner.nextDouble();
            scanner.nextLine();

            if (eurosToBuy == 0) {
                showCurrencyAccount(currencyAccount);
                return;
            }

            if (eurosToBuy <= 0) {
                System.out.println("Geçersiz miktar! Lütfen pozitif bir değer giriniz.");
                buyCurrency(currencyAccount);
                return;
            }

            double totalCost = eurosToBuy * EURO_PRICE;

            System.out.println(space);
            System.out.println("0 - Geri Dön");
            System.out.println("1 - Kredili Hesap");
            System.out.println("2 - Vadeli Hesap");
            System.out.println("3 - Vadesiz Hesap");
            System.out.print("Hangi hesabınızla ödeme yapmak istersiniz? ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            if (choice == 0) {
                showCurrencyAccount(currencyAccount);
                return;
            }

            boolean paymentSuccess = switch (choice) {
                case 1 -> processPaymentFromAccount("Kredili", totalCost);
                case 2 -> processPaymentFromAccount("Vadeli", totalCost);
                case 3 -> processPaymentFromAccount("Vadesiz", totalCost);
                default -> {
                    System.out.println("Geçersiz seçenek. Lütfen tekrar deneyiniz.");
                    buyCurrency(currencyAccount);
                    yield false;
                }
            };

            if (paymentSuccess) {
                currencyAccount.setEuroBalance(currencyAccount.getEuroBalance() + eurosToBuy);
                System.out.println("Başarılı bir şekilde " + eurosToBuy + " Euro satın alındı.");
            } else {
                System.out.println("Döviz satın alınamıyor. İşlem başarısız.");
            }

            showCurrencyAccount(currencyAccount);
        } catch (Exception e) {
            System.out.println("Bir hata oluştu, işlemi tekrar deneyiniz.");
            scanner.nextLine();
            buyCurrency(currencyAccount);
        }
    }

    private void sellCurrency(CurrencyAccountDetails currencyAccount) {
        final double EURO_PRICE = 40.0;
        System.out.println(space);
        System.out.println("Geri dönmek için 0'a basın.");
        System.out.print("Kaç Euro satmak istiyorsunuz? ");

        try {
            double eurosToSell = scanner.nextDouble();
            scanner.nextLine();

            if (eurosToSell == 0) {
                showCurrencyAccount(currencyAccount);
                return;
            }

            if (eurosToSell <= 0 || eurosToSell > currencyAccount.getEuroBalance()) {
                System.out.println("Geçersiz miktar! Lütfen sahip olduğunuz döviz miktarı kadar bir değer giriniz.");
                sellCurrency(currencyAccount);
                return;
            }

            double totalEarnings = eurosToSell * EURO_PRICE;

            System.out.println(space);
            System.out.println("0 - Geri Dön");
            System.out.println("1 - Kredili Hesap");
            System.out.println("2 - Vadeli Hesap");
            System.out.println("3 - Vadesiz Hesap");
            System.out.print("Hangi hesabınıza para aktarmak istersiniz? ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            if (choice == 0) {
                showCurrencyAccount(currencyAccount);
                return;
            }

            boolean transferSuccess = switch (choice) {
                case 1 -> processTransferToAccount("Kredili", totalEarnings);
                case 2 -> processTransferToAccount("Vadeli", totalEarnings);
                case 3 -> processTransferToAccount("Vadesiz", totalEarnings);
                default -> {
                    System.out.println("Geçersiz seçenek. Lütfen tekrar deneyiniz.");
                    sellCurrency(currencyAccount);
                    yield false;
                }
            };

            if (transferSuccess) {
                currencyAccount.setEuroBalance(currencyAccount.getEuroBalance() - eurosToSell);
                System.out.println("Başarılı bir şekilde " + eurosToSell + " Euro satıldı.");
            } else {
                System.out.println("Para aktarımı başarısız.");
            }

            showCurrencyAccount(currencyAccount);
        } catch (Exception e) {
            System.out.println("Geçersiz giriş. Lütfen tekrar deneyiniz.");
            scanner.nextLine();
            sellCurrency(currencyAccount);
        }
    }

    private boolean processPaymentFromAccount(String accountType, double amount) {
        System.out.println(space);
        System.out.println("Geri dönmek için 0'a basın.");
        System.out.print(accountType + " hesabınızın hesap kodunu giriniz: ");
        String accountCode = scanner.nextLine();

        if (accountCode.equals("0")) {
            return false;
        }

        boolean accountExists = false;
        switch (accountType) {
            case "Kredili" -> accountExists = Accounts.creditAccounts.containsKey(accountCode);
            case "Vadeli" -> accountExists = Accounts.depositAccounts.containsKey(accountCode);
            case "Vadesiz" -> accountExists = Accounts.currentAccounts.containsKey(accountCode);
        }

        if (!accountExists) {
            System.out.println("Hesap bulunamadı.");
            return false;
        }

        System.out.print("Şifrenizi giriniz: ");
        String password = scanner.nextLine();

        switch (accountType) {
            case "Kredili" -> {
                CreditAccountDetails account = Accounts.creditAccounts.get(accountCode);
                if (account.getPassword().equals(password)) {
                    if (account.getBalance() >= amount) {
                        account.setBalance(account.getBalance() - amount);
                        System.out.println("Ödeme başarıyla gerçekleştirildi.");
                        return true;
                    } else {
                        System.out.println("Yetersiz bakiye.");
                    }
                } else {
                    System.out.println("Hatalı hesap kodu veya şifre.");
                }
            }
            case "Vadeli" -> {
                DepositAccountDetails account = Accounts.depositAccounts.get(accountCode);
                if (account.getPassword().equals(password)) {
                    if (account.getBalance() >= amount) {
                        account.setBalance(account.getBalance() - amount);
                        System.out.println("Ödeme başarıyla gerçekleştirildi.");
                        return true;
                    } else {
                        System.out.println("Yetersiz bakiye.");
                    }
                } else {
                    System.out.println("Hatalı hesap kodu veya şifre.");
                }
            }
            case "Vadesiz" -> {
                CurrentAccountDetails account = Accounts.currentAccounts.get(accountCode);
                if (account.getPassword().equals(password)) {
                    if (account.getBalance() >= amount) {
                        account.setBalance(account.getBalance() - amount);
                        System.out.println("Ödeme başarıyla gerçekleştirildi.");
                        return true;
                    } else {
                        System.out.println("Yetersiz bakiye.");
                    }
                } else {
                    System.out.println("Hatalı hesap kodu veya şifre.");
                }
            }
        }
        return false;
    }

    private boolean processTransferToAccount(String accountType, double amount) {
        System.out.println(space);
        System.out.println("Geri dönmek için 0'a basın.");
        System.out.print(accountType + " hesabınızın hesap kodunu giriniz: ");
        String accountCode = scanner.nextLine();

        if (accountCode.equals("0")) {
            return false;
        }

        boolean accountExists = false;
        switch (accountType) {
            case "Kredili" -> accountExists = Accounts.creditAccounts.containsKey(accountCode);
            case "Vadeli" -> accountExists = Accounts.depositAccounts.containsKey(accountCode);
            case "Vadesiz" -> accountExists = Accounts.currentAccounts.containsKey(accountCode);
        }

        if (!accountExists) {
            System.out.println("Hesap bulunamadı.");
            return false;
        }

        switch (accountType) {
            case "Kredili" -> {
                CreditAccountDetails account = Accounts.creditAccounts.get(accountCode);
                account.setBalance(account.getBalance() + amount);
                System.out.println("Para başarıyla aktarıldı.");
                return true;
            }
            case "Vadeli" -> {
                DepositAccountDetails account = Accounts.depositAccounts.get(accountCode);
                account.setBalance(account.getBalance() + amount);
                System.out.println("Para başarıyla aktarıldı.");
                return true;
            }
            case "Vadesiz" -> {
                CurrentAccountDetails account = Accounts.currentAccounts.get(accountCode);
                account.setBalance(account.getBalance() + amount);
                System.out.println("Para başarıyla aktarıldı.");
                return true;
            }
        }
        return false;
    }
}
