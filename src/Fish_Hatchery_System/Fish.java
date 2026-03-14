package Fish_Hatchery_System;

public class Fish {
    private String species;               
    private int age;                  
    private double weight;               

    public Fish(String species, int age, double weight) {
        if (age < 0) {
            throw new IllegalArgumentException("Age cannot be negative.");
        }
        if (weight < 0) {
            throw new IllegalArgumentException("Weight cannot be negative.");
        }
        this.species = species;
        this.age = age;
        this.weight = weight;
    }

    public String getSpecies() {
        return species;
    }

    public int getAge() {
        return age;
    }

    public double getWeight() {
        return weight;
    }

    public void feed() {                 
        weight += 0.1;
        System.out.println(species + " has been fed. New weight: " + weight + " kg");
    }

    public String toFileString() {                        
        return species + "," + age + "," + weight;
    }

    public static Fish fromFileString(String line) {        
        try {
            String[] parts = line.split(",");              
            if (parts.length != 3) {
                throw new IllegalArgumentException("Invalid fish data format.");
            }
            String species = parts[0];                          
            int age = Integer.parseInt(parts[1]);        
            double weight = Double.parseDouble(parts[2]);      
            return new Fish(species, age, weight);
        } catch (Exception e) {
            System.out.println("Error parsing fish data: " + e.getMessage());
            return null;
        }
    }

    @Override
    public String toString() {               
        return "Fish{" +
               "species='" + species + '\'' +
               ", age=" + age + " months" +
               ", weight=" + weight + " kg" +
               '}';
    }
}

