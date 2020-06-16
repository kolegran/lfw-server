package letsfwserver;

import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LetsForwardServer {
    private static final Logger logger = Logger.getLogger(LetsForwardServer.class.getName());
    private static final int LETS_FW_SERVER_INNER_PORT = 8081;

    public void start() {
        try {
            final ServerSocket serverSocket = new ServerSocket(LETS_FW_SERVER_INNER_PORT);
            logger.log(Level.INFO, "Lets Forward Server has been started");

            while (!serverSocket.isClosed()) {
                final Socket clientSocket = serverSocket.accept();
                System.out.println(clientSocket.getRemoteSocketAddress());
                logger.log(Level.INFO, "Connection accepted");

                final int httpPort = getLetsForwardServerOuterPort();
                DataOutputStream dataOutputStream = new DataOutputStream(clientSocket.getOutputStream());
                dataOutputStream.writeUTF(String.valueOf(httpPort));

                final Socket browserClient = new ServerSocket(httpPort).accept();
                logger.log(Level.INFO, "Browser has been connected");

                final BrowserClientToClientSocketWriter browserClientToClientSocketWriter = new BrowserClientToClientSocketWriter(clientSocket, browserClient);
                browserClientToClientSocketWriter.setName("BrowserToClientSocketWriter thread");
                browserClientToClientSocketWriter.start();
                logger.log(Level.INFO, "BrowserToClientSocketWriter thread started");

                final ClientSocketToBrowserClientWriter clientSocketToBrowserClientWriter = new ClientSocketToBrowserClientWriter(clientSocket, browserClient);
                clientSocketToBrowserClientWriter.setName("ClientSocketToBrowserClientWriter thread");
                clientSocketToBrowserClientWriter.start();
                logger.log(Level.INFO, "ClientSocketToBrowserClientWriter thread started");
            }

        } catch (Exception e) {
            logger.log(Level.WARNING, e.getMessage());
        }
    }

    private int getLetsForwardServerOuterPort() {
        return new Random().nextInt(1000) + 8000;
    }
}
