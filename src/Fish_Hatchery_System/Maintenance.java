package Fish_Hatchery_System;

import java.time.LocalDate;

public class Maintenance {
    private String taskType;          
    private String schedule;            
    private LocalDate lastPerformedDate;
    private LocalDate nextDueDate;

    public Maintenance(String taskType, String schedule, LocalDate lastPerformedDate) {
        this.taskType = taskType;
        this.schedule = schedule;
        this.lastPerformedDate = lastPerformedDate;
        updateNextDueDate();
    }

    public Maintenance() {
        this.taskType = "General Maintenance";
        this.schedule = "Weekly";
        this.lastPerformedDate = LocalDate.now();
        updateNextDueDate();
    }

    public void updateNextDueDate() {
        if (schedule.equalsIgnoreCase("Weekly")) {
            nextDueDate = lastPerformedDate.plusWeeks(1);
        } else if (schedule.equalsIgnoreCase("Bi-weekly") || schedule.equalsIgnoreCase("Biweekly")) {
            nextDueDate = lastPerformedDate.plusWeeks(2);
        } else if (schedule.equalsIgnoreCase("Monthly")) {
            nextDueDate = lastPerformedDate.plusMonths(1);
        } else if (schedule.equalsIgnoreCase("Quarterly")) {
            nextDueDate = lastPerformedDate.plusMonths(3);
        } else {
            // default to weekly if schedule unrecognized
            nextDueDate = lastPerformedDate.plusWeeks(1);
        }
    }

    public void scheduleTask(String task) {
        scheduleTask(task, "Weekly");
    }

    public void scheduleTask(String task, String schedule) {
        this.taskType = task;
        this.schedule = schedule;
        this.lastPerformedDate = LocalDate.now();
        updateNextDueDate();
        System.out.println("Maintenance task scheduled: " + task + " (" + schedule + ")");
        System.out.println("Last performed on: " + lastPerformedDate);
        System.out.println("Next due on: " + nextDueDate);
    }

    public void viewTasks() {
        System.out.println("Task:             " + taskType);
        System.out.println("Schedule:         " + schedule);
        System.out.println("Last Performed:   " + lastPerformedDate);
        System.out.println("Next Due:         " + nextDueDate);
    }

    public String getTaskType() {
        return taskType;
    }

    public String getSchedule() {
        return schedule;
    }

    public LocalDate getLastPerformedDate() {
        return lastPerformedDate;
    }

    public LocalDate getNextDueDate() {
        return nextDueDate;
    }

    public void setTaskType(String taskType) {
        this.taskType = taskType;
    }

    public void setSchedule(String schedule) {
        this.schedule = schedule;
        updateNextDueDate();
    }

    public void setLastPerformedDate(LocalDate lastPerformedDate) {
        this.lastPerformedDate = lastPerformedDate;
        updateNextDueDate();
    }

    public String toFileString() {
        return taskType + "," + schedule + "," + lastPerformedDate;
    }

    public static Maintenance fromFileString(String line) {
        String[] parts = line.split(",", 3);
        if (parts.length != 3) {
            throw new IllegalArgumentException("Invalid maintenance data format.");
        }
        return new Maintenance(parts[0].trim(), parts[1].trim(), LocalDate.parse(parts[2].trim()));
    }
}
