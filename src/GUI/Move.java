package GUI;

import Structure.*;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.effect.Lighting;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.util.Scanner;
import java.util.concurrent.ArrayBlockingQueue;


import static GUI.MainStage.board;

public class Move {

    static boolean is_picked; //Info dla EventHandlera czy przypadkiem nie wybrano juz jakiegos pionka
    static Label piece;
    static ImageView piece_image; //Ikona wybranego pionka
    static String previous_field_color;
    static Lighting light = new Lighting();

    static Scanner in = new Scanner (System.in);
    private final ArrayBlockingQueue<int[]> clickCommand;
    private final ArrayBlockingQueue<ToDisplay> display;
    private static ToDisplay toDisplay;


    public Move(ArrayBlockingQueue<int[]> clickCommand, ArrayBlockingQueue<ToDisplay> display){
        this.clickCommand=clickCommand;
        this.display=display;
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

    //Metoda, ktora pozwala podawac wspolrzedne pionka oraz jego nowa pozycje poprzez konsole
    public static void execute_move_console()
    {

        EventHandler<MouseEvent> eventHandler = e -> {
            System.out.println("Podaj kolumne");
            int col=in.nextInt();
            System.out.println("Podaj wiersz");
            int row=in.nextInt();
            System.out.println("Podaj nowa kolumne");
            int newcol = in.nextInt();
            System.out.println("Podaj nowy wiersz");
            int newrow = in.nextInt();
            piece_image = (ImageView) board[7 - row][col].getGraphic();
            board[7 - row][col].setGraphic(null);
            board[7 - newrow][newcol].setGraphic(piece_image);
        };

        MainStage.gridPane.addEventHandler(MouseEvent.MOUSE_CLICKED, eventHandler);

    }


    //Metoda, ktora przesuwa pionek ze zrodla do celu poprzez podanie parametrow do funkcji
    public void execute_move_on_data_changes(int col, int row, int newcol, int newrow)
    {

        piece_image =(ImageView) board[7-row][col].getGraphic();
        board[7-row][col].setGraphic(null);
        board[7-newrow][newcol].setGraphic(piece_image);

    }

    public void execute_move( StructureTaskOffline t)
    {
        t.start();

        EventHandler<MouseEvent> eventHandler = e -> {
            int find_col;
            int find_row;

            //Okreslanie kolumny i wiersza w ktorym znajduje sie pionek, poprzez wspolrzedne miejsca klikniecia myszka
            find_col=(int)((e.getX()/50)+1);
            find_row=(int)((e.getY()/50)+1);


            //czy juz zostal wybrany pionek, jesli nie to zaznacza go
            try {
                    clickCommand.put(new int[]{find_col-1,7-(find_row-1)});
                    toDisplay = display.take();

                    if(toDisplay.getTypeOfAction()==TypeOfAction.nothing) //kiedy kliknieto na puste pole
                    {
                        return;
                    }
                    else if(toDisplay.getTypeOfAction()==TypeOfAction.pick) //kiedy wybrano po raz pierwszy pionka
                    {
                        piece = board[find_row - 1][find_col - 1];
                        piece_image = (ImageView) board[find_row - 1][find_col - 1].getGraphic();
                        board[find_row - 1][find_col - 1].getGraphic().setEffect(light);
                    }
                    else if(toDisplay.getTypeOfAction()==TypeOfAction.repick) //kiedy podjeto decyzje o wyborze innego pionka
                    {
                        piece.getGraphic().setEffect(null);
                        piece=board[find_row - 1][find_col - 1];
                        piece_image=(ImageView) board[find_row - 1][find_col - 1].getGraphic();
                        board[find_row - 1][find_col - 1].getGraphic().setEffect(light);
                    }
                    else if(toDisplay.getTypeOfAction()==TypeOfAction.clear) //kiedy zrezygnowano z wyboru pionka
                    {
                        piece=null;
                        piece_image=null;
                        board[find_row - 1][find_col - 1].getGraphic().setEffect(null);
                    }
                    else if(toDisplay.getTypeOfAction()==TypeOfAction.move) //kiedy wybrano puste pole po wyborze pionka
                    {
                        piece.setGraphic(null);
                        board[find_row-1][find_col-1].setGraphic(piece_image);
                        board[find_row-1][find_col-1].getGraphic().setEffect(null);
                    }
                    else if(toDisplay.getTypeOfAction()==TypeOfAction.capture) //kiedy wybrano pole na ktorym znajduje sie pionek koloru przeciwnego
                    {
                        piece.setGraphic(null);
                        board[find_row-1][find_col-1].setGraphic(piece_image);
                        board[find_row-1][find_col-1].getGraphic().setEffect(null);
                    }
                    else if(toDisplay.getTypeOfAction()==TypeOfAction.enPassant) //bicie w przelocie
                    {
                        piece.setGraphic(null);
                        board[find_row-1][find_col-1].setGraphic(piece_image);
                        board[find_row-1][find_col-1].getGraphic().setEffect(null);
                        int [] cptrpawn = toDisplay.getCoordinates().get(1);
                        board[7-(cptrpawn[1]+1)][cptrpawn[0]].setGraphic(null);
                    }
                    else if(toDisplay.getTypeOfAction()==TypeOfAction.castling)
                    {
                        ImageView rook;
                        piece.setGraphic(null);
                        board[find_row-1][find_col-1].setGraphic(piece_image);
                        board[find_row-1][find_col-1].getGraphic().setEffect(null);
                        int [] oldrook = toDisplay.getCoordinates().get(2);
                        rook=(ImageView)board[7-(oldrook[1])][oldrook[0]].getGraphic();
                        System.out.println(oldrook);
                        board[7-(oldrook[1])][oldrook[0]].setGraphic(null);
                        int [] newrook = toDisplay.getCoordinates().get(3);
                        board[7-(newrook[1])][newrook[0]].setGraphic(rook);
                        System.out.println(newrook);
                    }

            }catch (InterruptedException ex){
                ex.printStackTrace();
            }
        };

        MainStage.gridPane.addEventHandler(MouseEvent.MOUSE_CLICKED,eventHandler);
    }

}


