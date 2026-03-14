package Fish_Hatchery_System;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class SystemViewer {

    public static void displayAll(Hatchery hatchery) {
        SystemViewer viewer = new SystemViewer();
        viewer.displayAllData(hatchery);
    }

    public void displayAllData(Hatchery hatchery) {
        System.out.println("\n========== SYSTEM OVERVIEW ==========");
        displayAdminSection();
        displayHatcherySummary(hatchery);
        displayTanks(hatchery);
        displayMaintenance(hatchery);
        displaySupplies(hatchery);
        displayFeedingLogs("feeding_log.txt");
        displayTransactions("buyer_transactions.txt");
        System.out.println("======================================\n");
    }

    private void displayAdminSection() {
        System.out.println("\n>> Admin Section:");
        System.out.println("(Login info already validated.)");
    }

    private void displayHatcherySummary(Hatchery hatchery) {
        System.out.println("\n>> Hatchery Summary:");
        hatchery.showSummary();
    }

    private void displayTanks(Hatchery hatchery) {
        System.out.println("\n>> Tanks Overview:");
        hatchery.showTanks();
    }

    private void displayMaintenance(Hatchery hatchery) {
        System.out.println("\n>> Maintenance Schedule:");
        hatchery.showAllMaintenanceTasks();
    }

    private void displaySupplies(Hatchery hatchery) {
        System.out.println("\n>> Supplies Overview:");
        hatchery.showAllSupplies();
    }

    private void displayFeedingLogs(String fileName) {
        System.out.println("\n>> Feeding Logs:");
        File file = new File(fileName);
        if (!file.exists()) {
            System.out.println("(No feeding logs found.)");
            return;
        }

        try (Scanner scanner = new Scanner(file)) {
            boolean found = false;
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine().trim();
                if (!line.isEmpty()) {
                    System.out.println("- " + line);
                    found = true;
                }
            }
            if (!found) {
                System.out.println("(No feeding entries.)");
            }
        } catch (FileNotFoundException e) {
            System.out.println("Error reading feeding logs.");
        }
    }

    private void displayTransactions(String fileName) {
        System.out.println("\n>> Buyer Transactions:");
        File file = new File(fileName);
        if (!file.exists()) {
            System.out.println("(No transactions recorded yet.)");
            return;
        }

        try (Scanner scanner = new Scanner(file)) {
            boolean found = false;
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine().trim();
                if (!line.isEmpty()) {
                    System.out.println("- " + line);
                    found = true;
                }
            }
            if (!found) {
                System.out.println("(No transaction entries.)");
            }
        } catch (FileNotFoundException e) {
            System.out.println("Error reading transaction logs.");
        }
    }
}
