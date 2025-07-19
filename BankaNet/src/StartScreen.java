import java.util.Scanner;

public class StartScreen extends User {
    String space = "---------------------------------------";

    public StartScreen(String username, String password) {
        super(username, password);
    }

    public void printMessage() {
        Scanner scanner = new Scanner(System.in);
        String loginMessage = """
                ------ BANKNET'E  HOŞGELDİNİZ ------
                #Giriş yapmak için 1'e,
                #Hesap oluşturmak için 2'ye
                #Şifrenizi unuttuysanız 0'a basınız.
                Yapmak istediğiniz işlemi seçiniz:\s""";
        String exceptionMessage = "Geçersiz bir değer girdiniz. Lütfen 0, 1 ya da 2'yi seçin.";
        System.out.print(loginMessage);
        try {
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 0:
                    System.out.println(space);
                    System.out.println("Şifrenizi yenilemek için 123456'yı arayıp\nçalışanlarımızla görüşerek şifrenizi yenileyebilirsiniz.");
                    System.out.println(space);
                    printMessage();
                    break;
                case 1:
                    loginAccount(scanner);
                    break;
                case 2:
                    createAccount(scanner);
                    break;
                default:
                    System.out.println(space);
                    System.out.println(exceptionMessage);
                    System.out.println(space);
                    printMessage();
                    break;
            }
        } catch (Exception e) {
            System.out.println(space);
            System.out.println(exceptionMessage);
            System.out.println(space);
            scanner.nextLine();
            printMessage();
        }
    }

    public void loginAccount(Scanner scanner) {
        System.out.println(space);
        System.out.println("!Geri dönmek için kullanıcı adına 0 girip enter a basınız.");
        System.out.print("Kullanıcı adınız: ");
        String username = scanner.nextLine();
        if (username.equals("0")) {
            System.out.println(space);
            printMessage();
        }
        System.out.print("Şifreniz: ");
        String password = scanner.nextLine();

        if (managers.containsKey(username) && managers.get(username).equals(password)) {
            User manager = new Manager(username, password);
            System.out.println("Müdür olarak giriş yaptınız.");
            manager.displayScreen();
        } else if (employees.containsKey(username) && employees.get(username).equals(password)) {
            User employee = new Employee(username, password);
            System.out.println("Çalışan olarak giriş yaptınız.");
            employee.displayScreen();
        } else if (individualCustomers.containsKey(username) && individualCustomers.get(username).equals(password)) {
            User individualCustomer = new Customer(username, password);
            System.out.println("Bireysel Müşteri olarak giriş yaptınız.");
            individualCustomer.displayScreen();
        } else if (corporateCustomers.containsKey(username) && corporateCustomers.get(username).equals(password)) {
            User corporateCustomer = new Customer(username, password);
            System.out.println("Kurumsal Müşteri olarak giriş yaptınız.");
            corporateCustomer.displayScreen();
        } else {
            System.out.println("Kullanıcı adı veya şifre hatalı.");
            loginAccount(scanner);
        }
    }

    public void createAccount(Scanner scanner) {
        String username;
        String exceptionMessage = "Geçersiz bir değer girdiniz. Lütfen 1 ya da 2'yi seçin.";
        while (true) {
            System.out.println(space);
            System.out.print("Kullanıcı adınız: ");
            username = scanner.nextLine();
            if (username.isEmpty()) {
                System.out.println("Kullanıcı adı boş bırakılamaz. Lütfen geçerli bir kullanıcı adı girin.");
            } else if (individualCustomers.containsKey(username) || corporateCustomers.containsKey(username) || employees.containsKey(username) || managers.containsKey(username)) {
                System.out.println("Bu kullanıcı adı zaten mevcut. Lütfen başka bir kullanıcı adı giriniz.");
            } else {
                break;
            }
        }
        String password;
        while (true) {
            System.out.print("Şifreniz: ");
            password = scanner.nextLine();

            if (password.isEmpty()) {
                System.out.println("Şifre boş bırakılamaz. Lütfen geçerli bir şifre girin.");
            } else {
                break;
            }
        }
        System.out.println(space);
        System.out.print("""
                Lütfen hangi türde hesap oluşturmak istediğiniz seçiniz
                #Bireysel Müşteri hesabı için 1'e,
                #Kamusal Müşteri hesabı için 2'ye basınız.
                Yapmak istediğiniz işlemi seçiniz:\s""");
        try {
            int role = scanner.nextInt();
            scanner.nextLine();

            switch (role) {
                case 1:
                    IndividualCustomer individualCustomer = new IndividualCustomer(username, password);
                    System.out.println(space);
                    individualCustomer.getMoreInfo();
                    break;
                case 2:
                    System.out.println(space);
                    CorporateCustomer corporateCustomer = new CorporateCustomer(username, password);
                    corporateCustomer.getMoreInfo();
                    break;
                default:
                    System.out.println(exceptionMessage);
                    System.out.println(space);
                    printMessage();
                    break;
            }
        } catch (Exception e) {
            System.out.println(exceptionMessage);
            System.out.println(space);
            scanner.nextLine();
            printMessage();
        }
    }

    @Override
    public void displayScreen() {
    }
}