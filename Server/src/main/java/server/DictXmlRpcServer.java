package server;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.xmlrpc.XmlRpcException;
import org.apache.xmlrpc.server.PropertyHandlerMapping;
import org.apache.xmlrpc.server.XmlRpcServerConfigImpl;
import org.apache.xmlrpc.webserver.WebServer;

import java.io.IOException;

/**
 * <b>Dictionary XML-RPC Server</b>
 * <p>DictXmlRpcServer is infrastructure for messaging between dictionary client and server</p>
 *
 * @author squid
 */
public enum DictXmlRpcServer {
    INSTANCE;

    private final static String HANDLER_DICT = "dict";

    private static final Logger logger = LogManager.getLogger(DictXmlRpcServer.class);

    private WebServer webServer = null;

    /**
     * <p>Initializing and starting XML-RPC server</p>
     * @param port listening port
     */
    public void init(int port) {
        webServer = new WebServer(port);
        try {
            PropertyHandlerMapping phm = new PropertyHandlerMapping();
            phm.addHandler(HANDLER_DICT, Handler.class);
            webServer.getXmlRpcServer().setHandlerMapping(phm);
            XmlRpcServerConfigImpl serverConfig = (XmlRpcServerConfigImpl) webServer.getXmlRpcServer().getConfig();
            serverConfig.setEnabledForExtensions(true);
            serverConfig.setContentLengthOptional(false);
            webServer.start();
        } catch (XmlRpcException | IOException e) {
            logger.error("Server can't be initialized");
            throw new IllegalStateException("Server can't be initialized");
        }
    }

    /**
     * <p>Terminating XML-RPC server</p>
     */
    public void terminate() {
        if(null == webServer) {
            logger.error("DictXmlRpcServer is not initialized");
            return;
        }
        webServer.shutdown();
    }
}
