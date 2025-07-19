import java.util.InputMismatchException;
import java.util.Scanner;

public class Support {
    private Customer customer;

    public Support(Customer customer) {
        this.customer = customer;
    }

    public void help() {
        String space = "------------------------------------";
        System.out.println(space);
        System.out.print("""
                !Geri dönmek için boşluksuz 0 girip enter a basınız.
                1- YATIRIMLARIMI NASIL YÖNETEBİLİRİM?
                2- ŞİFREMİ NASIL YENİLEYEBİLİRİM?
                3- KULLANICI ADIMI NASIL DEĞİŞTİREBİLİRİM?
                Size hangi konuda yardımcı olabilirim?\s""");

        Scanner scanner = new Scanner(System.in);
        boolean validInput = false;

        while (!validInput) {
            try {
                int choice = scanner.nextInt();
                switch (choice) {
                    case 1:
                        System.out.println(space);
                        System.out.println("Yatırımlarınızı yönetmek için yatırım hesaplarınızı gözden geçirebilir veya temsilcimizden farklı yatırım araçları hakkında bilgi alabilirsiniz.");
                        turnBackToHelp(scanner);
                        validInput = true;
                        break;
                    case 2:
                        System.out.println(space);
                        System.out.println("AYARLAR sekmesinden Şifremi Güncelle seçeneğini kullanarak ya da müşteri hizmetlerimize bağlanıp şifrenizi yenileyebilirsiniz.");
                        turnBackToHelp(scanner);
                        validInput = true;
                        break;
                    case 3:
                        System.out.println(space);
                        System.out.println("AYARLAR sekmesinden Kullanıcı Adımı Güncelle seçeneğini kullanarak ya da müşteri hizmetlerimize bağlanıp kullanıcı adınızı yenileyebilirsiniz.");
                        turnBackToHelp(scanner);
                        validInput = true;
                        break;
                    case 0:
                        customer.displayScreen();
                        validInput = true;
                        break;
                    default:
                        System.out.println(space);
                        System.out.println("Geçersiz bir seçenek girdiniz. Lütfen 0, 1, 2 veya 3'ü seçin.");
                        help();
                        break;
                }
            } catch (InputMismatchException e) {
                System.out.println(space);
                System.out.println("Geçersiz bir giriş yaptınız. Lütfen 0, 1, 2 veya 3'ü seçin.");
                help();
                scanner.nextLine();
            }
        }
    }

    public void turnBackToHelp(Scanner scanner) {
        System.out.println("!Geri dönmek için 0 girip ENTER a basınız.");

        try {
            int back = scanner.nextInt();
            if (back == 0) {
                help();
            } else {
                System.out.println("Ekranda kalınıyor");
                turnBackToHelp(scanner);
            }
        } catch (Exception e) {
            System.out.println("Geçersiz bir giriş yaptınız. Lütfen bir sayı girin.");
            scanner.nextLine();
            turnBackToHelp(scanner);
        }
    }
}
