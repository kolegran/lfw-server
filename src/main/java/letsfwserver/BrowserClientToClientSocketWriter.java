package letsfwserver;

import java.io.DataOutputStream;
import java.io.InputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BrowserClientToClientSocketWriter extends Thread {
    private static final int IF_NOTHING_HAS_BEEN_READ_YET = 10;
    private static final Logger logger = Logger.getLogger(BrowserClientToClientSocketWriter.class.getName());
    private final Socket browserClient;
    private final Socket clientSocket;

    public BrowserClientToClientSocketWriter(Socket clientSocket, Socket browserClient) {
        this.browserClient = browserClient;
        this.clientSocket = clientSocket;
    }

    @Override
    public void run() {
        try {
            while (!clientSocket.isClosed() && !browserClient.isClosed()) {
                final InputStream browserClientInputStream = browserClient.getInputStream();
                while (browserClientInputStream.available() == 0) {
                    Thread.sleep(IF_NOTHING_HAS_BEEN_READ_YET);
                }

                final DataOutputStream clientSocketOutputStream = new DataOutputStream(clientSocket.getOutputStream());
                final byte[] browserClientBytes = browserClientInputStream.readNBytes(browserClientInputStream.available());
                clientSocketOutputStream.write(browserClientBytes);
            }
        } catch (Exception e) {
            logger.log(Level.WARNING, "Something went wrong in the " + currentThread().getName());
        }
    }
}
