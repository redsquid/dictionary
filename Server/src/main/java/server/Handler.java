package server;

import dictionary.Dictionary;
import dictionary.DictionaryImpl;

/**
 * <b>Dictionary XML-RPC Server handler</b>
 * <p>Service class for interaction between XML-RPC Server and Dictionary</p>
 *
 * @author squid
 */
public class Handler {

    private Dictionary dict = DictionaryImpl.INSTANCE;

    public Integer add(String word, String translations) {
        return dict.insert(word, translations);
    }

    public String get(String word) {
        return dict.get(word);
    }

    public Integer delete(String word, String translations) {
        return dict.delete(word, translations);
    }
}
