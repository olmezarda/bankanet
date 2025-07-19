import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class CorporateCustomer extends Customer {
    String space = "---------------------------------------";
    private CustomerDetails customerDetails;

    public CorporateCustomer(String username, String password) {
        super(username, password);
        this.customerDetails = new CustomerDetails(username, password);
    }

    protected static final Map<String, String> corporateMoreInfo = new HashMap<>();

    public void getMoreInfo() {
        Scanner scanner = new Scanner(System.in);

        String contact;
        while (true) {
            System.out.print("Size ulaşabilmemiz için e-mail adresinizi ya da telefon numaranızı giriniz: ");
            contact = scanner.nextLine();
            if (corporateCustomers.containsKey(contact)) {
                System.out.println("Hata: Bu iletişim bilgisine sahip hesap bulunmaktadır. Lütfen başka bir iletişim adresi girin.");
            } else if (contact.isEmpty()) {
                System.out.println("Hata: Bu alan boş bırakılamaz. Lütfen geçerli bir e-mail adresi ya da telefon numarası giriniz.");
            } else {
                break;
            }
        }

        String companyName;
        while (true) {
            try {
                System.out.print("Şirket adınız: ");
                companyName = scanner.nextLine();
                if (companyName.isEmpty()) {
                    System.out.println("Hata: Şirket adınız boş olamaz. Lütfen geçerli bir değer girin.");
                } else {
                    break;
                }
            } catch (Exception e) {
                System.out.println("Geçersiz giriş! Lütfen geçerli bir sayı giriniz.");
                scanner.nextLine();
            }
        }
        corporateCustomers.put(getUsername(), getPassword());
        corporateMoreInfo.put(contact, companyName);
        System.out.println("Hesap başarıyla oluşturuldu...");
        System.out.println(space);
        customerDetails.showMoreInfo(contact, companyName);
    }
}
