package GUI;

import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Font;

import static GUI.MainStage.board;


public class BoardInitialization {

    static Image BishopB = new Image(BoardInitialization.class.getResourceAsStream("img/BishopB_icon.png"));
    static Image BishopW = new Image(BoardInitialization.class.getResourceAsStream("img/BishopW_icon.png"));
    static Image KingB = new Image(BoardInitialization.class.getResourceAsStream("img/KingB_icon.png"));
    static Image KingW = new Image(BoardInitialization.class.getResourceAsStream("img/KingW_icon.png"));
    static Image KnightB = new Image(BoardInitialization.class.getResourceAsStream("img/KnightB_icon.png"));
    static Image KnightW = new Image(BoardInitialization.class.getResourceAsStream("img/KnightW_icon.png"));
    static Image PawnB = new Image(BoardInitialization.class.getResourceAsStream("img/PawnB_icon.png"));
    static Image PawnW = new Image(BoardInitialization.class.getResourceAsStream("img/PawnW_icon.png"));
    static Image QueenB = new Image(BoardInitialization.class.getResourceAsStream("img/QueenB_icon.png"));
    static Image QueenW = new Image(BoardInitialization.class.getResourceAsStream("img/QueenW_icon.png"));
    static Image RookB = new Image(BoardInitialization.class.getResourceAsStream("img/RookB_icon.png"));
    static Image RookW = new Image(BoardInitialization.class.getResourceAsStream("img/RookW_icon.png"));
    static Image [] WhitePawns=new Image[6];
    static Image [] BlackPawns=new Image[6];


    final static int FIELD_SIZE=50;
    final static Font FIELD_FONT=new Font("Arial",10);
    final static String WHITE_FIELD="#ead5a0";
    final static String BLACK_FIELD="#b6935e";


    public static void CreateListOfPawns(){
        WhitePawns[0]=PawnW;
        WhitePawns[1]=BishopW;
        WhitePawns[2]=KnightW;
        WhitePawns[3]=RookW;
        WhitePawns[4]=QueenW;

        BlackPawns[0]=PawnB;
        BlackPawns[1]=BishopB;
        BlackPawns[2]=KnightB;
        BlackPawns[3]=RookB;
        BlackPawns[4]=QueenB;
    }

    //wyswietla pusta plansze
    public static void BlankSpace(int size) {

        //x - wiersz, y - kolumna
        for (int x = 0; x < size; x++) {
            for (int y = 0; y < size; y++) {
                String color;
                board[x][y] = new Label();


                if ((x + y) % 2 == 0) color = WHITE_FIELD; //"biale pola"
                else color = BLACK_FIELD; //"czarne" pola

                board[x][y].setStyle("-fx-background-color: " + color + ";-fx-border-color: black; -fx-padding: 0");
                MainStage.gridPane.add(board[x][y], y+1,x+1);
                board[x][y].setPrefSize(FIELD_SIZE, FIELD_SIZE);
                board[x][y].setGraphicTextGap(0);
            }
        }

        MainStage.gridPane.setHgap(0); // przestrzenie między polami szachownicy
        MainStage.gridPane.setVgap(0);
        MainStage.gridPane.setPrefSize(FIELD_SIZE*8,FIELD_SIZE*8);

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

    public static void resetColors() {
        String color;
        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                if ((x + y) % 2 == 0) color = WHITE_FIELD; //"biale pola"
                else color = BLACK_FIELD; //"czarne" pola

                board[x][y].setStyle("-fx-background-color: " + color + ";-fx-border-color: black; -fx-padding: 0");
            }
        }
    }

    public static Boolean pawnColor(ImageView pawn){
        Boolean color=false;

        for(int i=0;i<6;i++)
        {
            if(pawn.getImage()==WhitePawns[i]){
                color=true;
                break;
            }
        }
        return color;
    }

}

