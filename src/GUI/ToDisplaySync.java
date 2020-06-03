package GUI;

import Structure.ToDisplay;
import Structure.TypeOfAction;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.scene.image.ImageView;

import java.util.concurrent.ArrayBlockingQueue;

import static GUI.MainStage.board;
import static GUI.Move.*;

public class ToDisplaySync extends Service<ToDisplay> {

    private final ArrayBlockingQueue<ToDisplay> display;
    private static ToDisplay toDisplay;

    public ToDisplaySync(ArrayBlockingQueue<ToDisplay> display){
        this.display=display;

        setOnSucceeded(event -> {

            ToDisplay todisplay = getValue();

            if (todisplay.getTypeOfAction() == TypeOfAction.nothing) //kiedy kliknieto na puste pole
            {
                //return;
            } else if (todisplay.getTypeOfAction() == TypeOfAction.pick) //kiedy wybrano po raz pierwszy pionka
            {
                BoardInitialization.resetColors();
                int[] pickpawn = todisplay.getCoordinates().get(0);
                board[7 - (pickpawn[1])][pickpawn[0]].getGraphic().setEffect(light);

            } else if (todisplay.getTypeOfAction() == TypeOfAction.repick) //kiedy podjeto decyzje o wyborze innego pionka
            {
                BoardInitialization.resetColors();
                int[] pickedpawn = todisplay.getCoordinates().get(0);
                int[] newpawn = todisplay.getCoordinates().get(1);
                board[7 - (pickedpawn[1])][pickedpawn[0]].getGraphic().setEffect(null);
                board[7 - (newpawn[1])][newpawn[0]].getGraphic().setEffect(light);
            } else if (todisplay.getTypeOfAction() == TypeOfAction.clear) //kiedy zrezygnowano z wyboru pionka
            {
                int[] pickedpawn = todisplay.getCoordinates().get(0);
                board[7 - (pickedpawn[1])][pickedpawn[0]].getGraphic().setEffect(null);
            } else if (todisplay.getTypeOfAction() == TypeOfAction.move) //kiedy wybrano puste pole po wyborze pionka
            {
                ImageView oldgraphic;

                int[] oldplace = todisplay.getCoordinates().get(0);
                int[] newplace = todisplay.getCoordinates().get(1);
                oldgraphic = (ImageView) board[7 - (oldplace[1])][oldplace[0]].getGraphic();

                BoardInitialization.resetColors();
                board[7 - (oldplace[1])][oldplace[0]].getGraphic().setEffect(null);
                board[7 - (oldplace[1])][oldplace[0]].setGraphic(null);
                board[7 - (oldplace[1])][oldplace[0]].setStyle(GREEN_FIELD);
                board[7 - (newplace[1])][newplace[0]].setGraphic(oldgraphic);
                board[7 - (newplace[1])][newplace[0]].setStyle(GREEN_FIELD);

            } else if (todisplay.getTypeOfAction() == TypeOfAction.capture) //kiedy wybrano pole na ktorym znajduje sie pionek koloru przeciwnego
            {
                ImageView oldgraphic;

                int[] oldplace = todisplay.getCoordinates().get(0);
                int[] newplace = todisplay.getCoordinates().get(1);
                oldgraphic = (ImageView) board[7 - (oldplace[1])][oldplace[0]].getGraphic();

                BoardInitialization.resetColors();
                board[7 - (oldplace[1])][oldplace[0]].getGraphic().setEffect(null);
                board[7 - (oldplace[1])][oldplace[0]].setGraphic(null);
                board[7 - (oldplace[1])][oldplace[0]].setStyle(GREEN_FIELD);
                board[7 - (newplace[1])][newplace[0]].setGraphic(oldgraphic);
                board[7 - (newplace[1])][newplace[0]].setStyle(RED_FIELD);
            } else if (todisplay.getTypeOfAction() == TypeOfAction.enPassant) //bicie w przelocie
            {
                ImageView oldgraphic;
                int[] oldplace = todisplay.getCoordinates().get(0);
                int[] newplace = todisplay.getCoordinates().get(1);
                int[] cptrdpawn = todisplay.getCoordinates().get(2);
                oldgraphic = (ImageView) board[7 - (oldplace[1])][oldplace[0]].getGraphic();
                BoardInitialization.resetColors();
                board[7 - (oldplace[1])][oldplace[0]].getGraphic().setEffect(null);
                board[7 - (oldplace[1])][oldplace[0]].setGraphic(null);
                board[7 - (oldplace[1])][oldplace[0]].setStyle(GREEN_FIELD);
                board[7 - (newplace[1])][newplace[0]].setGraphic(oldgraphic);
                board[7 - (newplace[1])][newplace[0]].setStyle(GREEN_FIELD);
                board[7 - (cptrdpawn[1])][cptrdpawn[0]].setGraphic(null);
                board[7 - (cptrdpawn[1])][cptrdpawn[0]].setStyle(RED_FIELD);

            } else if (todisplay.getTypeOfAction() == TypeOfAction.castling) {
                ImageView king;
                ImageView rook;

                int[] oldking = todisplay.getCoordinates().get(0);
                int[] newking = todisplay.getCoordinates().get(1);
                int[] oldrook = todisplay.getCoordinates().get(2);
                int[] newrook = todisplay.getCoordinates().get(3);

                king = (ImageView) board[7 - (oldking[1])][oldking[0]].getGraphic(); //Przestawienie krola
                board[7 - (oldking[1])][oldking[0]].getGraphic().setEffect(null);
                board[7 - (oldking[1])][oldking[0]].setGraphic(null);
                board[7 - (oldking[1])][oldking[0]].setStyle(GREEN_FIELD);
                board[7 - (newking[1])][newking[0]].setGraphic(king);
                board[7 - (newking[1])][newking[0]].setStyle(GREEN_FIELD);


                rook = (ImageView) board[7 - (oldrook[1])][oldrook[0]].getGraphic(); //Przestawienie wiezy
                board[7 - (oldrook[1])][oldrook[0]].setGraphic(null);
                board[7 - (newrook[1])][newrook[0]].setGraphic(rook);
            }
            event.consume();
            reset();
            start();
        });
    }

    @Override
    protected Task<ToDisplay> createTask() {
        return new Task<>() {
            @Override
            protected ToDisplay call() throws Exception {
                toDisplay = display.take();
                System.out.println("Display sync dziala!");
                return toDisplay;
            }
        };
    }

    public void startService(Service<ToDisplay>serwis)
    {
        if(!serwis.isRunning())
        {
            serwis.reset();
            serwis.start();
        }
    }

}
