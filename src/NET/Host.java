package NET;

import Structure.DataPackage;

import java.io.IOException;
import java.io.InterruptedIOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;

public class Host {

    private final int port;
    private ServerSocket ss;
    private Socket s;
    private ObjectInputStream is;
    private ObjectOutputStream os;

    public Host() {
        port = 7172;
    }

    public Host(int port) {
        this.port = port;
    }

    public void setupSocketServer() throws IOException{
        ss = new ServerSocket(port);
        ss.setSoTimeout(2000);
    }

    public void setupSocket() throws IOException{
        s = ss.accept();
        s.setSoTimeout(2000);
        os = new ObjectOutputStream(s.getOutputStream());
        is = new ObjectInputStream(s.getInputStream());
    }

    public void send(DataPackage dataPackage) throws IOException{
        os.writeObject(dataPackage);
    }

    public DataPackage receive() throws IOException,ClassNotFoundException{
        return (DataPackage)is.readObject();
    }

    public void close() throws IOException{
        s.close();
        ss.close();
    }

    public void closeSocketServer() throws IOException{
        ss.close();
    }

    /*void start() throws IOException {

        try {
            oq = new ArrayBlockingQueue<>(1);
            iq = new ArrayBlockingQueue<>(1);

            ss = new ServerSocket(port);
            System.out.println("Oczekiwanie na gracza...");
            s = ss.accept();
            System.out.println("Gracz dolaczyl do sesji");

            ObjectOutputStream os = new ObjectOutputStream(s.getOutputStream());
            ObjectInputStream is = new ObjectInputStream(s.getInputStream());

            DataChanges dc;

            while (true) {

                dc = oq.take();
                System.out.println("client send: " + dc);
                // Send
                os.writeObject(dc);

                // Receive
                dc = (DataChanges)is.readObject();
                System.out.println("client received: " + dc);
                iq.add(dc);

            }
        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            s.close();
            ss.close();
        }
    }*/


}



