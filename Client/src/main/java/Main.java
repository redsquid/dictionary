
import client.DictXmlRpcClient;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.xmlrpc.XmlRpcException;
import utils.ArgsLine;

import java.io.PrintWriter;
import java.util.MissingFormatArgumentException;


/**
 * <b>Dictionary client</b>
 *
 * <p>Client interracts with dictionary server</p>
 *
 * <p>Supported command:</p>
 * <p>add - add word and it translations to dictionary</p>
 * <p>delete - delete word and it translations from dictionary</p>
 * <p>get - retrieve word translations from dictionary</p>
 *
 * <p>Input argument line format: serverUrl:String port:int command:String word:String [translates:String]</p>
 * <p>For example: localhost 8000 add hello привет здравствуйте алло</p>
 *
 * @author squid
 */
public class Main {

    private static final Logger logger = LogManager.getLogger(Main.class);

    private Main() {}

    public static void main(String[] args) {
        logger.info("Client started");
        try {
            String result = DictXmlRpcClient.INSTANCE.exec(ArgsLine.parse(args));
            new PrintWriter(System.out).append(result).append("\n").flush();
        } catch (MissingFormatArgumentException | XmlRpcException e) {
            logger.fatal("Can't execute the command: "+ e.getMessage() + "\n\n", e);
        }
        logger.info("Client finished");
    }
}
