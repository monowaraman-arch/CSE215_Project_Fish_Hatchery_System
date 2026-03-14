# Fish Hatchery System

Console-based Java application for managing fish tanks, supplies, maintenance records, feeding logs, and buyer transactions.

## Features

- Admin login with a simple username/password check
- Add and view tanks
- Add fish to tanks
- Feed fish and persist feeding logs
- Transfer fish between tanks without losing untransferred stock
- Create and view maintenance tasks
- Manage supplies with duplicate-name merging
- Record buyer transactions
- Generate a summary report
- View all persisted system data from one menu

## Project Structure

- `src/Fish_Hatchery_System/`: application source code
- `tanks/`: saved tank data files
- `supplies.txt`: persisted supply inventory
- `buyer_transactions.txt`: saved buyer transaction history
- `feeding_log.txt`: feeding activity log
- `maintenance.txt`: persisted maintenance records
- `build.xml`: Ant build entry point
- `nbproject/`: NetBeans project metadata

## Default Login

The current admin credentials are hard-coded in `src/Fish_Hatchery_System/Admin.java`.

- Username: `aman`
- Password: `a232`
- Username: `shobuz`
- Password: `s231`

Change them in code if you do not want credentials stored directly in source.

## Requirements

- Java 23
- Apache Ant if you want to build/run from the command line
- NetBeans 24 or another IDE that can open an Ant-based Java project

The project is configured for Java 23 in `nbproject/project.properties`.

## Run

### NetBeans

1. Open the project folder in NetBeans.
2. Set the project JDK to Java 23.
3. Run the project.

### Command line with Ant

```powershell
ant clean
ant run
```

## Sample I/O

```text
===============================
   FISH HATCHERY SYSTEM LOGIN
===============================
Username: aman
Password: a232
Login successful. Welcome, Monowar Hossain Aman!

-------------------------------
         MAIN MENU
-------------------------------
1. View Tanks
2. Add New Tank
3. Add Fish to Tank
4. Feed Fish
5. Transfer Fish Between Tanks
6. Schedule/View Maintenance
7. Manage Supplies
8. Generate Report
9. View All System Data
10. Record Buyer Transaction
11. Save All Data
12. Logout / Exit
Enter choice: 2
Enter Tank ID: 6
Enter Capacity: 25
Tank added and saved.

Enter choice: 3
Enter Tank ID to add fish: 6
Enter Fish Species: Rui
Enter Age: 4
Enter Weight: 1.2

Enter choice: 10
Buyer Name: Karim
Fish Species: Rui
Quantity (pieces): 3
Transaction recorded.

Enter choice: 12
Successfully logged out!
```

## Data Persistence

This application stores its data in plain text files.

- Tanks are stored as `tank_<id>.txt`
- Supplies are stored in `supplies.txt`
- Maintenance tasks are stored in `maintenance.txt`
- Feeding history is stored in `feeding_log.txt`
- Buyer transactions are stored in `buyer_transactions.txt`

If these files are deleted, the related data will be recreated as new records during runtime.

## Notes

- This is a console application, not a GUI application.
- There are currently no automated tests under `test/`.
- Login credentials are static and intended for simple local use.
