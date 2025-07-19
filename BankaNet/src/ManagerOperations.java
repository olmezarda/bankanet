import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class ManagerOperations implements Operations {
    private Manager manager;
    String space = "------------------------------------";

    public ManagerOperations(Manager manager) {
        this.manager = manager;
    }

    @Override
    public void displayOperations() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println(space);
            System.out.print("""
                    1- Müşteri Çıkar
                    2- Müşteri Ekle
                    3- Çalışan Çıkar
                    4- Çalışan Ekle
                    5- Geri Bildirim Oku
                    6- Hesaptan Çıkış Yap
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
                        employeeDelete(scanner);
                        break;
                    case 4:
                        employeeAdd(scanner);
                        break;
                    case 5:
                        readFeedback(scanner);
                        break;
                    case 6:
                        System.out.println("Hesaptan çıkış yapılıyor...");
                        System.out.println(space);
                        StartScreen startScreen = new StartScreen(manager.getUsername(), manager.getPassword());
                        startScreen.printMessage();
                        return;
                    case 0:
                        manager.displayScreen();
                        return;
                    default:
                        System.out.println("Geçersiz seçim! Lütfen 0 ile 6 arasında bir değer giriniz.");
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
            User.individualCustomers.put(username, password);
        } else if (typeChoice == 2) {
            User.corporateCustomers.put(username, password);
        }

        System.out.println("Müşteri başarıyla eklendi.");
    }

    private void employeeDelete(Scanner scanner) {
        System.out.println(space);
        System.out.print("Çıkarmak istediğiniz çalışanın kullanıcı adını girin: ");
        scanner.nextLine();
        String username = scanner.nextLine();

        if (User.employees.containsKey(username)) {
            User.employees.remove(username);
            System.out.println("Çalışan başarıyla çıkarıldı.");
        } else {
            System.out.println("Çalışan bulunamadı.");
        }
    }

    private void employeeAdd(Scanner scanner) {
        System.out.println(space);
        System.out.print("Yeni çalışan için kullanıcı adını girin: ");
        scanner.nextLine();
        String username = scanner.nextLine();

        if (User.employees.containsKey(username)) {
            System.out.println("Bu kullanıcı adı zaten var, lütfen başka bir kullanıcı adı girin.");
            return;
        }

        System.out.print("Yeni çalışanın şifresini girin: ");
        String password = scanner.nextLine();

        User.employees.put(username, password);
        System.out.println("Çalışan başarıyla eklendi.");
    }

    private void readFeedback(Scanner scanner) {
        String filePath = "feedback.txt";
        FileReader fileReader;
        BufferedReader bufferedReader;

        try {
            fileReader = new FileReader(filePath);
            bufferedReader = new BufferedReader(fileReader);

            String line;
            System.out.println(space);
            System.out.println("Geri bildirimler:");

            while ((line = bufferedReader.readLine()) != null) {
                System.out.println(line);
            }

            bufferedReader.close();

            System.out.println("\nGeri dönmek için 0'a basın.");
            int backChoice = scanner.nextInt();
            if (backChoice == 0) {
                manager.displayScreen();
            } else {
                System.out.println("Geçersiz seçim, geri dönülemedi.");
                readFeedback(scanner);
            }

        } catch (IOException e) {
            System.out.println("Geri bildirimler okunamadı: " + e.getMessage());
        }
    }
}
