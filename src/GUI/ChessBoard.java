package GUI;

import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import static GUI.MainStage.board;


public class ChessBoard {

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


    Label piece;
    ImageView piece_image; //Ikona wybranego pionka


    //wyswietla pusta plansze
    public static void BlankSpace(int size) {

        //x - wiersz, y - kolumna
        for (int x = 0; x < size; x++) {
            for (int y = 0; y < size; y++) {
                String color;
                board[x][y] = new Label();

                if ((x + y) % 2 == 0) color = "#FFF2BC"; //"biale pola"
                else color = "#513A28"; //"czarne" pola

                board[x][y].setStyle("-fx-background-color: " + color + ";-fx-border-color: black; -fx-padding: 0");
                MainStage.gridPane.add(board[x][y], y, x);
                board[x][y].setPrefSize(50, 50);
            }
        }

        MainStage.gridPane.setHgap(0); // przestrzenie między polami szachownicy
        MainStage.gridPane.setVgap(0);
        MainStage.gridPane.setPrefSize(400,400);

    }

    //wyswietla pionki na planszy

    public static void InitChessBoard() {

        //Ustawienie pionków
        for (int y = 0; y < 8; y++) {
            board[1][y].setGraphic(new ImageView(PawnB));
        }

        for (int y = 0; y < 8; y++) {
            board[6][y].setGraphic(new ImageView(PawnW));
        }
        //Ustawienie wież
        board[0][0].setGraphic(new ImageView(RookB));
        board[0][7].setGraphic(new ImageView(RookB));
        board[7][0].setGraphic(new ImageView(RookW));
        board[7][7].setGraphic(new ImageView(RookW));

        //Ustawienie skoczków
        board[0][1].setGraphic(new ImageView(KnightB));
        board[0][6].setGraphic(new ImageView(KnightB));
        board[7][1].setGraphic(new ImageView(KnightW));
        board[7][6].setGraphic(new ImageView(KnightW));

        //Ustawienie gońców
        board[0][2].setGraphic(new ImageView(BishopB));
        board[0][5].setGraphic(new ImageView(BishopB));
        board[7][2].setGraphic(new ImageView(BishopW));
        board[7][5].setGraphic(new ImageView(BishopW));

        //Ustawienie krolowych
        board[0][3].setGraphic(new ImageView(QueenB));
        board[7][3].setGraphic(new ImageView(QueenW));

        //Ustawienie kroli
        board[0][4].setGraphic(new ImageView(KingB));
        board[7][4].setGraphic(new ImageView(KingW));
    }
}

