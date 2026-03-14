package Fish_Hatchery_System;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

public class Hatchery {

    private Tank[] tanks = new Tank[20];
    private int tankCount = 0;
    private Supply[] supplies = new Supply[20];
    private int supplyCount = 0;
    private Maintenance[] maintenanceTasks = new Maintenance[20];
    private int maintenanceCount = 0;
    private static final String TANK_FOLDER = "tanks";
    private static final String MAINTENANCE_FILE = "maintenance.txt";

    public Hatchery() {
        loadSupplies();
        loadTanks();
        loadMaintenanceTasks();
    }

    public void addTank(Tank tank) {
        if (tank == null) {
            return;
        }
        if (getTankById(tank.getId()) != null) {
            System.out.println("A tank with this ID already exists.");
            return;
        }
        if (tankCount < tanks.length) {
            tanks[tankCount] = tank;
            tankCount++;
            tank.saveToFile();  
            System.out.println("Tank added and saved.");
        } else {
            System.out.println("Maximum number of tanks reached.");
        }
    }

    public Tank getTankById(int id) {
        for (int i = 0; i < tankCount; i++) {
            if (tanks[i].getId() == id) {
                return tanks[i];
            }
        }
        return null;
    }

    public void viewTanks() {
        if (tankCount == 0) {
            System.out.println("No tanks found.");
            return;
        }
        for (int i = 0; i < tankCount; i++) {
            System.out.println("Tank ID: " + tanks[i].getId());
            tanks[i].viewFish();
        }
    }

    public void saveAllTanks() {
        for (int i = 0; i < tankCount; i++) {
            tanks[i].saveToFile();
        }
    }

    public void loadTanks() {
        File folder = new File(TANK_FOLDER);
        if (!folder.exists()) {
            folder.mkdir();
        }
        loadTankFiles(folder);
        loadTankFiles(new File("."));
    }

    private void loadTankFiles(File directory) {
        File[] files = directory.listFiles();
        if (files == null) {
            return;
        }
        for (File file : files) {
            if (!file.isFile()) {
                continue;
            }
            String name = file.getName();
            if (name.startsWith("tank_") && name.endsWith(".txt")) {
                Tank t = Tank.loadFromFile(file.getPath());
                if (t != null && getTankById(t.getId()) == null && tankCount < tanks.length) {
                    tanks[tankCount++] = t;
                }
            }
        }
    }

    public void addSupply(Supply supply) {
        if (addOrMergeSupply(supply, true)) {
            System.out.println("Supply saved.");
        }
    }

    public void viewSupplies() {
        if (supplyCount == 0) {
            System.out.println("No supplies found.");
            return;
        }
        for (int i = 0; i < supplyCount; i++) {
            supplies[i].viewSupply();
        }
    }

    public Supply findSupply(String name) {
        for (int i = 0; i < supplyCount; i++) {
            if (supplies[i].getName().equalsIgnoreCase(name)) {
                return supplies[i];
            }
        }
        return null;
    }

    public void loadSupplies() {
        Supply[] loaded = Supply.loadAllSupplies();
        for (Supply s : loaded) {
            addOrMergeSupply(s, false);
        }
    }

    public void saveAllData() {
        saveAllTanks();
        saveAllMaintenanceTasks();
        Supply.saveAllSupplies(supplies, supplyCount);
    }

    public void generateReport() {
        System.out.println("===== HATCHERY SYSTEM REPORT =====");
        System.out.println("Total Tanks: " + tankCount);
        System.out.println("Total Supplies: " + supplyCount);
        System.out.println("Total Maintenance Tasks: " + maintenanceCount);
        viewTanks();
        viewMaintenanceTasks();
        viewSupplies();
    }

    void showSummary() {
        System.out.println("Total Tanks: " + tankCount);
        System.out.println("Total Supplies: " + supplyCount);
        System.out.println("Total Maintenance Tasks: " + maintenanceCount);
    }

    void showTanks() {
        if (tankCount == 0) {
            System.out.println("(No tanks found.)");
            return;
        }
        for (int i = 0; i < tankCount; i++) {
            System.out.println("Tank ID: " + tanks[i].getId());
            tanks[i].viewFish();
        }
    }

    void showAllMaintenanceTasks() {
        viewMaintenanceTasks();
    }

    void showAllSupplies() {
        if (supplyCount == 0) {
            System.out.println("(No supplies found.)");
            return;
        }
        for (int i = 0; i < supplyCount; i++) {
            supplies[i].viewSupply();
        }
    }

    public void addMaintenanceTask(Maintenance maintenance) {
        if (maintenance == null) {
            return;
        }
        if (maintenanceCount >= maintenanceTasks.length) {
            System.out.println("Cannot add more maintenance tasks.");
            return;
        }
        maintenanceTasks[maintenanceCount++] = maintenance;
        saveAllMaintenanceTasks();
        System.out.println("Maintenance task saved.");
    }

    public void viewMaintenanceTasks() {
        if (maintenanceCount == 0) {
            System.out.println("(No maintenance tasks found.)");
            return;
        }
        for (int i = 0; i < maintenanceCount; i++) {
            maintenanceTasks[i].viewTasks();
            if (i < maintenanceCount - 1) {
                System.out.println();
            }
        }
    }

    private boolean addOrMergeSupply(Supply supply, boolean persist) {
        if (supply == null) {
            return false;
        }

        Supply existingSupply = findSupply(supply.getName());
        if (existingSupply != null) {
            if (supply.getQuantity() > 0) {
                existingSupply.addQuantity(supply.getQuantity());
            }
            if (persist) {
                Supply.saveAllSupplies(supplies, supplyCount);
            }
            return true;
        }

        if (supplyCount >= supplies.length) {
            System.out.println("Cannot add more supplies.");
            return false;
        }

        supplies[supplyCount++] = supply;
        if (persist) {
            Supply.saveAllSupplies(supplies, supplyCount);
        }
        return true;
    }

    private void loadMaintenanceTasks() {
        File file = new File(MAINTENANCE_FILE);
        if (!file.exists()) {
            return;
        }

        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine() && maintenanceCount < maintenanceTasks.length) {
                String line = scanner.nextLine().trim();
                if (line.isEmpty()) {
                    continue;
                }
                try {
                    maintenanceTasks[maintenanceCount++] = Maintenance.fromFileString(line);
                } catch (RuntimeException e) {
                    System.out.println("Skipping invalid maintenance entry: " + line);
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Error loading maintenance tasks.");
        }
    }

    private void saveAllMaintenanceTasks() {
        try (PrintWriter writer = new PrintWriter(MAINTENANCE_FILE)) {
            for (int i = 0; i < maintenanceCount; i++) {
                if (maintenanceTasks[i] != null) {
                    writer.println(maintenanceTasks[i].toFileString());
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Error saving maintenance tasks.");
        }
    }
}
