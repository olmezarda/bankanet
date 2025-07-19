import java.util.HashMap;
import java.util.Map;

public abstract class User {
    private String username;
    private String password;

    public abstract void displayScreen();

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    protected static final Map<String, String> managers = new HashMap<>() {
        {
            put("Furkan", "123");
        }
    };

    protected static final Map<String, String> individualCustomers = new HashMap<>() {
        {
            put("Ali", "123");
        }
    };

    protected static final Map<String, String> corporateCustomers = new HashMap<>() {
        {
            put("Kemal", "123");
        }
    };

    protected static final Map<String, String> employees = new HashMap<>() {
        {
            put("Ata", "123");
        }
    };
}
