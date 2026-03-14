package Fish_Hatchery_System;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;

public class BuyerTransaction {
    private static final String TRANSACTION_FILE = "buyer_transactions.txt";
    private String buyerName;
    private String buyerContact;
    private Fish purchasedFish;
    private int quantity;
    private LocalDate transactionDate;

    public BuyerTransaction(String buyerName, String fishType, int qtyBought) {
        if (qtyBought <= 0) {
            throw new IllegalArgumentException("Quantity must be greater than zero.");
        }
        this.buyerName = buyerName;
        this.buyerContact = "N/A"; 
        this.quantity = qtyBought;
        this.purchasedFish = new Fish(fishType, 0, 0.0);
        this.transactionDate = LocalDate.now();
    }

    public BuyerTransaction(String buyerName, String buyerContact, Fish purchasedFish, int quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be greater than zero.");
        }
        this.buyerName = buyerName;
        this.buyerContact = buyerContact;
        this.purchasedFish = purchasedFish;
        this.quantity = quantity;
        this.transactionDate = LocalDate.now();
    }
    
    public String getTransactionDetails() {
        return "Buyer: " + buyerName +
               ", Contact: " + buyerContact +
               ", Purchased Fish: " + purchasedFish.getSpecies() +
               " (Quantity: " + quantity + ")" +
               ", Date: " + transactionDate.toString();
    }
    
    public void saveTransactionToFile(String fileName) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(fileName, true))) {
            writer.println(getTransactionDetails());
        } catch (IOException e) {
            System.out.println("Error saving transaction: " + e.getMessage());
        }
    }

    public void saveToFile() {
        saveTransactionToFile(TRANSACTION_FILE);
    }
}


