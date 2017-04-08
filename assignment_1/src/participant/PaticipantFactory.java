package participant;

import game.EventType;
import game.RandomUtil;

/**
 * The factory to generate participants.
 */
public class ParticipantFactory {

    // Participant id.
    private static int participantID = 1;

    // Names.
    private static final String NAMES[] = {
            "Abel", "Adam", "Alger", "Algernon", "Antonio", "Archer",
            "August", "Baird", "Beck", "Ben", "Bernard", "Bernie", "Bert",
            "Bob", "Burgess", "Burton", "Cash", "Christ", "Clement",
            "Cleveland", "Cliff", "Colbert", "Colin", "Corey", "Cornelius",
            "Craig", "Cyril", "Dana", "Dempsey", "Derrick", "Dominic",
            "Donald", "Duke", "Dunn", "Dwight", "Earl", "Edward", "Eli",
            "Ellis", "Eugene", "Evan", "Francis", "Frank", "Frederic",
            "Gavin", "George", "Gustave", "Hardy", "Harry", "Hugo", "Hunter",
            "Jim", "Jo", "Julian", "Keith", "Levi", "Lyle", "Lynn", "Magee",
            "Malcolm", "Marlon", "Matt", "Meredith", "Miles", "Morgan",
            "Mortimer", "Murphy", "Neil", "Oliver", "Perry", "Philip",
            "Quentin", "Reg", "Reginald", "Rod", "Rodney", "Rudolf", "Sandy",
            "Saxon", "Sebastian", "Solomon", "Spencer", "Ternence", "Thomas",
            "Tim", "Tobias", "Tyler", "Uriah", "Victor", "Webster",
            "Winfred", "Zachary" };

    // States.
    private static final String STATES[] = {
            "NSW", "QLD", "SA", "TAS", "VIC", "WA"
    };

    /**
     * Creates an athlete.
     *
     * @return the athlete created.
     */
    public static Athlete createAthlete() {
        switch (RandomUtil.generate(0, 3)) {
            case 0:
                return createAthlete(EventType.SWIMMING);
            case 1:
                return createAthlete(EventType.SPRINT);
            case 2:
                return createAthlete(EventType.CYCLING);
            default:
                return createAthlete(null);
        }
    }

    /**
     * Creates an athlete for the specified event type.
     *
     * @param event the event type.
     * @return the created athlete.
     */
    private static Athlete createAthlete(EventType event) {

        // Generate id, name, age and state.
        int id = participantID++;
        String name = NAMES[RandomUtil.generate(0, NAMES.length - 1)];
        int age = RandomUtil.generate(18, 50);
        String state = STATES[RandomUtil.generate(0, STATES.length - 1)];

        // Create a super athlete if no event type specified.
        if (event == null) {
            return new SuperAthlete(id, name, age, state);
        }

        // Create a athlete for the event type.
        switch (event) {
            case SWIMMING:
                return new Swimmer(id, name, age, state);
            case SPRINT:
                return new Sprinter(id, name, age, state);
            default:
                return new Cycling(id, name, age, state);
        }
    }


    /**
     * Creates an official.
     *
     * @return the created official.
     */
    public static Official createOfficial() {

        // Generate id, name, age and state.
        int id = participantID++;
        String name = NAMES[RandomUtil.generate(0, NAMES.length - 1)];
        int age = RandomUtil.generate(15, 35);
        String state = STATES[RandomUtil.generate(0, STATES.length - 1)];

        // Create an official.
        return new Official(id, name, age, state);
    }
}
