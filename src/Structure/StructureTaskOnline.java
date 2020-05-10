package Structure;


import java.util.concurrent.ArrayBlockingQueue;

public class StructureTaskOnline extends Thread{
    private final ArrayBlockingQueue<int[]> clickOnBoard;
    private final Player player;

    public StructureTaskOnline(ArrayBlockingQueue<int[]> clickOnBoard, Player player){
        this.clickOnBoard = clickOnBoard;
        this.player = player;
    }

    public void run(){

    }
}
