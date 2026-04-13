import java.util.*;

class Habit {
    String name;
    int streak;
    boolean completedToday;

    Habit(String name) {
        this.name = name;
        this.streak = 0;
        this.completedToday = false;
    }

    void markDone() {
        if (!completedToday) {
            streak++;
            completedToday = true;
        }
    }
}

public class HabitTracker {
    static ArrayList<Habit> habits = new ArrayList<>();
    static Scanner sc = new Scanner(System.in);
    static final String FILE_NAME = "habits.txt";

    public static void main(String[] args) {
        loadFromFile();
        System.out.println("🌟 Welcome to Habit Tracker 🌟");

        while (true) {
            System.out.println("\n--- Habit Tracker ---");
            System.out.println("1. Add Habit");
            System.out.println("2. Mark Habit as Done");
            System.out.println("3. View Habits");
            System.out.println("4. Exit");
            System.out.println("5. Delete Habit");
            System.out.println("6. Reset Day (all habits)");

            int choice = sc.nextInt();
            sc.nextLine(); // clear buffer

            switch (choice) {
                case 1:
                    addHabit();
                    break;
                case 2:
                    markHabit();
                    break;
                case 3:
                    viewHabits();
                    break;
                case 4:
                    System.exit(0);
                case 5:
                    deleteHabit();
                    break;
                case 6:
                    resetDay();
                    break;
                default:
                    System.out.println("Invalid choice!");
            }
        }
    }

    static void addHabit() {
        System.out.print("Enter habit name: ");
        String name = sc.nextLine();
        habits.add(new Habit(name));
        System.out.println("✅ Habit added!");
        saveToFile();
    }

    static void markHabit() {
        viewHabits();
        System.out.print("Select habit number: ");
        int index = sc.nextInt() - 1;

        if (index >= 0 && index < habits.size()) {
            habits.get(index).markDone();
            System.out.println("🔥 Habit completed! Current streak: " + habits.get(index).streak);
            saveToFile();
        } else {
            System.out.println("Invalid selection.");
        }
    }

    static void viewHabits() {
        if (habits.isEmpty()) {
            System.out.println("No habits added.");
            return;
        }

        for (int i = 0; i < habits.size(); i++) {
            Habit h = habits.get(i);
            System.out.println((i + 1) + ". " + h.name +
                    " | Streak: " + h.streak +
                    " | Done Today: " + (h.completedToday ? "Yes" : "No"));
        }
    }

    static void deleteHabit() {
        viewHabits();
        if (habits.isEmpty()) return;

        System.out.print("Enter habit number to delete: ");
        int index = sc.nextInt() - 1;

        if (index >= 0 && index < habits.size()) {
            System.out.println("🗑️ Deleted: " + habits.get(index).name);
            habits.remove(index);
            saveToFile();
        } else {
            System.out.println("Invalid selection.");
        }
    }

    static void resetDay() {
        if (habits.isEmpty()) {
            System.out.println("No habits to reset.");
            return;
        }

        for (Habit h : habits) {
            h.completedToday = false;
        }

        saveToFile();

        System.out.println("🔄 All habits reset for a new day!");
        
        System.out.println("🌅 New day started. Stay consistent!");
    }

    static void saveToFile() {
        try {
            java.io.PrintWriter writer = new java.io.PrintWriter(FILE_NAME);

            for (Habit h : habits) {
                writer.println(h.name + "," + h.streak + "," + h.completedToday);
            }

            writer.close();
        } catch (Exception e) {
            System.out.println("Error saving data.");
        }
    }

    static void loadFromFile() {
        try {
            java.io.File file = new java.io.File(FILE_NAME);
            if (!file.exists()) return;

            Scanner fileScanner = new Scanner(file);

            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine();
                String[] parts = line.split(",");

                Habit h = new Habit(parts[0]);
                h.streak = Integer.parseInt(parts[1]);
                h.completedToday = Boolean.parseBoolean(parts[2]);

                habits.add(h);
            }

            fileScanner.close();
        } catch (Exception e) {
            System.out.println("Error loading data.");
        }
    }
}