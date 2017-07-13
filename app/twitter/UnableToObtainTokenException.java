package twitter;

/**
 * Created by g.gerasimov on 03.05.2017.
 */
public class UnableToObtainTokenException extends RuntimeException {

    private static final UnableToObtainTokenException INSTANCE;

    static {
        try {
            INSTANCE = new UnableToObtainTokenException();
        } catch (Exception e) {
            throw new ExceptionInInitializerError(e);
        }
    }

    private UnableToObtainTokenException() {
        super("Can't obtain token");
    }

    public static UnableToObtainTokenException getInstance() {
        return INSTANCE;
    }
}
