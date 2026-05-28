import java.io.*;
import java.time.LocalDate;
import java.time.Month;
import java.util.*;

class Expense implements Serializable {
    double amount;
    String category;
    LocalDate date;

    Expense(double amount, String category, LocalDate date) {
        this.amount = amount;
        this.category = category;
        this.date = date;
    }

    public String toString() {
        return "Amount: " + amount +
                " | Category: " + category +
                " | Date: " + date;
    }
}

public class ExpenseTracker {

    static ArrayList<Expense> expenses = new ArrayList<>();
    static final String FILE_NAME = "expenses.dat";

    // Add expense
    static void addExpense(Scanner sc) {
        System.out.print("Enter Amount: ");
        double amount = sc.nextDouble();
        sc.nextLine();

        System.out.print("Enter Category: ");
        String category = sc.nextLine();

        System.out.print("Enter Date (YYYY-MM-DD): ");
        String dateInput = sc.nextLine();

        LocalDate date = LocalDate.parse(dateInput);

        expenses.add(new Expense(amount, category, date));
        System.out.println("Expense Added Successfully!\n");
    }

    // Display expenses
    static void displayExpenses() {
        if (expenses.isEmpty()) {
            System.out.println("No Expenses Found!\n");
            return;
        }

        System.out.println("\n----- Expense Records -----");
        for (Expense e : expenses) {
            System.out.println(e);
        }
        System.out.println();
    }

    // Monthly report
    static void monthlyReport(Scanner sc) {
        System.out.print("Enter Month Number (1-12): ");
        int month = sc.nextInt();

        double total = 0;

        for (Expense e : expenses) {
            if (e.date.getMonthValue() == month) {
                total += e.amount;
            }
        }

        System.out.println("Total Expense for " +
                Month.of(month) + " = " + total + "\n");
    }

    // Highest expense category
    static void highestCategory() {
        HashMap<String, Double> map = new HashMap<>();

        for (Expense e : expenses) {
            map.put(e.category,
                    map.getOrDefault(e.category, 0.0) + e.amount);
        }

        String maxCategory = "";
        double maxAmount = 0;

        for (String category : map.keySet()) {
            if (map.get(category) > maxAmount) {
                maxAmount = map.get(category);
                maxCategory = category;
            }
        }

        System.out.println("Highest Expense Category: " +
                maxCategory + " = " + maxAmount + "\n");
    }

    // Save data
    static void saveData() {
        try {
            ObjectOutputStream out =
                    new ObjectOutputStream(new FileOutputStream(FILE_NAME));

            out.writeObject(expenses);
            out.close();

            System.out.println("Data Saved Successfully!\n");

        } catch (Exception e) {
            System.out.println("Error Saving Data");
        }
    }

    // Load data
    @SuppressWarnings("unchecked")
    static void loadData() {
        try {
            ObjectInputStream in =
                    new ObjectInputStream(new FileInputStream(FILE_NAME));

            expenses = (ArrayList<Expense>) in.readObject();
            in.close();

            System.out.println("Data Loaded Successfully!\n");

        } catch (Exception e) {
            System.out.println("No Previous Data Found!\n");
        }
    }

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        loadData();

        int choice;

        do {
            System.out.println("===== Personal Expense Tracker =====");
            System.out.println("1. Add Expense");
            System.out.println("2. Display Expenses");
            System.out.println("3. Monthly Report");
            System.out.println("4. Highest Expense Category");
            System.out.println("5. Save Data");
            System.out.println("6. Exit");

            System.out.print("Enter Choice: ");
            choice = sc.nextInt();

            switch (choice) {
                case 1:
                    addExpense(sc);
                    break;

                case 2:
                    displayExpenses();
                    break;

                case 3:
                    monthlyReport(sc);
                    break;

                case 4:
                    highestCategory();
                    break;

                case 5:
                    saveData();
                    break;

                case 6:
                    saveData();
                    System.out.println("Exiting Program...");
                    break;

                default:
                    System.out.println("Invalid Choice!\n");
            }

        } while (choice != 6);

        sc.close();
    }
}
