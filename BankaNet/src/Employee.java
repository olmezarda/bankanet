import java.util.Scanner;

public class Employee extends User {
    private EmployeeSettings employeeSettings;
    private EmployeeOperations employeeOperations;

    public Employee(String username, String password) {
        super(username, password);
        this.employeeSettings = new EmployeeSettings(this);
        this.employeeOperations = new EmployeeOperations(this);
    }

    public void displayScreen() {
        String options = """
                1- AYARLAR
                2- İŞLEMLER
                0- ÇIKIŞ
                Yapmak istediğiniz işlemi seçiniz:\s""";
        String space = "------------------------------------";
        System.out.println(space);
        System.out.print(options);
        Scanner scanner = new Scanner(System.in);

        try {
            int choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    employeeSettings.displaySettings();
                    break;
                case 2:
                    employeeOperations.displayOperations();
                    break;
                case 0:
                    System.out.println(space);
                    System.out.println("Uygulamadan çıkılıyor...");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Geçersiz seçenek! Lütfen 0 ile 2 arasında bir seçenek giriniz.");
                    displayScreen();
                    break;
            }
        } catch (Exception e) {
            System.out.println("Geçersiz giriş! Lütfen 0 ile 2 arasında bir seçenek giriniz.");
            scanner.nextLine();
            displayScreen();
        }
    }
}
