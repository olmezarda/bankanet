import java.util.Scanner;

public class Manager extends User {
    private ManagerSettings managerSettings;
    private ManagerOperations managerOperations;

    public Manager(String username, String password) {
        super(username, password);
        this.managerSettings = new ManagerSettings(this);
        this.managerOperations = new ManagerOperations(this);
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
                    managerSettings.displaySettings();
                    break;
                case 2:
                    managerOperations.displayOperations();
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
