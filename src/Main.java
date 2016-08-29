import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(8091);
        while (true) {
            Socket client = serverSocket.accept();
            System.out.println("New connection");
            Socket target = new Socket("192.168.99.100", 8091);
            tunnel(client.getInputStream(), target.getOutputStream());
            tunnel(target.getInputStream(), client.getOutputStream());
        }
    }

    private static void tunnel(InputStream source, OutputStream target) {
        new Thread(() -> {
            try {
                while (true) {
                    target.write(source.read());
                }
            } catch (IOException e) {
            }
        }).start();
    }
}
