import java.util.Scanner;

public class CustomerSettings implements Settings {
    private Customer customer;
    String space = "------------------------------------";

    public CustomerSettings(Customer customer) {
        this.customer = customer;
    }

    @Override
    public void displaySettings() {
        Scanner scanner = new Scanner(System.in);
        System.out.println(space);
        while (true) {
            System.out.print("""
                    1- Şifremi Güncelle
                    2- Kullanıcı Adımı Güncelle
                    3- Hesaptan Çıkış Yap
                    0- Geri Dön
                    Seçiminizi yapınız:\s""");

            try {
                int choice = scanner.nextInt();

                switch (choice) {
                    case 1:
                        updatePassword(scanner);
                        break;
                    case 2:
                        updateUsername(scanner);
                        break;
                    case 3:
                        System.out.println("Hesaptan çıkış yapılıyor...");
                        System.out.println(space);
                        StartScreen startScreen = new StartScreen(customer.getUsername(), customer.getPassword());
                        startScreen.printMessage();
                        return;
                    case 0:
                        customer.displayScreen();
                        return;
                    default:
                        System.out.println("Geçersiz seçim! Lütfen 0 ile 4 arasında bir değer giriniz.");
                        System.out.println(space);
                }
            } catch (Exception e) {
                System.out.println("Geçersiz giriş! Lütfen geçerli bir sayı giriniz.");
                System.out.println(space);
                scanner.nextLine();
            }
        }
    }

    @Override
    public void updatePassword(Scanner scanner) {
        System.out.println(space);
        System.out.print("Şifre değiştirmek için kullanıcı adınızı girin: ");
        String username = scanner.next();

        if (customer.getUsername().equals(username)) {
            System.out.print("Mevcut şifrenizi girin: ");
            String currentPassword = scanner.next();

            if (User.individualCustomers.get(username).equals(currentPassword) || User.corporateCustomers.get(username).equals(currentPassword)) {
                System.out.print("Yeni şifrenizi girin: ");
                String newPassword = scanner.next();
                if (User.individualCustomers.containsKey(username)) {
                    User.individualCustomers.put(username, newPassword);
                }
                if (User.corporateCustomers.containsKey(username)) {
                    User.corporateCustomers.put(username, newPassword);
                }
                System.out.println("Şifreniz başarıyla güncellendi!");
            } else {
                System.out.println("Geçersiz şifre! Şifreyi tekrar kontrol edin.");
            }
        } else {
            System.out.println("Mevcut kullanıcı adınızı doğru giriniz.");
        }
        System.out.println(space);
    }

    @Override
    public void updateUsername(Scanner scanner) {
        System.out.println(space);
        System.out.print("Mevcut kullanıcı adınızı girin: ");
        String currentUsername = scanner.next();

        if (!customer.getUsername().equals(currentUsername)) {
            System.out.println("Geçersiz kullanıcı adı! Lütfen mevcut kullanıcı adınızı doğru girin.");
            System.out.println(space);
            return;
        }

        System.out.print("Yeni kullanıcı adınızı girin: ");
        String newUsername = scanner.next();

        if (newUsername.isEmpty()) {
            System.out.println("Kullanıcı adı boş olamaz. Lütfen geçerli bir kullanıcı adı girin.");
            System.out.println(space);
            return;
        }

        if (User.individualCustomers.containsKey(newUsername) || User.corporateCustomers.containsKey(newUsername)) {
            System.out.println("Bu kullanıcı adı zaten alınmış. Lütfen başka bir kullanıcı adı seçin.");
            System.out.println(space);
            return;
        }

        if (User.individualCustomers.containsKey(currentUsername)) {
            String password = customer.getPassword();
            User.individualCustomers.remove(currentUsername);
            User.individualCustomers.put(newUsername, password);
        }

        if (User.corporateCustomers.containsKey(currentUsername)) {
            String password = customer.getPassword();
            User.corporateCustomers.remove(currentUsername);
            User.corporateCustomers.put(newUsername, password);
        }

        customer.setUsername(newUsername);
        System.out.println("Kullanıcı adınız başarıyla güncellendi!");
        System.out.println(space);
    }
}
