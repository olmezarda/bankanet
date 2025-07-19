import java.util.Scanner;

public class EmployeeOperations implements Operations {
    private Employee employee;
    String space = "------------------------------------";

    public EmployeeOperations(Employee employee) {
        this.employee = employee;
    }

    public void displayOperations() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println(space);
            System.out.print("""
                    1- Müşteri Çıkar
                    2- Müşteri Ekle
                    3- Müşteri Şifre Yenileme
                    4- Müşteri Kullanıcı Adı Yenileme
                    5- Hesaptan Çıkış Yap
                    0- Geri Dön
                    Seçiminizi yapınız:\s""");

            try {
                int choice = scanner.nextInt();

                switch (choice) {
                    case 1:
                        customerDelete(scanner);
                        break;
                    case 2:
                        customerAdd(scanner);
                        break;
                    case 3:
                        customerPasswordSet(scanner);
                        return;
                    case 4:
                        customerUsernameSet(scanner);
                        break;
                    case 5:
                        System.out.println("Hesaptan çıkış yapılıyor...");
                        System.out.println(space);
                        StartScreen startScreen = new StartScreen(employee.getUsername(), employee.getPassword());
                        startScreen.printMessage();
                        return;
                    case 0:
                        employee.displayScreen();
                        return;
                    default:
                        System.out.println("Geçersiz seçim! Lütfen 0 ile 3 arasında bir değer giriniz.");
                }
            } catch (Exception e) {
                System.out.println("Geçersiz giriş! Lütfen geçerli bir sayı giriniz.");
                scanner.nextLine();
            }
        }
    }

    @Override
    public void customerDelete(Scanner scanner) {
        System.out.println(space);
        System.out.print("Çıkarmak istediğiniz müşterinin kullanıcı adını girin: ");
        scanner.nextLine();
        String username = scanner.nextLine();

        if (User.individualCustomers.containsKey(username)) {
            User.individualCustomers.remove(username);
            System.out.println(username + " adlı bireysel müşteri başarıyla çıkarıldı.");
        } else if (User.corporateCustomers.containsKey(username)) {
            User.corporateCustomers.remove(username);
            System.out.println(username + " adlı kurumsal müşteri başarıyla çıkarıldı.");
        } else {
            System.out.println("Müşteri bulunamadı.");
        }
    }

    @Override
    public void customerAdd(Scanner scanner) {
        System.out.println(space);
        System.out.print("Yeni müşteri için kullanıcı adını girin: ");
        scanner.nextLine();
        String username = scanner.nextLine();

        if (User.individualCustomers.containsKey(username) || User.corporateCustomers.containsKey(username)) {
            System.out.println("Bu kullanıcı adı zaten var, lütfen başka bir kullanıcı adı girin.");
            return;
        }

        System.out.print("Yeni müşterinin şifresini girin: ");
        String password = scanner.nextLine();

        System.out.println(space);
        System.out.print("1- Bireysel\n2- Kurumsal\nMüşteri türünü seçin: ");
        int typeChoice = scanner.nextInt();

        if (typeChoice == 1) {
            IndividualCustomer individualCustomer = new IndividualCustomer(username, password);
            individualCustomer.getMoreInfo();
        } else if (typeChoice == 2) {
            CorporateCustomer corporateCustomer = new CorporateCustomer(username, password);
            corporateCustomer.getMoreInfo();
        }
    }

    public void customerUsernameSet(Scanner scanner) {
        System.out.println(space);
        System.out.print("Kullanıcı adını değiştirmek istediğiniz müşterinin mevcut kullanıcı adını girin: ");
        scanner.nextLine();
        String oldUsername = scanner.nextLine();

        if (User.individualCustomers.containsKey(oldUsername)) {
            System.out.print("Yeni kullanıcı adını girin: ");
            String newUsername = scanner.nextLine();

            if (User.individualCustomers.containsKey(newUsername) || User.corporateCustomers.containsKey(newUsername)) {
                System.out.println("Bu kullanıcı adı zaten var. Lütfen başka bir kullanıcı adı girin.");
                return;
            }

            String password = User.individualCustomers.remove(oldUsername);
            User.individualCustomers.put(newUsername, password);
            System.out.println("Kullanıcı adı başarıyla değiştirildi.");

        } else if (User.corporateCustomers.containsKey(oldUsername)) {
            System.out.print("Yeni kullanıcı adını girin: ");
            String newUsername = scanner.nextLine();

            if (User.individualCustomers.containsKey(newUsername) || User.corporateCustomers.containsKey(newUsername)) {
                System.out.println("Bu kullanıcı adı zaten var. Lütfen başka bir kullanıcı adı girin.");
                return;
            }

            String password = User.corporateCustomers.remove(oldUsername);
            User.corporateCustomers.put(newUsername, password);
            System.out.println("Kullanıcı adı başarıyla değiştirildi.");

        } else {
            System.out.println("Müşteri bulunamadı.");
        }
        displayOperations();
    }

    public void customerPasswordSet(Scanner scanner) {
        System.out.println(space);
        System.out.print("Şifresini değiştirmek istediğiniz müşterinin kullanıcı adını girin: ");
        scanner.nextLine();
        String username = scanner.nextLine();

        if (User.individualCustomers.containsKey(username)) {
            System.out.print("Yeni şifreyi girin: ");
            String newPassword = scanner.nextLine();
            User.individualCustomers.put(username, newPassword);
            System.out.println("Şifre başarıyla değiştirildi.");

        } else if (User.corporateCustomers.containsKey(username)) {
            System.out.print("Yeni şifreyi girin: ");
            String newPassword = scanner.nextLine();
            User.corporateCustomers.put(username, newPassword);
            System.out.println("Şifre başarıyla değiştirildi.");

        } else {
            System.out.println("Müşteri bulunamadı.");
        }
        displayOperations();
    }

}
