package dictionary;

/**
 * <b>Dictionary</b>
 *
 * @author squid
 */
public interface Dictionary {

    /**
     * <p>Add word and it translations to dictionary</p>
     * @param word translated word
     * @param translations words separated by spaces
     * @return words number added to the dictionary
     */
    Integer insert(String word, String translations);

    /**
     * <p>Retrieve word translations from dictionary</p>
     * @param word word for retrieve its translations
     * @return translation for the given word
     */
    String get(String word);

    /**
     * <p>Delete word and it translations from dictionary</p>
     * @param word translated word
     * @param translations words separated by spaces
     * @return words number deleted to the dictionary
     */
    Integer delete(String word, String translations);

    void clear();
}
