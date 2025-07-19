import java.util.Scanner;

public class EmployeeSettings implements Settings {
    private Employee employee;
    String space = "------------------------------------";

    public EmployeeSettings(Employee employee) {
        this.employee = employee;
    }

    @Override
    public void displaySettings() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println(space);
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
                        System.out.println(space);
                        System.out.println("Hesaptan çıkış yapılıyor...");
                        StartScreen startScreen = new StartScreen(employee.getUsername(), employee.getPassword());
                        startScreen.printMessage();
                        return;
                    case 0:
                        employee.displayScreen();
                        return;
                    default:
                        System.out.println("Geçersiz seçim! Lütfen 0 ile 4 arasında bir değer giriniz.");
                }
            } catch (Exception e) {
                System.out.println("Geçersiz giriş! Lütfen geçerli bir sayı giriniz.");
                scanner.nextLine();
            }
        }
    }

    @Override
    public void updatePassword(Scanner scanner) {
        System.out.println(space);
        System.out.print("Şifre değiştirmek için kullanıcı adınızı girin: ");
        String username = scanner.next();

        if (employee.getUsername().equals(username)) {
            System.out.print("Mevcut şifrenizi girin: ");
            String currentPassword = scanner.next();

            if (User.employees.get(username).equals(currentPassword)) {
                System.out.print("Yeni şifrenizi girin: ");
                String newPassword = scanner.next();
                User.employees.put(username, newPassword);
                System.out.println("Şifreniz başarıyla güncellendi!");
            } else {
                System.out.println("Geçersiz şifre! Şifreyi tekrar kontrol edin.");
            }
        } else {
            System.out.println("Mevcut kullanıcı adınızı doğru giriniz.");
        }
    }

    @Override
    public void updateUsername(Scanner scanner) {
        System.out.println(space);
        System.out.print("Mevcut kullanıcı adınızı girin: ");
        String currentUsername = scanner.next();

        if (!employee.getUsername().equals(currentUsername)) {
            System.out.println("Geçersiz kullanıcı adı! Lütfen mevcut kullanıcı adınızı doğru girin.");
            return;
        }

        System.out.print("Yeni kullanıcı adınızı girin: ");
        String newUsername = scanner.next();

        if (newUsername.isEmpty()) {
            System.out.println("Kullanıcı adı boş olamaz. Lütfen geçerli bir kullanıcı adı girin.");
            return;
        }

        if (User.employees.containsKey(newUsername)) {
            System.out.println("Bu kullanıcı adı zaten alınmış. Lütfen başka bir kullanıcı adı seçin.");
            return;
        }

        String password = employee.getPassword();
        User.employees.remove(currentUsername);
        User.employees.put(newUsername, password);

        employee.setUsername(newUsername);

        System.out.println("Kullanıcı adınız başarıyla güncellendi!");
    }
}
