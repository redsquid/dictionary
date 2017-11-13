package utils;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.MissingFormatArgumentException;

/**
 * <b>Argument line parser</b>
 * <p>ArgsLine extracts arguments from input argument line</p>
 *
 * @author squid
 */
public class ArgsLine {

    private static final int POS_SRV_NAME = 0;
    private static final int POS_PORT = 1;
    private static final int POS_COMMAND = 2;
    private static final int POS_WORD = 3;
    private static final int MIN_ARGS_NUMBER = 4;

    private static final String ARGS_FORMAT = "serverUrl:String port:int command:String word:String [translates:String]";

    private final URL serverUrl;

    private final String command;

    private final List<String> words;

    private ArgsLine(URL serverUrl, String command, List<String> words) {
        this.serverUrl = serverUrl;
        this.command = command;
        this.words = words;
    }

    /**
     * <p>Extract and validation arguments from input argument line</p>
     * @param str argument line
     * @return instance of ArgsLine with arguments
     */
    public static ArgsLine parse(String[] str) {
        if(str.length < MIN_ARGS_NUMBER) throw new MissingFormatArgumentException("Wrong args line format format: " + ARGS_FORMAT);
        try {
            return new ArgsLine(
                    new URL(String.join("", "http://", str[POS_SRV_NAME], ":", str[POS_PORT], "/xmlrpc")),
                    str[POS_COMMAND],
                    Arrays.asList(Arrays.copyOfRange(str, POS_WORD, str.length)));
        } catch (MalformedURLException | NumberFormatException e) {
            MissingFormatArgumentException ex = new MissingFormatArgumentException(
                    String.join(" ", "Wrong argument line", e.getMessage(), "format:", ARGS_FORMAT));
            ex.initCause(e);
            throw ex;
        }
    }

    public URL getServerUrl() {
        return serverUrl;
    }

    public String getCommand() {
        return command;
    }

    public List<String> getWords() {
        return words;
    }
}
