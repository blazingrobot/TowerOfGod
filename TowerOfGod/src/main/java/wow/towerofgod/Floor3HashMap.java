package wow.towerofgod;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Random;

public class Floor3HashMap {
    private static final Map<String, String> catalystProperties = new HashMap<>();
    private static final String[] allProperties = {"Heat", "Water", "Wind", "Gravity", "Light", "Storm", "Mist", "Void"};
    private static final Random random = new Random();
    //Game storage, this level gamit tag Hashmap
    
    //Player stats
    private static int energy = 100;
    private static int correctAnswers = 0;
    private static final int ANSWERS_NEEDED = 3;

    //game data
    static {
        catalystProperties.put("Crimson Core", "Heat");
        catalystProperties.put("Azure Gem", "Water");
        catalystProperties.put("Verdant Shard", "Wind");
        catalystProperties.put("Obsidian Fragment", "Gravity");
        catalystProperties.put("Golden Prism", "Light");
        catalystProperties.put("Violet Crystal", "Storm");
        catalystProperties.put("Silver Orb", "Mist");
        catalystProperties.put("Ebony Stone", "Void");
    }
    
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        
        // Game introduction
        System.out.println("=== Shinsoo Resonance Chamber ===");
        System.out.println("Match each catalyst to its property.");
        System.out.println("Get 3 correct answers to unlock the puzzle!");
        
        // Main game loop
        while (energy > 0) {
            playRound(scan);
            
            // Check if player can attempt the puzzle
            if (correctAnswers >= ANSWERS_NEEDED) {
                if (solvePuzzle(scan)) {
                    System.out.println("\nYou passed the test!");
                    break; // Exit game loop on success
                }
            }
        }
        
        scan.close();
    }
    
    private static void playRound(Scanner scanner) {
        String catalyst = getRandomCatalyst();
        String correctAnswer = catalystProperties.get(catalyst); // Get random catalyst and its correct property
        
        String[] choices = generateChoices(correctAnswer); // Generate multiple-choice options
        
        System.out.println("\nEnergy: " + energy + "% | Correct: " + correctAnswers + "/" + ANSWERS_NEEDED);
        System.out.println("Catalyst: " + catalyst);
        
        for (int i = 0; i < choices.length; i++) {
            System.out.println((i+1) + ") " + choices[i]); // Show choices
        }
        
        System.out.print("Your choice (1-4): ");
        int playerChoice = scanner.nextInt();
        scanner.nextLine(); 
        
        //check answer
        if (playerChoice >= 1 && playerChoice <= 4 && 
            choices[playerChoice-1].equalsIgnoreCase(correctAnswer)) {
            correctAnswers++;
            energy = Math.min(100, energy + 10);
            System.out.println("Correct! +10 Energy");
        } else {
            int damage = 15 + random.nextInt(10);
            energy -= damage;
            System.out.println("Wrong! -" + damage + " Energy");
            System.out.println("Correct answer was: " + correctAnswer);
            
            if (energy <= 0) {
                System.out.println("\nYou ran out of energy! Game Over.");
            }
        }
    }
    
    private static String[] generateChoices(String correctAnswer) {
        String[] options = new String[4];
        options[0] = correctAnswer; 
        
        for (int i = 1; i < 4; i++) {
            String wrongAnswer;
            do {
                wrongAnswer = allProperties[random.nextInt(allProperties.length)];
            } while (arrayContains(options, wrongAnswer));
            options[i] = wrongAnswer;
        }
        
        // Shuffle options
        for (int i = 0; i < options.length; i++) {
            int swapWith = random.nextInt(options.length);
            String temp = options[i];
            options[i] = options[swapWith];
            options[swapWith] = temp;
        }
        
        return options;
    }
    
    private static boolean solvePuzzle(Scanner scanner) {
        System.out.println("\n=== Final Puzzle ===");
        System.out.println("Put these in order:");
        System.out.println("1) Heat\n2) Water\n3) Wind\n4) Gravity");
        System.out.print("Enter the correct order (e.g. 1,2,3,4): ");
        
        String answer = scanner.nextLine().replace(" ", "");
        boolean correct = answer.equalsIgnoreCase("1,2,3,4");
        
        if (correct) {
            return true;
        } else {
            System.out.println("Wrong order! Try again after more correct answers.");
            correctAnswers = 0; // Reset progress
            return false;
        }
    }
    
    private static boolean arrayContains(String[] array, String value) {
        for (String item : array) {
            if (item != null && item.equalsIgnoreCase(value)) {
                return true;
            }
        }
        return false;
    }
    
    private static String getRandomCatalyst() {
        Object[] catalysts = catalystProperties.keySet().toArray();
        return (String) catalysts[random.nextInt(catalysts.length)];
    }
}
