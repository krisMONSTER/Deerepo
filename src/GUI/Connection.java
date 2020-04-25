package GUI;

import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.effect.Lighting;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;

public class Connection {

    boolean is_picked; //Info dla EventHandlera czy przypadkiem nie wybrano juz jakiegos pionka
    Label piece;
    ImageView picked_piece; //Ikona wybranego pionka
    Lighting light = new Lighting();
    Label[][] board;
    GridPane Board;

   public Connection(ChessBoard chessBoard){
       this.board=chessBoard.getboard();
       this.Board=chessBoard.getBoard();
   }

    //Obsluga przesuwania pionkow po planszy
    public void moving_on_chessboard()
    {
        EventHandler<MouseEvent> eventHandler = e -> {
            int find_col;
            int find_row;

            find_col=(int)((e.getX()/50)+1);
            find_row=(int)((e.getY()/50+1));

            //czy juz zostal wybrany pionek, jesli nie to zaznacza go
            if(is_picked==false) {
                if(board[find_row-1][find_col-1].getGraphic()==null) return;
                piece = board[find_row-1][find_col-1];
                picked_piece = (ImageView) board[find_row-1][find_col-1].getGraphic();
                board[find_row-1][find_col-1].getGraphic().setEffect(light);
                is_picked = true;
            }
            //jesli zostal wybrany wczesniej pionek to przenosi go na nowe miejsce
            else{
                piece.setGraphic(null);
                board[find_row-1][find_col-1].setGraphic(picked_piece);
                board[find_row-1][find_col-1].getGraphic().setEffect(null);
                is_picked = false;
            }
        };

        Board.addEventHandler(MouseEvent.MOUSE_CLICKED,eventHandler);
    }


}
