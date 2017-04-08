package game;

/**
 * Game Exception.
 */
@SuppressWarnings("serial")
public class GameException extends IllegalStateException {

    /**
     * Constructor.
     *
     * @param message Exception message.
     */
    public GameException(String message) {
        super(message);
    }
}
