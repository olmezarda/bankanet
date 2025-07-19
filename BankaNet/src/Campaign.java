import java.util.InputMismatchException;
import java.util.Scanner;

public class Campaign {
    private Customer customer;
    String space = "------------------------------------";

    public Campaign(Customer customer) {
        this.customer = customer;
    }

    public void showCurrentCampaigns() {
        Scanner scanner = new Scanner(System.in);
        boolean validInput = false;

        while (!validInput) {
            try {
                System.out.println(space);
                System.out.print("""
                        !Geri dönmek için boşluksuz 0 girip enter a basınız.
                        1- VADELİ HESAP KAMPANYASI
                        2- YENİ HESAP KAMPANYASI
                        3- KREDİLİ HESAP KAMPANYASI
                        Size hangi konuda yardımcı olabilirim?\s""");

                int choice = scanner.nextInt();
                switch (choice) {
                    case 1:
                        System.out.println(space);
                        CurrentAccountCampaign.showCampaign();
                        turnBackToCampaigns(scanner);
                        validInput = true;
                        break;
                    case 2:
                        System.out.println(space);
                        DepositAccountCampaign.showCampaign();
                        turnBackToCampaigns(scanner);
                        validInput = true;
                        break;
                    case 3:
                        System.out.println(space);
                        CreditAccountCampaign.showCampaign();
                        turnBackToCampaigns(scanner);
                        validInput = true;
                        break;
                    case 0:
                        customer.displayScreen();
                        validInput = true;
                        break;
                    default:
                        System.out.println("Geçersiz bir seçenek girdiniz. Lütfen 0, 1, 2 veya 3'ü seçin.");
                        break;
                }
            } catch (InputMismatchException e) {
                System.out.println("Geçersiz bir giriş yaptınız. Lütfen yalnızca bir sayı girin.");
                scanner.nextLine();
            }
        }
    }

    public void turnBackToCampaigns(Scanner scanner) {
        System.out.println("!Geri dönmek için 0 girip ENTER a basınız.");

        try {
            int back = scanner.nextInt();
            if (back == 0) {
                showCurrentCampaigns();
            } else {
                System.out.println("Ekranda kalınıyor");
                turnBackToCampaigns(scanner);
            }
        } catch (Exception e) {
            System.out.println("Geçersiz bir giriş yaptınız. Lütfen bir sayı girin.");
            scanner.nextLine();
            turnBackToCampaigns(scanner);
        }
    }
}
