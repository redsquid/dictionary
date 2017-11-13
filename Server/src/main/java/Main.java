import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import server.DictXmlRpcServer;
import java.util.Scanner;

/**
 * <p>Dictionary server</p>
 *
 * @author squid
 */
public class Main {

    private static final Logger logger = LogManager.getLogger(Main.class);

    private Main() {}

    public static void main(String[] args) {
        try {
            logger.info("Starting server...");
            final int port = readPortFromArgs(args);
            logger.info("port: " + port);
            DictXmlRpcServer.INSTANCE.init(port);
            logger.info("Server is started");
        } catch (IllegalStateException e) {
            logger.fatal("Server can't be initialized", e);
            System.exit(-1);
        } catch (IllegalArgumentException e) {
            logger.fatal("Can't read arguments. Please specify listening port.", e);
            System.exit(-1);
        }

        Scanner scn = new Scanner(System.in);
        while(!"exit".equalsIgnoreCase(scn.next())) {}
        DictXmlRpcServer.INSTANCE.terminate();
    }

    private static int readPortFromArgs(String[] args)  {
        if(1 != args.length) throw new IllegalArgumentException("Wrong arguments line");
        try {
            return Integer.parseInt(args[0]);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Wrong argument");
        }
    }
}
