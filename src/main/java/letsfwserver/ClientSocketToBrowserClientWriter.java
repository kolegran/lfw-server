package letsfwserver;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ClientSocketToBrowserClientWriter extends Thread {
    private static final int IF_NOTHING_HAS_BEEN_READ_YET = 10;
    private static final Logger logger = Logger.getLogger(ClientSocketToBrowserClientWriter.class.getName());
    private final Socket clientSocket;
    private final Socket browserClient;

    public ClientSocketToBrowserClientWriter(Socket clientSocket, Socket browserClient) {
        this.clientSocket = clientSocket;
        this.browserClient = browserClient;
    }

    @Override
    public void run() {
        try {
            while (!clientSocket.isClosed() && !browserClient.isClosed()) {
                final InputStream clientSocketInputStream = clientSocket.getInputStream();
                while (clientSocketInputStream.available() == 0) {
                    Thread.sleep(IF_NOTHING_HAS_BEEN_READ_YET);
                }
                final byte[] clientSocketBytes = clientSocketInputStream.readNBytes(clientSocketInputStream.available());

                final OutputStream browserClientOutputStream = browserClient.getOutputStream();
                browserClientOutputStream.write(clientSocketBytes);

                // TODO: 2020/17/6 flush & close browserClientOutputStream
            }
        } catch (Exception e) {
            logger.log(Level.WARNING, "Something went wrong in the " + currentThread().getName());
        }
    }
}
