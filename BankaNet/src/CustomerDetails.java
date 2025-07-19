public class CustomerDetails extends Customer {
    private Customer customer;

    public CustomerDetails(String username, String password) {
        super(username, password);
        this.customer = new Customer(getUsername(), getPassword());
    }

    public void showMoreInfo(String contact, String companyName) {
        System.out.println("Kullanıcı adı: " + getUsername());
        System.out.println("İletişim Bilgisi: " + contact);
        System.out.println("Şirket Adı: " + companyName);
        customer.displayScreen();
    }

    public void showMoreInfo(String contact, int age) {
        System.out.println("Kullanıcı adı: " + getUsername());
        System.out.println("İletişim Bilgisi: " + contact);
        System.out.println("Yaş: " + age);
        customer.displayScreen();
    }
}
