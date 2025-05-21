

import java.util.*;
import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class ExpenseTracker {
	private static final List<Transaction>  transactions = new ArrayList<>();
	private static final Scanner sc = new Scanner(System.in);

	public static void main(String args[]) {
		while(true) {
			System.out.println("\n--- Expense Tracker Menu ---");
            System.out.println("1. Add Transaction");
            System.out.println("2. View Monthly Summary");
            System.out.println("3. Load from File");
            System.out.println("4. Save to File");
            System.out.println("5. Exit");
            System.out.print("Choose an option: ");

            int choice = Integer.parseInt(sc.nextLine());

			switch(choice) {
				case 1 -> addTransaction();
				case 2 -> viewMonthlySummary();
				case 3 -> loadFromFile();
				case 4 -> saveToFile();
				case 5 -> System.exit(0);
				default -> System.out.println("Invalid option.");
			}
		}
	}

		private static void addTransaction() {
			System.out.print("Enter type (Income/Expense): ");
			String type = sc.nextLine();

			String category = null;
			if(type.equalsIgnoreCase("Income")) {
				System.out.print("Enter category (Salary/Business): ");
				category = sc.nextLine();
				if (!category.equalsIgnoreCase("Salary") && !category.equalsIgnoreCase("Business")) {
        			System.out.println("Invalid category for Income. Please choose either 'Salary' or 'Business'.");
        			return;
    			}
			} else if(type.equalsIgnoreCase("Expense")) {
				System.out.print("Enter category (Food/Rent/Travel): ");
				category = sc.nextLine();
				if (!category.equalsIgnoreCase("Food") &&
       				 !category.equalsIgnoreCase("Rent") &&
        			 !category.equalsIgnoreCase("Travel")) {
       				 System.out.println("Invalid category for Expense. Please choose 'Food', 'Rent', or 'Travel'.");
        			 return;
    			}
			} else {
				System.out.println("Invalid type.");
				return;
			}
			
			System.out.print("Enter Amount: ");
			double amount = Double.parseDouble(sc.nextLine());
			LocalDate date = LocalDate.now();

			transactions.add(new Transaction(type, category, amount, date));
			System.out.println("Transaction added.");
		}

		private static void viewMonthlySummary() {
			System.out.print("Enter year and month (YYYY-MM): ");
			String[] ym = sc.nextLine().split("-");
			int year = Integer.parseInt(ym[0]);
			int month = Integer.parseInt(ym[1]);

			double totalIncome = 0, totalExpense = 0;

			for(Transaction t : transactions) {
				if(t.date.getYear() == year && t.date.getMonthValue() == month){
					if(t.type.equalsIgnoreCase("Income")) {
						totalIncome += t.amount;
					} else {
						totalExpense += t.amount;
					}
				}
			}

			System.out.println("\n--- Monthly Summary for " + year + "-" + month + " ---");
			System.out.println("Total Income: " + totalIncome);
        	System.out.println("Total Expense: " + totalExpense);
        	System.out.println("Net Savings: " + (totalIncome - totalExpense));
		}

		private static void loadFromFile() {
        	System.out.print("Enter filename to load: ");
        	String filename = sc.nextLine();

        	try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
        		transactions.clear();
            	String line;
            	while ((line = br.readLine()) != null) {
                transactions.add(Transaction.fromString(line));
            }
            	System.out.println("Data loaded from file.");

            	for (Transaction t : transactions) {
           			 System.out.println(t);
        		}
        	} catch (IOException e) {
            	System.out.println("Error reading file: " + e.getMessage());
        	}
    	}

    	private static void saveToFile() {
        	System.out.print("Enter filename to save: ");
        	String filename = sc.nextLine();

        	try (BufferedWriter bw = new BufferedWriter(new FileWriter(filename))) {
            	for (Transaction t : transactions) {
                	bw.write(t.toString());
                	bw.newLine();
            }
            	System.out.println("Data saved to file.");
        	} catch (IOException e) {
            	System.out.println("Error writing file: " + e.getMessage());
        	}
    	}
}