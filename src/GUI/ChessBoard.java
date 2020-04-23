package GUI;

import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class ChessBoard  {

    static Image BishopB = new Image(ChessBoard.class.getResourceAsStream("img/BishopB_icon.png"));
    static Image BishopW = new Image(ChessBoard.class.getResourceAsStream("img/BishopW_icon.png"));
    static Image KingB = new Image(ChessBoard.class.getResourceAsStream("img/KingB_icon.png"));
    static Image KingW = new Image(ChessBoard.class.getResourceAsStream("img/KingW_icon.png"));
    static Image KnightB = new Image(ChessBoard.class.getResourceAsStream("img/KnightB_icon.png"));
    static Image KnightW = new Image(ChessBoard.class.getResourceAsStream("img/KnightW_icon.png"));
    static Image PawnB = new Image(ChessBoard.class.getResourceAsStream("img/PawnB_icon.png"));
    static Image PawnW = new Image(ChessBoard.class.getResourceAsStream("img/PawnW_icon.png"));
    static Image QueenB = new Image(ChessBoard.class.getResourceAsStream("img/QueenB_icon.png"));
    static Image QueenW = new Image(ChessBoard.class.getResourceAsStream("img/QueenW_icon.png"));
    static Image RookB = new Image(ChessBoard.class.getResourceAsStream("img/RookB_icon.png"));
    static Image RookW = new Image(ChessBoard.class.getResourceAsStream("img/RookW_icon.png"));




    Insets buttonMargin = new Insets(0, 0, 0, 0);

    public static void BlankSpace(GridPane Board,Button[][] field, int size)
    {

        for(int row=0; row<size; row++)
        {
            for(int col=0;col<size;col++)
            {
                String color;
                field[row][col]=new Button();

                if((row+col)%2==0) color="#FFF2BC"; //"biale pola"
                else color="#513A28"; //"czarne" pola

                field[row][col].setStyle("-fx-background-color: " + color + ";-fx-border-color: black; -fx-padding: 0");
                Board.add(field[row][col], col, row);
                field[row][col].setPrefSize(52, 52);
            }
        }

        //Ustawienie pionków
        for (int col = 0; col < 8; col++) {
            field[1][col].setGraphic(new ImageView(PawnB));
        }
        for (int col = 0; col < 8; col++) {
            field[6][col].setGraphic(new ImageView(PawnW));
        }
        //Ustawienie wież
        field[0][0].setGraphic(new ImageView(RookB));
        field[0][7].setGraphic(new ImageView(RookB));
        field[7][0].setGraphic(new ImageView(RookW));
        field[7][7].setGraphic(new ImageView(RookW));

        //Ustawienie skoczków
        field[0][1].setGraphic(new ImageView(KnightB));
        field[0][6].setGraphic(new ImageView(KnightB));
        field[7][1].setGraphic(new ImageView(KnightW));
        field[7][6].setGraphic(new ImageView(KnightW));

        //Ustawienie gońców
        field[0][2].setGraphic(new ImageView(BishopB));
        field[0][5].setGraphic(new ImageView(BishopB));
        field[7][2].setGraphic(new ImageView(BishopW));
        field[7][5].setGraphic(new ImageView(BishopW));

        //Ustawienie krolowych
        field[0][3].setGraphic(new ImageView(QueenB));
        field[7][3].setGraphic(new ImageView(QueenW));

        //Ustawienie kroli
        field[0][4].setGraphic(new ImageView(KingB));
        field[7][4].setGraphic(new ImageView(KingW));


        for (int i = 0; i < size; i++) {
            Board.setHgap(0); // przestrzenie między polami szachownicy
            Board.setVgap(0);
        }
    }
}

