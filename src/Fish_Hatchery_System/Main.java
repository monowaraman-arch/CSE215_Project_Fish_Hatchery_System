package Fish_Hatchery_System;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Hatchery hatchery = new Hatchery();

        System.out.println("===============================");
        System.out.println("   FISH HATCHERY SYSTEM LOGIN");
        System.out.println("===============================");
        System.out.print("Username: ");
        String username = sc.nextLine();
        System.out.print("Password: ");
        String password = sc.nextLine();


        Admin currentAdmin = Admin.authenticate(username, password);
        if (currentAdmin == null) {
            System.out.println("Access Denied. Exiting.");
            sc.close();
            return;
        }
        System.out.println("Login successful. Welcome, " + currentAdmin.getName() + "!");

        int choice;
        do {
            System.out.println("\n-------------------------------");
            System.out.println("         MAIN MENU");
            System.out.println("-------------------------------");
            System.out.println("1. View Tanks");
            System.out.println("2. Add New Tank");
            System.out.println("3. Add Fish to Tank");
            System.out.println("4. Feed Fish");
            System.out.println("5. Transfer Fish Between Tanks");
            System.out.println("6. Schedule/View Maintenance");
            System.out.println("7. Manage Supplies");
            System.out.println("8. Generate Report");
            System.out.println("9. View All System Data");            
            System.out.println("10. Record Buyer Transaction");
            System.out.println("11. Save All Data");             
            System.out.println("12. Logout / Exit");                 
            System.out.print("Enter choice: ");
            choice = readInt(sc);

            switch (choice) {
                case 1:
                    hatchery.viewTanks();
                    break;

                case 2:
                    System.out.print("Enter Tank ID: ");
                    int id = readInt(sc);
                    System.out.print("Enter Capacity: ");
                    int cap = readPositiveInt(sc, "Capacity must be greater than zero.");
                    try {
                        Tank t = new Tank(id, cap);
                        hatchery.addTank(t);
                    } catch (IllegalArgumentException e) {
                        System.out.println(e.getMessage());
                    }
                    break;

                case 3:
                    System.out.print("Enter Tank ID to add fish: ");
                    int tankId = readInt(sc);
                    Tank selectedTank = hatchery.getTankById(tankId);
                    if (selectedTank == null) {
                        System.out.println("Tank not found.");
                        break;
                    }
                    System.out.print("Enter Fish Species: ");
                    String species = readRequiredText(sc, "Fish species cannot be empty.");
                    System.out.print("Enter Age: ");
                    int age = readNonNegativeInt(sc, "Age cannot be negative.");
                    System.out.print("Enter Weight: ");
                    double weight = readNonNegativeDouble(sc, "Weight cannot be negative.");
                    try {
                        Fish f = new Fish(species, age, weight);
                        if (selectedTank.addFish(f)) {
                            selectedTank.saveToFile();
                        }
                    } catch (IllegalArgumentException e) {
                        System.out.println(e.getMessage());
                    }
                    break;

                case 4:
                    System.out.print("Enter Tank ID to feed fish: ");
                    int feedId = readInt(sc);
                    Tank feedTank = hatchery.getTankById(feedId);
                    if (feedTank != null) {
                        if (feedTank.feedFish() > 0) {
                            feedTank.saveToFile();
                        }
                    } else {
                        System.out.println("Tank not found.");
                    }
                    break;

                case 5:
                    System.out.print("Source Tank ID: ");
                    int source = readInt(sc);
                    System.out.print("Destination Tank ID: ");
                    int dest = readInt(sc);
                    Tank sTank = hatchery.getTankById(source);
                    Tank dTank = hatchery.getTankById(dest);
                    if (sTank != null && dTank != null) {
                        int movedCount = sTank.transferFishTo(dTank);
                        if (movedCount > 0) {
                            sTank.saveToFile();
                            dTank.saveToFile();
                            System.out.println(movedCount + " fish transferred.");
                        }
                    } else {
                        System.out.println("Invalid tank(s).");
                    }
                    break;

                case 6:
                    System.out.print("Enter maintenance task: ");
                    String taskName = readRequiredText(sc, "Maintenance task cannot be empty.");
                    System.out.print("Enter schedule (Weekly/Bi-weekly/Monthly/Quarterly): ");
                    String schedule = readRequiredText(sc, "Schedule cannot be empty.");
                    Maintenance m = new Maintenance();
                    m.scheduleTask(taskName, schedule);
                    hatchery.addMaintenanceTask(m);
                    hatchery.viewMaintenanceTasks();
                    break;

                case 7:
                    System.out.print("Enter supply name: ");
                    String name = readRequiredText(sc, "Supply name cannot be empty.");
                    System.out.print("Enter quantity: ");
                    int qty = readPositiveInt(sc, "Quantity must be greater than zero.");
                    System.out.print("Enter unit: ");
                    String unit = readRequiredText(sc, "Supply unit cannot be empty.");
                    try {
                        Supply supply = new Supply(name, qty, unit);
                        hatchery.addSupply(supply);
                    } catch (IllegalArgumentException e) {
                        System.out.println(e.getMessage());
                    }
                    break;

                case 8:
                    hatchery.generateReport();
                    break;

                case 9:
                    SystemViewer.displayAll(hatchery);
                    break;

                case 10:
                    System.out.print("Buyer Name: ");
                    String buyer = readRequiredText(sc, "Buyer name cannot be empty.");
                    System.out.print("Fish Species: ");
                    String fishType = readRequiredText(sc, "Fish species cannot be empty.");
                    System.out.print("Quantity (pieces): ");
                    int qtyBought = readPositiveInt(sc, "Quantity must be greater than zero.");
                    try {
                        BuyerTransaction bt = new BuyerTransaction(buyer, fishType, qtyBought);
                        bt.saveToFile();
                        System.out.println("Transaction recorded.");
                    } catch (IllegalArgumentException e) {
                        System.out.println(e.getMessage());
                    }
                    break;

                case 11:
                    hatchery.saveAllData();
                    System.out.println("All data saved.");
                    break;
                    
                case 12:
                    Admin.logout();
                    break;

                default:
                    System.out.println("Invalid choice.");
            }

        } while (choice != 12);

        sc.close();
    }

    private static int readInt(Scanner sc) {
        while (true) {
            String input = sc.nextLine().trim();
            try {
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.print("Enter a valid integer: ");
            }
        }
    }

    private static int readPositiveInt(Scanner sc, String errorMessage) {
        while (true) {
            int value = readInt(sc);
            if (value > 0) {
                return value;
            }
            System.out.print(errorMessage + " Try again: ");
        }
    }

    private static int readNonNegativeInt(Scanner sc, String errorMessage) {
        while (true) {
            int value = readInt(sc);
            if (value >= 0) {
                return value;
            }
            System.out.print(errorMessage + " Try again: ");
        }
    }

    private static double readNonNegativeDouble(Scanner sc, String errorMessage) {
        while (true) {
            String input = sc.nextLine().trim();
            try {
                double value = Double.parseDouble(input);
                if (value >= 0) {
                    return value;
                }
            } catch (NumberFormatException e) {
                // fall through to message
            }
            System.out.print(errorMessage + " Try again: ");
        }
    }

    private static String readRequiredText(Scanner sc, String errorMessage) {
        while (true) {
            String input = sc.nextLine().trim();
            if (!input.isEmpty()) {
                return input;
            }
            System.out.print(errorMessage + " Try again: ");
        }
    }
}
