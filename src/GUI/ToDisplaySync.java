package GUI;

import Structure.ToDisplay;
import Structure.TypeOfAction;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.concurrent.ArrayBlockingQueue;

import static GUI.BoardInitialization.*;
import static GUI.MainStage.board;
import static GUI.Move.*;


public class ToDisplaySync extends Service<ToDisplay> {

    private final ArrayBlockingQueue<ToDisplay> display;
    private static ToDisplay toDisplay;

    public void startTheService(){
        if(!isRunning()){
            System.out.println("startuje");
            reset();
            start();
        }
        else
            System.out.println("nie startuje");
    }

    //Pobieranie danych ze struktury oraz obsługa ruchu
    public ToDisplaySync(ArrayBlockingQueue<ToDisplay> display){
        this.display=display;

        setOnSucceeded(new EventHandler<WorkerStateEvent>() {

            @Override
            public void handle(WorkerStateEvent event) {

                ToDisplay todisplay = getValue();

                if (todisplay.getTypeOfAction() == TypeOfAction.nothing) //kiedy kliknieto na puste pole
                {
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
                }
                else if (todisplay.getTypeOfAction() == TypeOfAction.clear) //kiedy zrezygnowano z wyboru pionka
                {
                    int[] pickedpawn = todisplay.getCoordinates().get(0);
                    board[7 - (pickedpawn[1])][pickedpawn[0]].getGraphic().setEffect(null);

                }
                else if (todisplay.getTypeOfAction() == TypeOfAction.move) //kiedy wybrano puste pole po wyborze pionka
                {
                    ImageView oldgraphic;

                    int[] oldplace = todisplay.getCoordinates().get(0);
                    int[] newplace = todisplay.getCoordinates().get(1);
                    oldgraphic = (ImageView) board[7 - (oldplace[1])][oldplace[0]].getGraphic();

                    BoardInitialization.resetColors();
                    board[7 - (oldplace[1])][oldplace[0]].getGraphic().setEffect(null);
                    board[7 - (oldplace[1])][oldplace[0]].setGraphic(null);
                    board[7 - (oldplace[1])][oldplace[0]].setStyle(YELLOW_FIELD);
                    board[7 - (newplace[1])][newplace[0]].setGraphic(oldgraphic);
                    board[7 - (newplace[1])][newplace[0]].setStyle(YELLOW_FIELD);

                } else if (todisplay.getTypeOfAction() == TypeOfAction.capture) //kiedy wybrano pole na ktorym znajduje sie pionek koloru przeciwnego
                {
                    ImageView oldgraphic;
                    ImageView graphicforshelf;
                    Boolean color;

                    int[] oldplace = todisplay.getCoordinates().get(0);
                    int[] newplace = todisplay.getCoordinates().get(1);
                    oldgraphic = (ImageView) board[7 - (oldplace[1])][oldplace[0]].getGraphic();
                    graphicforshelf=(ImageView) board[7 - (newplace[1])][newplace[0]].getGraphic();

                    BoardInitialization.resetColors();
                    board[7 - (oldplace[1])][oldplace[0]].getGraphic().setEffect(null);
                    board[7 - (oldplace[1])][oldplace[0]].setGraphic(null);
                    board[7 - (oldplace[1])][oldplace[0]].setStyle(YELLOW_FIELD);

                    color=pawnColor(graphicforshelf);
                    if(color) ShelvesForPawns.addWhitePawn(graphicforshelf);
                    else ShelvesForPawns.addBlackPawn(graphicforshelf);

                    board[7 - (newplace[1])][newplace[0]].setGraphic(oldgraphic);
                    board[7 - (newplace[1])][newplace[0]].setStyle(RED_FIELD);
                }
                else if (todisplay.getTypeOfAction() == TypeOfAction.enPassant) //bicie w przelocie
                {
                    Boolean color;
                    ImageView oldgraphic;
                    ImageView graphicforshelf;

                    int[] oldplace = todisplay.getCoordinates().get(0);
                    int[] newplace = todisplay.getCoordinates().get(1);
                    int[] cptrdpawn = todisplay.getCoordinates().get(2);

                    oldgraphic = (ImageView) board[7 - (oldplace[1])][oldplace[0]].getGraphic();
                    graphicforshelf=(ImageView) board[7 - (cptrdpawn[1])][cptrdpawn[0]].getGraphic();
                    BoardInitialization.resetColors();

                    board[7 - (oldplace[1])][oldplace[0]].getGraphic().setEffect(null);
                    board[7 - (oldplace[1])][oldplace[0]].setGraphic(null);
                    board[7 - (oldplace[1])][oldplace[0]].setStyle(YELLOW_FIELD);

                    color=pawnColor(graphicforshelf);
                    if(color) ShelvesForPawns.addWhitePawn(graphicforshelf);
                    else ShelvesForPawns.addBlackPawn(graphicforshelf);

                    board[7 - (newplace[1])][newplace[0]].setGraphic(oldgraphic);
                    board[7 - (newplace[1])][newplace[0]].setStyle(YELLOW_FIELD);
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
                    board[7 - (oldking[1])][oldking[0]].setStyle(YELLOW_FIELD);
                    board[7 - (newking[1])][newking[0]].setGraphic(king);
                    board[7 - (newking[1])][newking[0]].setStyle(YELLOW_FIELD);


                    rook = (ImageView) board[7 - (oldrook[1])][oldrook[0]].getGraphic(); //Przestawienie wiezy
                    board[7 - (oldrook[1])][oldrook[0]].setGraphic(null);
                    board[7 - (newrook[1])][newrook[0]].setGraphic(rook);
                }
                else if(todisplay.getTypeOfAction()==TypeOfAction.promotionToBishop || todisplay.getTypeOfAction()==TypeOfAction.promotionToKnight || todisplay.getTypeOfAction()==TypeOfAction.promotionToQueen || todisplay.getTypeOfAction()==TypeOfAction.promotionToRook)
                {
                    TypeOfAction type = todisplay.getTypeOfAction();
                    ImageView oldpawn;
                    ImageView newgraphic;
                    String color;

                    int[] oldplace = todisplay.getCoordinates().get(0);
                    int[] newplace = todisplay.getCoordinates().get(1);
                    oldpawn = (ImageView) board[7 - (oldplace[1])][oldplace[0]].getGraphic();

                    if(oldpawn.getImage()==PawnB) color="black";
                    else color="white";

                    if(type==TypeOfAction.promotionToKnight){
                        if(color=="white") newgraphic=new ImageView(KnightW);
                        else newgraphic=new ImageView(KnightB);
                    }
                    else if(type==TypeOfAction.promotionToBishop){
                        if(color=="white") newgraphic=new ImageView(BishopW);
                        else newgraphic=new ImageView(BishopB);
                    }
                    else if(type==TypeOfAction.promotionToQueen){
                        if(color=="white") newgraphic=new ImageView(QueenW);
                        else newgraphic=new ImageView(QueenB);
                    }
                    else {
                        if(color=="white") newgraphic=new ImageView(RookW);
                        else newgraphic=new ImageView(RookB);
                    }

                    BoardInitialization.resetColors();
                    board[7 - (oldplace[1])][oldplace[0]].getGraphic().setEffect(null);
                    board[7 - (oldplace[1])][oldplace[0]].setGraphic(null);
                    board[7 - (oldplace[1])][oldplace[0]].setStyle(YELLOW_FIELD);
                    board[7 - (newplace[1])][newplace[0]].setGraphic(newgraphic);
                    board[7 - (newplace[1])][newplace[0]].setStyle(YELLOW_FIELD);
                }
                else if(todisplay.getTypeOfAction()==TypeOfAction.promotionToBishopWithCapture || todisplay.getTypeOfAction()==TypeOfAction.promotionToKnightWithCapture || todisplay.getTypeOfAction()==TypeOfAction.promotionToQueenWithCapture || todisplay.getTypeOfAction()==TypeOfAction.promotionToRookWithCapture)
                {
                    TypeOfAction type = todisplay.getTypeOfAction();
                    ImageView oldpawn;
                    ImageView newgraphic;
                    ImageView cptrpawn;
                    Boolean color;

                    int[] oldplace = todisplay.getCoordinates().get(0);
                    int[] newplace = todisplay.getCoordinates().get(1);
                    oldpawn =(ImageView) board[7 - (oldplace[1])][oldplace[0]].getGraphic();
                    cptrpawn=(ImageView) board[7 - (newplace[1])][newplace[0]].getGraphic();

                    color=pawnColor(oldpawn);

                    if(color) ShelvesForPawns.addBlackPawn(cptrpawn);
                    else ShelvesForPawns.addWhitePawn(cptrpawn);

                    if(type==TypeOfAction.promotionToKnightWithCapture){
                        if(color) newgraphic=new ImageView(KnightW);
                        else newgraphic=new ImageView(KnightB);
                    }
                    else if(type==TypeOfAction.promotionToBishopWithCapture){
                        if(color) newgraphic=new ImageView(BishopW);
                        else newgraphic=new ImageView(BishopB);
                    }
                    else if(type==TypeOfAction.promotionToQueenWithCapture){
                        if(color) newgraphic=new ImageView(QueenW);
                        else newgraphic=new ImageView(QueenB);
                    }
                    else {
                        if(color) newgraphic=new ImageView(RookW);
                        else newgraphic=new ImageView(RookB);
                    }

                    BoardInitialization.resetColors();
                    board[7 - (oldplace[1])][oldplace[0]].getGraphic().setEffect(null);
                    board[7 - (oldplace[1])][oldplace[0]].setGraphic(null);
                    board[7 - (oldplace[1])][oldplace[0]].setStyle(YELLOW_FIELD);
                    board[7 - (newplace[1])][newplace[0]].setGraphic(newgraphic);
                    board[7 - (newplace[1])][newplace[0]].setStyle(RED_FIELD);
                }
                event.consume();
                reset();
                start();
            }
            });
    }

    @Override
    protected Task<ToDisplay> createTask() {
        return new Task<>() {
            protected ToDisplay call() {
                try {
                    toDisplay = display.take();
                }catch (InterruptedException e){
                    return null;
                }
                return toDisplay;
            }
        };
    }

}
