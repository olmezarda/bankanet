import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class Feedback {
    private Customer customer;

    public Feedback(Customer customer) {
        this.customer = customer;
    }

    public void sendMessage() {
        System.out.println("Görüşünüz bizim için çok değerli (Yazınızı tamamladıktan sonra iki kere ENTER tuşuna basarak işleminizi bitirebilirsiniz):");
        Scanner scanner = new Scanner(System.in);
        StringBuilder feedback = new StringBuilder();

        String line;
        while (true) {
            line = scanner.nextLine();
            if (line.isEmpty()) {
                break;
            }
            feedback.append(line).append("\n");
        }

        String filePath = "feedback.txt";

        try {
            File file = new File(filePath);
            if (!file.exists()) {
                file.createNewFile();
            }

            FileWriter writer = new FileWriter(file, true);
            writer.write("Geri Bildirim: \n" + feedback.toString());
            writer.write("Gönderilme Zamanı: " + getCurrentTimestamp() + "\n\n");
            writer.close();

            System.out.println("Geri bildiriminiz başarıyla kaydedildi.");
            customer.turnBack(scanner);

        } catch (IOException e) {
            System.out.println("Bir hata oluştu, geri bildiriminiz kaydedilemedi: " + e.getMessage());
        }
    }

    private static String getCurrentTimestamp() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        return now.format(formatter);
    }
}
