package Structure;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.concurrent.TimeUnit;

public class Test2 {
    private static Socket s;

    private static DataPackage receive(ObjectInputStream is) throws IOException, ClassNotFoundException{
        return (DataPackage)is.readObject();
    }

    public static void main(String[] args) {
        ObjectOutputStream os = null;
        ObjectInputStream is = null;
        DataPackage dp = null;
        try {
            s = new Socket("localhost", 7172);
            os = new ObjectOutputStream(s.getOutputStream());
            is = new ObjectInputStream(s.getInputStream());
            TimeUnit.MILLISECONDS.sleep(1000);
            dp = receive(is);
            System.out.println("koniec");
        }catch (IOException e){
            System.out.println("nie udalo sie polaczyc");
        }catch (ClassNotFoundException e){
            System.out.println("nie znaleziono struktury");
            e.printStackTrace();
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        if(dp == null){
            System.out.println("null");
        }else{
            System.out.println("not null");
        }
        //spr
    }
}
