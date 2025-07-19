import java.util.Scanner;

public class Contact {
    private Customer customer;

    public Contact(Customer customer) {
        this.customer = customer;
    }

    public void contactInfo() {
        System.out.println("Bize ulaşmak için 123 456 numarasını arayabilir ya da bank@net.com üzerinden mail yollayabilirsiniz.");
        Scanner scanner = new Scanner(System.in);
        customer.turnBack(scanner);
    }
}
