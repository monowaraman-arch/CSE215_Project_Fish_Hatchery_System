package Fish_Hatchery_System;

import java.io.*;
import java.util.Scanner;

public class Supply {
    private String name;              
    private int quantity;
    private String unit;

    public Supply(String name, int quantity, String unit) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Supply name cannot be empty.");
        }
        if (unit == null || unit.trim().isEmpty()) {
            throw new IllegalArgumentException("Supply unit cannot be empty.");
        }
        if (quantity < 0) {
            throw new IllegalArgumentException("Supply quantity cannot be negative.");
        }
        this.name = name.trim();
        this.quantity = quantity;
        this.unit = unit.trim();
    }

    public String getName() {
        return name;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getUnit() {
        return unit;
    }

    public void addQuantity(int amount) {             
        if (amount <= 0) {
            throw new IllegalArgumentException("Added quantity must be greater than zero.");
        }
        quantity += amount;
    }

    public void useQuantity(int amount) {           
        if (amount <= 0) {
            throw new IllegalArgumentException("Used quantity must be greater than zero.");
        }
        if (amount <= quantity) {
            quantity -= amount;
        } else {
            System.out.println("Not enough " + name + " in stock!");
        }
    }

    public void viewSupply() {                        
        System.out.println(name + ": " + quantity + " " + unit);
    }

    public String toFileString() {                      
        return name + "," + quantity + "," + unit;
    }

    public static Supply fromFileString(String line) {        
        String[] parts = line.split(",", 3);
        if (parts.length != 3) {
            throw new IllegalArgumentException("Invalid supply data format.");
        }
        return new Supply(parts[0], Integer.parseInt(parts[1].trim()), parts[2]);
    }

    public static void saveAllSupplies(Supply[] supplies, int count) {
    File file = new File("supplies.txt");   
    try (PrintWriter writer = new PrintWriter(file)) {
        for (int i = 0; i < count; i++) {
            if (supplies[i] != null) {
                writer.println(supplies[i].toFileString());
            }
        }
    } catch (Exception e) {
        e.printStackTrace();
      }
    }


    public static Supply[] loadAllSupplies() { 
        Supply[] supplies = new Supply[20];
        File file = new File("supplies.txt");
        int index = 0;
        if (!file.exists()) {
            return supplies;
        }
        try (Scanner scanner = new Scanner(file)) {  
            while (scanner.hasNextLine() && index < supplies.length) {
                String line = scanner.nextLine().trim();
                if (line.isEmpty()) {
                    continue;
                }
                try {
                    supplies[index++] = Supply.fromFileString(line);
                } catch (IllegalArgumentException e) {
                    System.out.println("Skipping invalid supply entry: " + line);
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("No supply file found or error loading supplies.");
        }
        return supplies;
    }
}
