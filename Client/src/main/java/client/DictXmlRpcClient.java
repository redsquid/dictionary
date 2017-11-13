package client;

import client.handlers.Handlers;
import org.apache.xmlrpc.XmlRpcException;
import org.apache.xmlrpc.client.XmlRpcClient;
import org.apache.xmlrpc.client.XmlRpcClientConfigImpl;
import utils.ArgsLine;

import java.net.URL;


/**
 * <b>Dictionary XML-RPC Client</b>
 * <p>DictXmlRpcClient is infrastructure for messaging between dictionary client and server</p>
 *
 * @author squid
 */
public enum DictXmlRpcClient {
    INSTANCE;

    /**
     * <p>Execute command: processing arguments, creating instance of client, and sending command to server</p>
     * @param args arguments from input arguments line
     * @return command result
     */
    public String exec(ArgsLine args) throws XmlRpcException {
        XmlRpcClient client = create(args.getServerUrl());
        return Handlers.exec(args.getCommand(), args.getWords(), client);
    }

    /**
     * <p>Create XmlRpcClienr</p>
     * @param url url of dictionary server
     * @return instance of XmlRpcClient
     */
    private XmlRpcClient create(URL url) {
        XmlRpcClientConfigImpl config = new XmlRpcClientConfigImpl();
        config.setServerURL(url);
        XmlRpcClient client = new XmlRpcClient();
        client.setConfig(config);
        return client;
    }
}
