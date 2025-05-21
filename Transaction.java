

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;


public class Transaction {
	String type;
	String category;
	double amount;
	LocalDate date;

	Transaction(String type, String category, double amount, LocalDate date) {
		this.type = type;
		this.category = category;
		this.amount = amount;
		this.date = date;
	}

	@Override
	public String toString() {
		return type + "," + category + "," + amount + "," + date;
	}

	public static Transaction fromString(String line) {
		String parts[] = line.split(",");
		return new Transaction(parts[0], parts[1], Double.parseDouble(parts[2]), LocalDate.parse(parts[3]));
	}

}