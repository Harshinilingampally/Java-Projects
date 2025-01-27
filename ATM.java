import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

class Account {
    private String accountNumber;
    private String pin;
    private double balance;

    public Account(String accountNumber, String pin, double initialBalance) {
        this.accountNumber = accountNumber;
        this.pin = pin;
        this.balance = initialBalance;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public boolean validatePin(String inputPin) {
        return pin.equals(inputPin);
    }

    public double getBalance() {
        return balance;
    }

    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
            System.out.printf("Successfully deposited $%.2f%n", amount);
        }
    }

    public boolean withdraw(double amount) {
        if (amount > 0 && amount <= balance) {
            balance -= amount;
            System.out.printf("Successfully withdrawn $%.2f%n", amount);
            return true;
        }
        System.out.println("Insufficient funds or invalid amount");
        return false;
    }
}

public class ATM {
    private static Map<String, Account> accounts = new HashMap<>();
    private static Account currentAccount = null;
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        // Initialize some test accounts
        initializeAccounts();

        while (true) {
            if (currentAccount == null) {
                if (!login()) {
                    continue;
                }
            }

            displayMenu();
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    checkBalance();
                    break;
                case 2:
                    withdraw();
                    break;
                case 3:
                    deposit();
                    break;
                case 4:
                    logout();
                    break;
                case 5:
                    System.out.println("Thank you for using our ATM. Goodbye!");
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private static void initializeAccounts() {
        // Add some test accounts (account number, PIN, initial balance)
        accounts.put("1234", new Account("1234", "5678", 1000.00));
        accounts.put("4321", new Account("4321", "8765", 2000.00));
    }

    private static boolean login() {
        System.out.println("\n=== ATM Login ===");
        System.out.print("Enter account number: ");
        String accountNumber = scanner.next();
        System.out.print("Enter PIN: ");
        String pin = scanner.next();

        Account account = accounts.get(accountNumber);
        if (account != null && account.validatePin(pin)) {
            currentAccount = account;
            System.out.println("Login successful!");
            return true;
        } else {
            System.out.println("Invalid account number or PIN");
            return false;
        }
    }

    private static void displayMenu() {
        System.out.println("\n=== ATM Menu ===");
        System.out.println("1. Check Balance");
        System.out.println("2. Withdraw");
        System.out.println("3. Deposit");
        System.out.println("4. Logout");
        System.out.println("5. Exit");
        System.out.print("Choose an option: ");
    }

    private static void checkBalance() {
        System.out.printf("Current balance: $%.2f%n", currentAccount.getBalance());
    }

    private static void withdraw() {
        System.out.print("Enter amount to withdraw: $");
        double amount = scanner.nextDouble();
        currentAccount.withdraw(amount);
    }

    private static void deposit() {
        System.out.print("Enter amount to deposit: $");
        double amount = scanner.nextDouble();
        currentAccount.deposit(amount);
    }

    private static void logout() {
        currentAccount = null;
        System.out.println("Logged out successfully");
    }
}