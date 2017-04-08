package game;

import participant.Athlete;
import participant.Official;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Ozlympic game.
 */
public class Game {

    // Minimum number of athletes in a game.
    public static final int MIN_ATHLETES = 4;

    // Maximum number of athletes in a game.
    public static final int MAX_ATHLETES = 8;

    // Game id.
    private static int GAME_ID = 1;

    // Points awarded to the top 3 winners.
    private static final int[] POINTS = {5, 3, 1};

    // id of the game.
    private final String id;

    // event of the game.
    private final EventType event;

    // athletes of the game.
    private final List<Athlete> athletes;

    // if the game is finished.
    private boolean finished = false;

    // referee of the game.
    private Official referee;

    // times of the athletes.
    private int[] times;

    // ranks of the athletes.
    private int[] ranks;

    /**
     * Constructor.
     *
     * @param event event of the game.
     */
    public Game(EventType event) {
        this.id = String.format("%c%02d", event.getSymbol(), GAME_ID++);
        this.event = event;
        this.athletes = new ArrayList<>();
        this.referee = null;
        this.finished = false;
    }

    /**
     * Adds athletes to the game.
     *
     * @param newAthletes the list of athletes to be added.
     */
    public void addAthletes(Collection<Athlete> newAthletes) {
        if (athletes.size() + newAthletes.size() > MAX_ATHLETES) {
            throw new GameException("No more than " + MAX_ATHLETES + " " +
                    "athletes in a game");
        }
        athletes.addAll(newAthletes);
    }

    /**
     * Sets referee to the game.
     *
     * @param referee the referee to be set.
     */
    public void setReferee(Official referee) {
        if (referee == null) {
            throw new GameException("A referee has already be assigned.");
        }
        this.referee = referee;
    }

    /**
     * Starts the game.
     */
    public void startGame() {

        // Validate the game.
        if (referee == null) {
            throw new GameException("No referee in the game");
        }
        if (athletes.size() < MIN_ATHLETES) {
            throw new GameException("No more than " + MIN_ATHLETES + " " +
                    "athletes, the game is canceled.");
        }

        System.out.println(this + " started...");

        times = new int[athletes.size()];
        ranks = new int[athletes.size()];

        // Generate time for each athlete.
        for (int i = 0; i < athletes.size(); i++) {
            times[i] = athletes.get(i).compete(event);
            System.out.println("  Time of " + athletes.get(i).toShortString() +
                    ": " + times[i] + "(s)");
            ranks[i] = 1;
        }

        // Calculate rank for each athlete.
        for (int i = 0; i < athletes.size(); i++) {
            for (int j = 0; j < athletes.size(); j++) {
                if (times[i] > times[j]) {
                    ranks[i]++;
                }
            }
        }

        // Add points to the top 3 winners.
        for (int i = 0; i < athletes.size(); i++) {
            if (ranks[i] <= POINTS.length) {
                athletes.get(i).addPoints(POINTS[ranks[i] - 1]);
            }
        }

        // Sort the athletes by their times.
        for (int i = 0; i < athletes.size(); i++) {
            for (int j = i + 1; j < athletes.size(); j++) {
                if (times[i] > times[j]) {
                    Athlete temp = athletes.get(i);
                    athletes.set(i, athletes.get(j));
                    athletes.set(j, temp);

                    int tempRank = ranks[i];
                    ranks[i] = ranks[j];
                    ranks[j] = tempRank;

                    int tempTime = times[i];
                    times[i] = times[j];
                    times[j] = tempTime;
                }
            }
        }

        finished = true;
        System.out.println(this);

        // Display the winner of this game.
        System.out.print("Winner is");
        for (int i = 0; i < athletes.size(); i++) {
            if (ranks[i] == 1) {
                System.out.print(" " + athletes.get(i).toShortString());
            }
        }
        System.out.println(".");
    }

    /**
     * @return the athletes in the game.
     */
    public List<Athlete> getAthletes() {
        return athletes;
    }

    /**
     * @return a string represents the game.
     */
    @Override
    public String toString() {
        return id + ": " + event.toString().toLowerCase()
                + " (" + athletes.size() + " athletes)"
                + (finished ? " (FINISHED)" : "");
    }

    /**
     * Displays the information of the game.
     */
    public void displayGame() {
        System.out.println("Game ID  : " + id);
        System.out.println("Event    : " + event.toString().toLowerCase());
        System.out.println("Referee  : "
                + (referee == null ? "" : referee.toShortString()));
        System.out.println("Athletes :");
        for (int i = 0; i < athletes.size(); i++) {
            System.out.println("  " + (i + 1) + ". "
                    + athletes.get(i).toShortString());
        }
        System.out.println();
    }

    /**
     * Displays the results of the game.
     */
    public void displayGameResults() {
        System.out.println("Game ID  : " + id);
        System.out.println("Event    : " + event.toString().toLowerCase());
        System.out.println("Referee  : "
                + (referee == null ? "" : referee.toShortString()));
        if (finished) {
            String pattern = "%-8s%-18s%-10s%-6s";
            System.out.println(String.format("Result   : " + pattern,
                    "Rank", "Athlete", "Time(s)", "Score"));
            for (int i = 0; i < athletes.size(); i++) {
                System.out.println(String.format("           " + pattern, ranks[i],
                        athletes.get(i).toShortString(), times[i],
                        ranks[i] <= POINTS.length ? POINTS[ranks[i] - 1] : 0));
            }
            System.out.println();
        } else {
            System.out.println("Result   : Not started.");
            System.out.println();
        }
    }

    /**
     * @return true if the game is finished or false otherwise.
     */
    public boolean isFinished() {
        return finished;
    }

    /**
     * @return the event of the game.
     */
    public EventType getEvent() {
        return event;
    }

    /**
     * Determines whether the athlete is the winner of this game.
     *
     * @param athlete the input athlete.
     * @return true if he is the winner or false otherwise.
     */
    public boolean isWinner(Athlete athlete) {
        for (int i = 0; i < athletes.size(); i++) {
            if (athletes.get(i) == athlete && ranks[i] == 1) {
                return true;
            }
        }
        return false;
    }
}
