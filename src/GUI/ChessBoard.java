package GUI;

import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;

public class ChessBoard {

    public static void BlankSpace(GridPane Board,Button[][] field, int size)
    {
        for(int row=0; row<size; row++)
        {
            for(int col=0;col<size;col++)
            {
                String color;
                field[row][col]=new Button();

                if((row+col)%2==0) color="white";
                else color="black";

                field[row][col].setStyle("-fx-background-color: " + color + ";-fx-border-color: black;");
                Board.add(field[row][col], col, row);
                field[row][col].setPrefSize(50, 50);
            }
        }

        for (int i = 0; i < size; i++) {
            Board.setHgap(0); // przestrzenie między polami szachownicy
            Board.setVgap(0);
        }
    }
}

