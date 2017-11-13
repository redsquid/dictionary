package dictionary;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.Multimaps;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import java.util.Arrays;

/**
 * <b>Dictionary</b>
 * <p>Dictionary stores words and their translations</p>
 *
 * @author squid
 */
public enum DictionaryImpl implements Dictionary {
    INSTANCE;

    private static final Logger logger = LogManager.getLogger(DictionaryImpl.class);

    private static final String DEVIDER = " ";

    private Multimap<String, String> storage = Multimaps.synchronizedSetMultimap(HashMultimap.create());

    public Integer insert(String word, String translations) {
        logger.debug("Add: " + word + " -> " + translations);
        return (int) Arrays.asList(translations.split(DEVIDER)).stream()
                .filter(str -> storage.put(word.toLowerCase(), str.toLowerCase())).count();
    }

    public String get(String word) {
        logger.debug("get: " + word);
        final String str = String.join(DEVIDER, storage.get(word.toLowerCase()));
        logger.debug("result: " + str);
        return str;
    }

    public Integer delete(String word, String translations) {
        logger.debug("Delete: " + word + " -> " + translations);
        return (int) Arrays.asList(translations.split(DEVIDER)).stream()
                .filter(str -> storage.remove(word.toLowerCase(), str.toLowerCase())).count();
    }

    public void clear() {
        storage.clear();
    }
}
