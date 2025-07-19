import java.util.HashMap;
import java.util.Map;

public abstract class Accounts {
    public static Map<String, CurrentAccountDetails> currentAccounts = new HashMap<>();
    public static Map<String, CreditAccountDetails> creditAccounts = new HashMap<>();
    public static Map<String, DepositAccountDetails> depositAccounts = new HashMap<>();
    public static Map<String, GoldAccountDetails> goldAccounts = new HashMap<>();
    public static Map<String, CurrencyAccountDetails> currencyAccounts = new HashMap<>();

    public abstract void updateAccountPassword();
}
