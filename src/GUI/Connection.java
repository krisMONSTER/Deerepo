package GUI;

import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.effect.Lighting;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;

public class Connection {

    boolean is_picked; //Info dla EventHandlera czy przypadkiem nie wybrano juz jakiegos pionka
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
            int ustal_kolumne;
            int ustal_wiersz;
            ustal_kolumne=(int)((e.getX()/50)+1);
            ustal_wiersz=(int)((e.getY()/50+1));
            if(is_picked==false) {
                if(board[ustal_wiersz-1][ustal_kolumne-1].getGraphic()==null) return;
                picked_piece = (ImageView) board[ustal_wiersz-1][ustal_kolumne-1].getGraphic();
                board[ustal_wiersz-1][ustal_kolumne-1].getGraphic().setEffect(light);
                is_picked = true;
            }
            else{
                board[ustal_wiersz-1][ustal_kolumne-1].setGraphic(picked_piece);
                board[ustal_wiersz-1][ustal_kolumne-1].getGraphic().setEffect(null);
                is_picked = false;
            }
        };

        Board.addEventHandler(MouseEvent.MOUSE_CLICKED,eventHandler);
    }


}
