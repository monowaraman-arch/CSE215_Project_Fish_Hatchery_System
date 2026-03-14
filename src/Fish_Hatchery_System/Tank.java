package Fish_Hatchery_System;

import java.io.File;
import java.io.IOException;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.Scanner;

public class Tank {
    private static final String TANK_FOLDER = "tanks";
    private static final String FEEDING_LOG_FILE = "feeding_log.txt";
    private int id;
    private int capacity;
    private Fish[] fishList;
    private int fishCount;

    public Tank(int id, int capacity) {
        if (capacity <= 0) {
            throw new IllegalArgumentException("Tank capacity must be greater than zero.");
        }
        this.id = id;
        this.capacity = capacity;
        this.fishList = new Fish[capacity];
        this.fishCount = 0;
    }

    public int getId() {
        return id;
    }

    public int getCapacity() {
        return capacity;
    }

    public int getFishCount() {
        return fishCount;
    }

    public boolean addFish(Fish fish) {
        if (fish == null) {
            return false;
        }
        if (fishCount < fishList.length) {
            fishList[fishCount] = fish;
            fishCount++;
            return true;
        } else {
            System.out.println("Tank is full!");
            return false;
        }
    }

    public void viewFish() {
        System.out.println("Tank " + id + " has " + fishCount + " fish:");
        if (fishCount == 0) {
            System.out.println("(No fish in this tank.)");
            return;
        }
        for (int i = 0; i < fishCount; i++) {
            System.out.println("- " 
                + fishList[i].getSpecies() 
                + ", Age: " + fishList[i].getAge() 
                + ", Weight: " + fishList[i].getWeight());
        }
    }

    public int feedFish() {
        if (fishCount == 0) {
            System.out.println("No fish available in tank " + id + ".");
            return 0;
        }
        for (int i = 0; i < fishCount; i++) {
            fishList[i].feed();
        }
        logFeeding();
        return fishCount;
    }


    public int transferFishTo(Tank targetTank) {
        if (targetTank == null) {
            return 0;
        }
        if (targetTank.id == this.id) {
            System.out.println("Cannot transfer fish to the same tank.");
            return 0;
        }

        int originalCount = fishCount;
        int movedCount = 0;
        while (movedCount < fishCount && targetTank.fishCount < targetTank.fishList.length) {
            targetTank.fishList[targetTank.fishCount++] = fishList[movedCount];
            movedCount++;
        }

        if (movedCount == 0) {
            System.out.println("Target tank is full!");
            return 0;
        }

        for (int i = movedCount; i < originalCount; i++) {
            fishList[i - movedCount] = fishList[i];
        }
        for (int i = originalCount - movedCount; i < originalCount; i++) {
            fishList[i] = null;
        }
        fishCount = originalCount - movedCount;

        if (movedCount < originalCount) {
            System.out.println("Target tank filled before all fish could be transferred.");
        }
        return movedCount;
    }

    public void saveToFile() {
    File folder = new File(TANK_FOLDER);
    if (!folder.exists()) {
        folder.mkdir();
    }

    File file = new File(folder, "tank_" + id + ".txt");
    try (PrintWriter writer = new PrintWriter(file)) {
        writer.println(id + "," + capacity);
        for (int i = 0; i < fishCount; i++) {
            writer.println(fishList[i].toFileString());
        }
    } catch (Exception e) {
        e.printStackTrace();
      }
    }


    public static Tank loadFromFile(String filename) {
        File file = new File(filename);
        if (!file.exists()) {
            System.out.println("Tank file not found: " + filename);
            return null;
        }

        try (Scanner scanner = new Scanner(file)) {

            if (!scanner.hasNextLine()) {
                System.out.println("Tank file is empty: " + filename);
                return null;
            }
            String header = scanner.nextLine();         
            String[] parts = header.split(",");         
            if (parts.length != 2) {
                System.out.println("Invalid tank header in file: " + filename);
                return null;
            }
            int id = Integer.parseInt(parts[0].trim());        
            int cap = Integer.parseInt(parts[1].trim());         

            Tank tank = new Tank(id, cap);


            while (scanner.hasNextLine()) {
                String line = scanner.nextLine().trim();
                if (line.isEmpty()) continue;   
                Fish fish = Fish.fromFileString(line);       
                if (fish != null) {
                    tank.addFish(fish);        
                }
            }
            return tank;

        } catch (Exception e) {
            System.out.println("Error loading tank data: " + e.getMessage());
            return null;
        }
    }

    private void logFeeding() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(FEEDING_LOG_FILE, true))) {
            writer.println(LocalDate.now() + " - Tank " + id + " fed " + fishCount + " fish.");
        } catch (IOException e) {
            System.out.println("Error saving feeding log: " + e.getMessage());
        }
    }
}
