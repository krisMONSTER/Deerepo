package GUI;

import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.effect.Lighting;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;

import java.util.Scanner;

public class Move {

    boolean is_picked; //Info dla EventHandlera czy przypadkiem nie wybrano juz jakiegos pionka
    Label piece;
    ImageView piece_image; //Ikona wybranego pionka
    Lighting light = new Lighting();
    Label[][] board;
    GridPane Board;
    Scanner in = new Scanner (System.in);

   public Move(ChessBoard chessBoard){

       this.board=chessBoard.getboard();
       this.Board=chessBoard.getBoard();

   }

    //Metoda obsluguje przesuwania pionkow po planszy
    public void execute_move_mouse()
    {

        EventHandler<MouseEvent> eventHandler = e -> {
            int find_col;
            int find_row;

            //Okreslanie kolumny i wiersza w ktorym znajduje sie pionek, poprzez wspolrzedne miejsca klikniecia myszka
            find_col=(int)((e.getX()/50)+1);
            find_row=(int)((e.getY()/50+1));

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
                piece.setGraphic(null);
                board[find_row-1][find_col-1].setGraphic(piece_image);
                board[find_row-1][find_col-1].getGraphic().setEffect(null);
                is_picked = false;

            }
        };

        Board.addEventHandler(MouseEvent.MOUSE_CLICKED,eventHandler);
    }

    //Metoda, ktora pozwala podawac wspolrzedne pionka oraz jego nowa pozycje poprzez konsole
    public void execute_move_console()
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

        Board.addEventHandler(MouseEvent.MOUSE_CLICKED, eventHandler);

    }


    //Metoda, ktora przesuwa pionek ze zrodla do celu poprzez podanie parametrow do funkcji
    public void execute_move_on_data_changes(int col, int row, int newcol, int newrow)
    {

        piece_image =(ImageView) board[7-row][col].getGraphic();
        board[7-row][col].setGraphic(null);
        board[7-newrow][newcol].setGraphic(piece_image);

    }
}


