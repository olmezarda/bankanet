import java.util.Scanner;

public class GoldAccount extends Accounts {
    private static final Scanner scanner = new Scanner(System.in);
    private InvestmentAccount investmentAccount;

    String space = "------------------------------------";

    public GoldAccount(InvestmentAccount investmentAccount) {
        this.investmentAccount = investmentAccount;
    }

    public void enterGoldAccount() {
        String enterMessage = """
                !Geri dönmek için hesap koduna 0 girip enter a basınız,
                Altın hesabınıza,
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
                case 1 -> loginGoldAccount();
                case 2 -> createGoldAccount();
                case 3 -> updateAccountPassword();
                default -> {
                    System.out.println(exceptionMessage);
                    enterGoldAccount();
                }
            }
        } catch (Exception e) {
            System.out.println(exceptionMessage);
            scanner.nextLine();
            enterGoldAccount();
        }
    }

    public void updateAccountPassword() {
        System.out.println(space);
        System.out.print("Şifre değiştirmek için hesap kodunuzu girin: ");
        String accountCode = scanner.next();

        if (goldAccounts.containsKey(accountCode)) {
            GoldAccountDetails accountDetails = goldAccounts.get(accountCode);

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
        enterGoldAccount();
    }

    public void loginGoldAccount() {
        System.out.println(space);
        System.out.println("!Geri dönmek için hesap koduna 0 girip ENTER a basın.");
        System.out.print("Hesap kodunu giriniz: ");
        String accountCode = scanner.nextLine();

        if (accountCode.equals("0")) {
            enterGoldAccount();
            return;
        }

        System.out.println("Geri dönmek için 0'a basın.");
        System.out.print("Şifrenizi giriniz: ");
        String password = scanner.nextLine();

        if (password.equals("0")) {
            enterGoldAccount();
            return;
        }

        if (Accounts.goldAccounts.containsKey(accountCode) &&
                Accounts.goldAccounts.get(accountCode).getPassword().equals(password)) {
            System.out.println("Başarılı bir şekilde giriş yapıldı.");
            showGoldAccount(Accounts.goldAccounts.get(accountCode));
        } else {
            System.out.println("Hatalı hesap kodu veya şifre. Tekrar deneyiniz.");
            loginGoldAccount();
        }
    }

    public void createGoldAccount() {
        System.out.println(space);
        System.out.println("!Geri dönmek için hesap koduna 0 girip ENTER a basın.");
        System.out.print("Hesap kodu oluşturun: ");
        String accountCode = scanner.nextLine();

        if (accountCode.equals("0")) {
            enterGoldAccount();
            return;
        }

        if (Accounts.goldAccounts.containsKey(accountCode)) {
            System.out.println("Bu hesap kodu zaten mevcut. Lütfen başka bir kod deneyin.");
            createGoldAccount();
        } else {
            System.out.println("Geri dönmek için 0'a basın.");
            System.out.print("Şifrenizi oluşturun: ");
            String password = scanner.nextLine();

            if (password.equals("0")) {
                enterGoldAccount();
                return;
            }

            Accounts.goldAccounts.put(accountCode, new GoldAccountDetails(password, 0));
            System.out.println("Hesabınız başarıyla oluşturuldu. Şimdi giriş yapabilirsiniz.");
            loginGoldAccount();
        }
    }

    private void showGoldAccount(GoldAccountDetails goldAccount) {
        String options = """
                ALTIN 1gr = 3000,00TL
                Sahip olunan altın: %s gram
                !Geri dönmek için 0'a basınız.
                Altın almak için 1'e, satmak için 2'ye basınız.
                Yapmak istediğiniz işlem:\s""".formatted(goldAccount.getGoldBalance());
        System.out.println(space);
        System.out.print(options);

        try {
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 0 -> enterGoldAccount();
                case 1 -> buyGold(goldAccount);
                case 2 -> sellGold(goldAccount);
                default -> {
                    System.out.println("Geçersiz seçenek. Lütfen 0, 1 ya da 2'yi seçin.");
                    showGoldAccount(goldAccount);
                }
            }
        } catch (Exception e) {
            System.out.println("Geçersiz giriş. Lütfen geçerli bir sayı giriniz.");
            scanner.nextLine();
            showGoldAccount(goldAccount);
        }
    }

    private void buyGold(GoldAccountDetails goldAccount) {
        final double GOLD_PRICE_PER_GRAM = 3000.0;
        System.out.println(space);
        System.out.println("Geri dönmek için 0'a basın.");
        System.out.print("Kaç gram altın almak istiyorsunuz? ");

        try {
            double gramsToBuy = scanner.nextDouble();
            scanner.nextLine();

            if (gramsToBuy == 0) {
                showGoldAccount(goldAccount);
                return;
            }

            if (gramsToBuy <= 0) {
                System.out.println("Geçersiz miktar! Lütfen pozitif bir değer giriniz.");
                buyGold(goldAccount);
                return;
            }

            double totalCost = gramsToBuy * GOLD_PRICE_PER_GRAM;
            System.out.println(space);
            System.out.println("0 - Geri Dön");
            System.out.println("1 - Kredili Hesap");
            System.out.println("2 - Vadeli Hesap");
            System.out.println("3 - Vadesiz Hesap");
            System.out.print("Hangi hesabınızla ödeme yapmak istersiniz? ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            if (choice == 0) {
                showGoldAccount(goldAccount);
                return;
            }

            boolean paymentSuccess = switch (choice) {
                case 1 -> processPaymentFromAccount("Kredili", totalCost);
                case 2 -> processPaymentFromAccount("Vadeli", totalCost);
                case 3 -> processPaymentFromAccount("Vadesiz", totalCost);
                default -> {
                    System.out.println("Geçersiz seçenek. Lütfen tekrar deneyiniz.");
                    buyGold(goldAccount);
                    yield false;
                }
            };

            if (paymentSuccess) {
                goldAccount.setGoldBalance(goldAccount.getGoldBalance() + gramsToBuy);
                System.out.println("Başarılı bir şekilde " + gramsToBuy + " gram altın satın alındı.");
            } else {
                System.out.println("Altın satın alınamıyor. İşlem başarısız.");
            }

            showGoldAccount(goldAccount);
        } catch (Exception e) {
            System.out.println("Geçersiz giriş. Lütfen geçerli bir sayı giriniz.");
            scanner.nextLine();
            buyGold(goldAccount);
        }
    }

    private void sellGold(GoldAccountDetails goldAccount) {
        final double GOLD_PRICE_PER_GRAM = 3000.0;
        System.out.println(space);
        System.out.println("Geri dönmek için 0'a basın.");
        System.out.print("Kaç gram altın satmak istiyorsunuz? ");

        try {
            double gramsToSell = scanner.nextDouble();
            scanner.nextLine();

            if (gramsToSell == 0) {
                showGoldAccount(goldAccount);
                return;
            }

            if (gramsToSell <= 0 || gramsToSell > goldAccount.getGoldBalance()) {
                System.out.println("Geçersiz miktar! Lütfen sahip olduğunuz altın miktarı kadar bir değer giriniz.");
                sellGold(goldAccount);
                return;
            }

            double totalEarnings = gramsToSell * GOLD_PRICE_PER_GRAM;
            System.out.println(space);
            System.out.println("0 - Geri Dön");
            System.out.println("1 - Kredili Hesap");
            System.out.println("2 - Vadeli Hesap");
            System.out.println("3 - Vadesiz Hesap");
            System.out.println("Hangi hesabınıza para aktarmak istersiniz? ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            if (choice == 0) {
                showGoldAccount(goldAccount);
                return;
            }

            boolean transferSuccess = switch (choice) {
                case 1 -> processTransferToAccount("Kredili", totalEarnings);
                case 2 -> processTransferToAccount("Vadeli", totalEarnings);
                case 3 -> processTransferToAccount("Vadesiz", totalEarnings);
                default -> {
                    System.out.println("Geçersiz seçenek. Lütfen tekrar deneyiniz.");
                    sellGold(goldAccount);
                    yield false;
                }
            };

            if (transferSuccess) {
                goldAccount.setGoldBalance(goldAccount.getGoldBalance() - gramsToSell);
                System.out.println("Başarılı bir şekilde " + gramsToSell + " gram altın satıldı.");
            } else {
                System.out.println("Para aktarımı başarısız.");
            }

            showGoldAccount(goldAccount);
        } catch (Exception e) {
            System.out.println("Geçersiz giriş. Lütfen geçerli bir sayı giriniz.");
            scanner.nextLine();
            sellGold(goldAccount);
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
