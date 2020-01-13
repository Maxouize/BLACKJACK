package epsi.blackjack;

import java.security.SecureRandom;

/**
 * Class to help writing test
 */
public class HelpTest {

    private static final SecureRandom random = new SecureRandom();

    /*
    * Generate random enum value from the given enum class
    */
    public static <T extends Enum<?>> T randomEnum(Class<T> clazz) {
        int x = random.nextInt(clazz.getEnumConstants().length);
        return clazz.getEnumConstants()[x];
    }

    /*
    *   Create and return a random card
    */
    public static Card createCard() {
        return new Card(randomEnum(Suit.class),randomEnum(Value.class));
    }
}
