package game;

import participant.Athlete;

import java.util.Collections;
import java.util.List;
import java.util.Scanner;

/**
 * The driver of the the Ozlympic Game.
 */
public class Ozlympic {

    /**
     * Input scanner.
     */
    private static final Scanner INPUT = new Scanner(System.in);

    /**
     * Prompts the user to enter a number in the specified range.
     *
     * @param prompt the prompted message.
     * @param min    the lower bound of the number.
     * @param max    the upper bound of the number.
     * @return the number entered.
     */
    private static int input(String prompt, int min, int max) {
        System.out.print(prompt);
        try {
            String input = INPUT.next();
            System.out.println();
            int num = Integer.parseInt(input);
            if (num < min || num > max) {
                throw new GameException("Invalid input.");
            } else {
                return num;
            }
        } catch (NumberFormatException e) {
            throw new GameException("Invalid input.");
        }
    }

    /**
     * Displays the menu.
     */
    private static void showMenu() {
        System.out.print("Ozlympic Game\n" +
                "===================================\n" +
                "1. Select a game to run\n" +
                "2. Predict the winner of the game\n" +
                "3. Start the game\n" +
                "4. Display the final results of all games\n" +
                "5. Display the points of all athletes\n" +
                "6. Exit\n");
    }

    /**
     * Selects a game to run.
     *
     * @param data Game data.
     * @return the game selected.
     */
    private static Game selectGame(GameData data) {
        System.out.println("Select a game:");

        // List the games.
        int i;
        int numGames = data.getGames().size();
        for (i = 0; i < numGames; i++) {
            Game game = data.getGames().get(i);
            System.out.println("  " + (i + 1) + ". " + game);
        }
        System.out.println("  " + (i + 1) + ". Cancel");

        // Enter an options.
        int option = input("Enter an option: ", 1, numGames + 1);

        if (option == numGames + 1) {
            // Cancel selection.
            return null;

        } else {
            // Get the selected game.
            Game game = data.getGames().get(option - 1);

            if (game.isFinished()) {
                // Game is finished.
                throw new GameException("The game is finished.");

            } else {
                // Return the selected game.
                System.out.println(game + " is selected.");
                return game;
            }
        }
    }

    /**
     * Predict the winner of the game.
     *
     * @param game The game to predict.
     * @return the user's prediction.
     */
    private static Athlete predictGame(Game game) {
        // Validation.
        if (game == null) {
            throw new GameException("Select a new game first.");
        }
        if (game.isFinished()) {
            throw new GameException("The game is finished.");
        }

        // List the athletes.
        game.displayGame();

        // Select an athlete.
        int option = input("Who is the winner you predict (1 ~ "
                        + game.getAthletes().size() + "): ", 1,
                game.getAthletes().size());
        Athlete winner = game.getAthletes().get(option);
        System.out.println(winner.toShortString() + " is selected.");
        return winner;
    }

    /**
     * Starts the game.
     *
     * @param game            the current game.
     * @param predictedWinner the predicted winner.
     */
    private static void startGame(Game game, Athlete predictedWinner) {
        // Validation.
        if (game == null) {
            throw new GameException("Select a game first.");
        }
        if (game.isFinished()) {
            throw new GameException("The game is finished.");
        }

        // Start the game.
        game.startGame();

        // Check the prediction.
        if (predictedWinner != null) {
            if (game.isWinner(predictedWinner)) {
                System.out.println("Congratulation! You prediction is " +
                        "correct!");
            } else {
                System.out.println("Sorry, you prediction is incorrect!");
            }
        }
    }

    /**
     * Displays the final results of all games.
     *
     * @param games list of the games.
     */
    private static void displayGameResults(List<Game> games) {
        for (Game game : games) {
            game.displayGameResults();
        }
    }

    /**
     * Displays the points of all athletes
     *
     * @param athletes list of the athletes.
     */
    private static void displayAthletePoints(List<Athlete> athletes) {
        Collections.sort(athletes);
        System.out.printf("%-6s%-18s%-15s%-6s%-8s%-10s\n", "Rank", "Athlete",
                "Type", "Age", "State", "Points");
        for (int i = 0; i < athletes.size(); i++) {
            Athlete athlete = athletes.get(i);
            System.out.printf("%-6d%-18s%-15s%-6d%-8s%-10d\n", i + 1,
                    athlete.toShortString(),
                    athlete.getClass().getSimpleName(),athlete.getAge(),
                    athlete.getState(), athlete.getPoints());
        }
    }

    public static void main(String[] args) {
        GameData data = new GameData();
        boolean exit = false;
        Game currentGame = null;
        Athlete predictedWinner = null;
        while (!exit) {
            showMenu();
            try {
                switch (input("Enter an option: ", 1, 6)) {
                    case 1:
                        currentGame = selectGame(data);
                        break;
                    case 2:
                        predictedWinner = predictGame(currentGame);
                        break;
                    case 3:
                        startGame(currentGame, predictedWinner);
                        predictedWinner = null;
                        break;
                    case 4:
                        displayGameResults(data.getGames());
                        break;
                    case 5:
                        displayAthletePoints(data.getAthletes());
                        break;
                    case 6:
                        exit = true;
                        System.out.println("Bye!");
                        break;
                    default:
                        break;
                }

            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
            System.out.println();
        }
    }
}
