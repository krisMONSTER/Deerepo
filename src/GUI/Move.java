package GUI;

import NET.Game;
import Structure.*;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.effect.Lighting;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.util.Scanner;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Semaphore;


import static GUI.MainStage.board;

public class Move {

    static boolean is_picked; //Info dla EventHandlera czy przypadkiem nie wybrano juz jakiegos pionka
    static Label piece;
    static ImageView piece_image; //Ikona wybranego pionka
    static Lighting light = new Lighting();
    static String RED_FIELD="-fx-background-color:#bf4040  ;-fx-border-color: black; -fx-padding: 0";
    static String GREEN_FIELD="-fx-background-color:#196F3D ;-fx-border-color: black; -fx-padding: 0";
    static String YELLOW_FIELD="-fx-background-color:#DBD687;-fx-border-color: black; -fx-padding: 0";

    static Scanner in = new Scanner (System.in);
    private final ArrayBlockingQueue<int[]> clickCommand;
    private final ArrayBlockingQueue<ToDisplay> display;
    private final ArrayBlockingQueue<GameState> gameState;
    private static ToDisplay toDisplay;


    public Move(ArrayBlockingQueue<int[]> clickCommand, ArrayBlockingQueue<ToDisplay> display, ArrayBlockingQueue<GameState> gameState){
        this.clickCommand=clickCommand;
        this.display=display;
        this.gameState=gameState;
    }

    //Metoda obsluguje przesuwania pionkow po planszy
    public static void execute_move_mouse()
    {

        EventHandler<MouseEvent> eventHandler = e -> {
            int find_col;
            int find_row;

            //Okreslanie kolumny i wiersza w ktorym znajduje sie pionek, poprzez wspolrzedne miejsca klikniecia myszka
            find_col=(int)((e.getX()/50)+1);
            find_row=(int)((e.getY()/50)+1);


            //czy juz zostal wybrany pionek, jesli nie to zaznacza go
            if(is_picked==false) {
                if(board[find_row-1][find_col-1].getGraphic()==null) return;
                piece = board[find_row-1][find_col-1];
                piece_image = (ImageView) board[find_row-1][find_col-1].getGraphic();
                board[find_row-1][find_col-1].getGraphic().setEffect(light);
                is_picked = true;
            }
            //jesli zostal wybrany wczesniej pionek to przenosi go na nowe miejsce
            else{
                //MoveAnimation.move_animation(piece.getGraphic(),piece.getLayoutX(), piece.getLayoutY(),e.getX(),e.getY());
                piece.setGraphic(null);
                board[find_row-1][find_col-1].setGraphic(piece_image);
                board[find_row-1][find_col-1].getGraphic().setEffect(null);
                is_picked = false;

            }
        };

        MainStage.gridPane.addEventHandler(MouseEvent.MOUSE_CLICKED,eventHandler);
    }

    public void execute_move( StructureTaskOffline t, Semaphore clickSemaphore)
    {
        t.start();

        EventHandler<MouseEvent> eventHandler = e -> {
            int find_col;
            int find_row;

            try{

            if(clickSemaphore.tryAcquire()){
            //Okreslanie kolumny i wiersza w ktorym znajduje sie pionek, poprzez wspolrzedne miejsca klikniecia myszka
            find_col=(int)((e.getX()/50)+1);
            find_row=(int)((e.getY()/50)+1);

                GameState state = gameState.take();
                if (state == GameState.draw) {
                    AlertBox.display("Koniec gry", "Remis!");
                    MainStage.endGame();
                }

                else if (state == GameState.whiteWon) {
                    AlertBox.display("Koniec gry", "Biale wygraly!");
                    System.out.println("Biale wygraly!");
                    MainStage.endGame();
                }

                else if (state == GameState.blackWon) {
                    AlertBox.display("Koniec gry", "Czarne wygraly!");
                    MainStage.endGame();
                }
                clickCommand.put(new int[]{find_col-1,7-(find_row-1)});
                }
                clickSemaphore.release();

                toDisplay = display.take();
                    if(toDisplay.getTypeOfAction()==TypeOfAction.nothing) //kiedy kliknieto na puste pole
                    {
                        return;
                    }

                    else if(toDisplay.getTypeOfAction()==TypeOfAction.pick) //kiedy wybrano po raz pierwszy pionka
                    {
                        BoardInitialization.resetColors();
                        int [] pickpawn = toDisplay.getCoordinates().get(0);
                        board[7-(pickpawn[1])][pickpawn[0]].getGraphic().setEffect(light);

                    }

                    else if(toDisplay.getTypeOfAction()==TypeOfAction.repick) //kiedy podjeto decyzje o wyborze innego pionka
                    {
                        BoardInitialization.resetColors();
                        int [] pickedpawn = toDisplay.getCoordinates().get(0);
                        int [] newpawn = toDisplay.getCoordinates().get(1);
                        board[7-(pickedpawn[1])][pickedpawn[0]].getGraphic().setEffect(null);
                        board[7-(newpawn[1])][newpawn[0]].getGraphic().setEffect(light);
                    }

                    else if(toDisplay.getTypeOfAction()==TypeOfAction.clear) //kiedy zrezygnowano z wyboru pionka
                    {
                        int [] pickedpawn = toDisplay.getCoordinates().get(0);
                        board[7-(pickedpawn[1])][pickedpawn[0]].getGraphic().setEffect(null);
                    }

                    else if(toDisplay.getTypeOfAction()==TypeOfAction.move) //kiedy wybrano puste pole po wyborze pionka
                    {
                        ImageView oldgraphic;

                        int [] oldplace = toDisplay.getCoordinates().get(0);
                        int [] newplace = toDisplay.getCoordinates().get(1);
                        oldgraphic=(ImageView)board[7-(oldplace[1])][oldplace[0]].getGraphic();

                        BoardInitialization.resetColors();
                        board[7-(oldplace[1])][oldplace[0]].getGraphic().setEffect(null);
                        board[7-(oldplace[1])][oldplace[0]].setGraphic(null);
                        board[7-(oldplace[1])][oldplace[0]].setStyle(GREEN_FIELD);
                        board[7-(newplace[1])][newplace[0]].setGraphic(oldgraphic);
                        board[7-(newplace[1])][newplace[0]].setStyle(GREEN_FIELD);

                    }

                    else if(toDisplay.getTypeOfAction()==TypeOfAction.capture) //kiedy wybrano pole na ktorym znajduje sie pionek koloru przeciwnego
                    {
                        ImageView oldgraphic;

                        int [] oldplace = toDisplay.getCoordinates().get(0);
                        int [] newplace = toDisplay.getCoordinates().get(1);
                        oldgraphic=(ImageView)board[7-(oldplace[1])][oldplace[0]].getGraphic();

                        BoardInitialization.resetColors();
                        board[7-(oldplace[1])][oldplace[0]].getGraphic().setEffect(null);
                        board[7-(oldplace[1])][oldplace[0]].setGraphic(null);
                        board[7-(oldplace[1])][oldplace[0]].setStyle(GREEN_FIELD);
                        board[7-(newplace[1])][newplace[0]].setGraphic(oldgraphic);
                        board[7-(newplace[1])][newplace[0]].setStyle(RED_FIELD);
                    }

                    else if(toDisplay.getTypeOfAction()==TypeOfAction.enPassant) //bicie w przelocie
                    {
                        ImageView oldgraphic;
                        int [] oldplace = toDisplay.getCoordinates().get(0);
                        int [] newplace = toDisplay.getCoordinates().get(1);
                        int [] cptrdpawn = toDisplay.getCoordinates().get(2);
                        oldgraphic=(ImageView)board[7-(oldplace[1])][oldplace[0]].getGraphic();
                        BoardInitialization.resetColors();
                        board[7-(oldplace[1])][oldplace[0]].getGraphic().setEffect(null);
                        board[7-(oldplace[1])][oldplace[0]].setGraphic(null);
                        board[7-(oldplace[1])][oldplace[0]].setStyle(GREEN_FIELD);
                        board[7-(newplace[1])][newplace[0]].setGraphic(oldgraphic);
                        board[7-(newplace[1])][newplace[0]].setStyle(GREEN_FIELD);
                        board[7-(cptrdpawn[1])][cptrdpawn[0]].setGraphic(null);
                        board[7-(cptrdpawn[1])][cptrdpawn[0]].setStyle(RED_FIELD);

                    }

                    else if(toDisplay.getTypeOfAction()==TypeOfAction.castling)
                    {
                        ImageView king;
                        ImageView rook;

                        int [] oldking = toDisplay.getCoordinates().get(0);
                        int [] newking = toDisplay.getCoordinates().get(1);
                        int [] oldrook = toDisplay.getCoordinates().get(2);
                        int [] newrook = toDisplay.getCoordinates().get(3);

                        king=(ImageView)board[7-(oldking[1])][oldking[0]].getGraphic(); //Przestawienie krola
                        board[7-(oldking[1])][oldking[0]].getGraphic().setEffect(null);
                        board[7-(oldking[1])][oldking[0]].setGraphic(null);
                        board[7-(oldking[1])][oldking[0]].setStyle(GREEN_FIELD);
                        board[7-(newking[1])][newking[0]].setGraphic(king);
                        board[7-(newking[1])][newking[0]].setStyle(GREEN_FIELD);


                        rook=(ImageView)board[7-(oldrook[1])][oldrook[0]].getGraphic(); //Przestawienie wiezy
                        board[7-(oldrook[1])][oldrook[0]].setGraphic(null);
                        board[7-(newrook[1])][newrook[0]].setGraphic(rook);
                    }

            }catch (InterruptedException ex){
                ex.printStackTrace();
            }
        };

        MainStage.gridPane.addEventHandler(MouseEvent.MOUSE_CLICKED,eventHandler);
    }

    public void addCheckStateHandler() {

        CheckState check = new CheckState(gameState);
        check.start();
    }

    public void addProcessHandler()
    {
        ToDisplaySync process = new ToDisplaySync(display);
        process.start();
    }


    public void executeMove(Semaphore clickSemaphore, ArrayBlockingQueue<int[]> clickCommand, StructureTaskOffline t)
    {
        t.start();

        EventHandler<MouseEvent> eventHandler = e -> {
            if(clickSemaphore.tryAcquire()){
                int find_col;
                int find_row;
                find_col=(int)((e.getX()/50)+1);
                find_row=(int)((e.getY()/50)+1);
                System.out.println("pobieranie");
                try {
                    clickCommand.put(new int[]{find_col - 1, 7 - (find_row - 1)});
                }catch (InterruptedException exception){
                    exception.printStackTrace();
                }
                clickSemaphore.release();
            }
        };
        MainStage.gridPane.addEventHandler(MouseEvent.MOUSE_CLICKED,eventHandler);
    }
}


