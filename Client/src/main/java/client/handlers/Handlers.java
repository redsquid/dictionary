package client.handlers;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.xmlrpc.XmlRpcException;
import org.apache.xmlrpc.client.XmlRpcClient;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * <b>Handlers</b>
 * <p>Enum for command processing</p>
 * <p>Each handler corresponds to a specific command</p>
 * <p>Supported command:</p>
 * <p>add - add word and it translations to dictionary</p>
 * <p>delete - delete word and it translations from dictionary</p>
 * <p>get - retrieve word translations from dictionary</p>
 *
 * @author squid
 */
public enum Handlers {

    ADD_HANDLER("add") {
        @Override
        public String exec(String word, String translations, XmlRpcClient client) throws XmlRpcException {
            if(translations.isEmpty()) throw new XmlRpcException("add - Wrong argument");
            client.execute("dict.add", Arrays.asList(word, translations));
            return "<значения слова успешно добавлены>";
        }
    },

    DELETE_HANDLER("delete") {
        @Override
        public String exec(String word, String translations, XmlRpcClient client) throws XmlRpcException{
            if(translations.isEmpty()) throw new XmlRpcException("delete - Wrong argument");
            Integer n = (Integer) client.execute("dict.delete", Arrays.asList(word, translations));
            return (n == 0) ? "<слово/значение отсутвует в словаре>" : "<значения слова успешно удалены>";
        }
    },

    GET_HANDLER("get") {
        @Override
        public String exec(String word, String translations, XmlRpcClient client) throws XmlRpcException {
            if(!translations.isEmpty()) throw new XmlRpcException("get - Wrong argument");
            String result = (String) client.execute("dict.get", Arrays.asList(word));
            return ("".equals(result)) ? "<слово отсутствует в словаре>" : result.replace(" ", "\n");
        }
    },

    ERROR_HANDLER("ERROR") {
        @Override
        public String exec(String word, String translations, XmlRpcClient client) throws XmlRpcException {
            return "No such command";
        }
    };

    private static final Logger logger = LogManager.getLogger(Handlers.class);

    private final String name;

    Handlers(String name) {
        this.name = name;
    }

    /**
     * <p>Execute command</p>
     * @param command command sent to the server
     * @param words first element - word, other elements is it translates
     * @param client instance of DictXmlRpcClient for interraction with dictionary server
     * @return command result
     */
    public static String exec(String command, List<String> words, XmlRpcClient client) throws XmlRpcException {
        logger.info(String.join(" ", "Command:", command));
        return getHandler(command).exec(words.get(0), String.join(" ", words.subList(1, words.size())), client);
    }

    protected abstract String exec(String word, String translations, XmlRpcClient client) throws XmlRpcException;

    private static Handlers getHandler(String name) {
        Optional<Handlers> opt = Arrays.asList(Handlers.values()).stream().filter(hndl -> hndl.name.equals(name)).findAny();
        return (opt.isPresent()) ? opt.get() : ERROR_HANDLER;
    }
}
