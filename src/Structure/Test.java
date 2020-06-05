package Structure;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.TimeUnit;

public class Test {
    private static ServerSocket ss;
    private static Socket s;

    public static void main(String[] args) {
        ObjectOutputStream os;
        ObjectInputStream is = null;
        DataChanges dataChanges = new DataChanges();
        ToDisplay toDisplay = new ToDisplay();
        DataPackage dp = new DataPackage(dataChanges, toDisplay);
        try {
            ss = new ServerSocket(7172);
            s = ss.accept();
            os = new ObjectOutputStream(s.getOutputStream());
            is = new ObjectInputStream(s.getInputStream());
            TimeUnit.MILLISECONDS.sleep(1000);
            //os.writeObject(dp);
        }catch (IOException e){
            System.out.println("nie udalo sie polaczyc");
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        try{
            System.out.println("zamykanie");
            ss.close();
            s.close();
        }catch (IOException e){
            System.out.println("wyjatek przy zamykaniu");
        }
        try{
            System.out.println("zamykanie");
            ss.close();
            s.close();
        }catch (IOException e){
            System.out.println("wyjatek przy zamykaniu");
        }
    }
}
