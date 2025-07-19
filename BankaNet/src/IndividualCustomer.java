import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class IndividualCustomer extends Customer {
    String space = "---------------------------------------";
    private CustomerDetails customerDetails;

    public IndividualCustomer(String username, String password) {
        super(username, password);
        this.customerDetails = new CustomerDetails(username, password);
    }

    protected static final Map<String, Integer> individualMoreInfo = new HashMap<>();

    public void getMoreInfo() {
        Scanner scanner = new Scanner(System.in);

        String contact;
        while (true) {
            System.out.print("Size ulaşabilmemiz için e-mail adresinizi ya da telefon numaranızı giriniz: ");
            contact = scanner.nextLine();
            if (individualCustomers.containsKey(contact)) {
                System.out.println("Hata: Bu iletişim bilgisine sahip hesap bulunmaktadır. Lütfen başka bir iletişim adresi girin.");
            } else if (contact.isEmpty()) {
                System.out.println("Hata: Bu alan boş bırakılamaz. Lütfen geçerli bir e-mail adresi ya da telefon numarası giriniz.");
            } else {
                break;
            }
        }

        int age = 0;
        while (true) {
            try {
                System.out.print("Yaşınız: ");
                age = scanner.nextInt();
                if (age < 18) {
                    System.out.println("Hesap oluşturmak için en az 18 yaşında olmanız gerekmektedir.");
                    StartScreen startScreen = new StartScreen("default", "default");
                    startScreen.printMessage();
                } else {
                    break;
                }
            } catch (Exception e) {
                System.out.println("Geçersiz giriş! Lütfen geçerli bir sayı giriniz.");
                scanner.nextLine();
            }
        }
        individualCustomers.put(getUsername(), getPassword());
        individualMoreInfo.put(contact, age);
        System.out.println("Hesap başarıyla oluşturuldu...");
        System.out.println(space);
        customerDetails.showMoreInfo(contact, age);
    }

}
