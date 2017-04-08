package game;

import participant.Athlete;
import participant.ParticipantFactory;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Game data collection.
 */
public class GameData {

    // Maximum number of athletes.
    private static final int NUM_ATHLETES = 25;

    // Number of games.
    private static final int NUM_GAMES = 5;

    // List of athletes.
    private List<Athlete> athletes;

    // List of games.
    private List<Game> games;

    /**
     * Constructor.
     */
    public GameData() {

        athletes = new ArrayList<>();
        games = new ArrayList<>();
        Set<Athlete> participants = new HashSet<>();

        // Create athletes.
        for (int i = 0; i < NUM_ATHLETES; i++) {
            athletes.add(ParticipantFactory.createAthlete());
        }

        // Create games.
        for (int i = 0; i < NUM_GAMES; i++) {

            // Create a game of a random event.
            Game game = new Game(EventType.random());

            // Select the number of athletes for the game.
            int numAthletes = RandomUtil.generate(Game.MIN_ATHLETES, Game
                    .MAX_ATHLETES);

            // Pick athletes who can play the event.
            Set<Athlete> athleteSet = new HashSet<>();
            while (athleteSet.size() < numAthletes) {
                Athlete athlete = athletes.get(RandomUtil.generate(0,
                        athletes.size() - 1));
                if (athlete.canPlay(game.getEvent())) {
                    athleteSet.add(athlete);
                }
            }
            game.addAthletes(athleteSet);
            participants.addAll(athleteSet);
            game.setReferee(ParticipantFactory.createOfficial());
            games.add(game);
        }

        // Retain the participated athletes.
        athletes = new ArrayList<>(participants);
    }

    /**
     * @return the list of athletes.
     */
    public List<Athlete> getAthletes() {
        return athletes;
    }

    /**
     * @return the list of games.
     */
    public List<Game> getGames() {
        return games;
    }
}
