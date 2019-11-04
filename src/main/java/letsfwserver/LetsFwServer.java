package letsfwserver;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;

public class LetsFwServer {
    private static final int LETS_FW_SERVER_INNER_PORT = 8081;
    private static final int SLEEP_FOR_DATA_RECEIVING = 1000;
    private static final int WAIT_WHEN_DATA_WILL_WRITE = 2000;

    public void start() {
        try {
            ServerSocket letsFwServerSocket = new ServerSocket(LETS_FW_SERVER_INNER_PORT);
            System.out.println("Lets forward server has been started");

            Socket letsFwClientSocket = letsFwServerSocket.accept();
            System.out.println("Connection accepted");

            DataOutputStream letsFwClientSocketOutputStream = new DataOutputStream(letsFwClientSocket.getOutputStream());
            ServerSocket httpServer = new ServerSocket(getLetsFwServerOuterPort());
            letsFwClientSocketOutputStream.writeUTF(String.valueOf(httpServer.getLocalPort()));

            while (true) {
                Socket browserClient = httpServer.accept();

                System.out.println("Browser has been connected");
                Thread.sleep(SLEEP_FOR_DATA_RECEIVING);

                InputStream browserClientInputStream = browserClient.getInputStream();
                byte[] bytesFromBrowserClient = browserClientInputStream.readNBytes(browserClientInputStream.available());
                System.out.println(new String(bytesFromBrowserClient));
                letsFwClientSocketOutputStream.write(bytesFromBrowserClient);

                Thread.sleep(WAIT_WHEN_DATA_WILL_WRITE);

                InputStream letsFwClientSocketInputStream = letsFwClientSocket.getInputStream();
                byte[] bytesFromLetsFwClientSocket = letsFwClientSocketInputStream.readNBytes(letsFwClientSocketInputStream.available());
                System.out.println(new String(bytesFromLetsFwClientSocket));

                OutputStream browserClientOutputStream = browserClient.getOutputStream();
                browserClientOutputStream.write(bytesFromLetsFwClientSocket);

                browserClientOutputStream.flush();
                browserClientOutputStream.close();

                browserClient.close();
            }
        } catch (InterruptedException | IOException e) {
            System.out.println(e.getMessage());
        }
    }

    private int getLetsFwServerOuterPort() {
        return new Random().nextInt(1000) + 8000;
    }
}
