package GUI;

import Structure.Board;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.awt.*;

public class Help {

    //Samouczek - moze byc wlaczony w "tle"
    public static void display(String color) {

        Stage window = new Stage();
        Label [] explain = new Label[6];

        window.setTitle("Samouczek"); //tytul okienka
        window.setMinWidth(490); //rozmiar okienka
        window.setMinHeight(550);
        window.getIcons().add(new Image(MainStage.class.getResourceAsStream("img/chess_icon.png"))); //ikona okna


        explain[0]=new Label(" Pionek w początkowym stadium, zanim przekroczy połowę " +
                "planszy,\n " +
                "może poruszać się o jeden bądź dwa kroki do przodu. Po przekroczeniu \n " +
                "tej granicy może poruszać się jedynie o jedno " +
                "pole do przodu.\n\n" +
                " Wartość punktowa w grze: 1");

        explain[1]=new Label(" Skoczek porusza się po literze \"L\"." +
                "  Może on wykonać najpierw\n jeden ruch w lewo bądź w prawo, a " +
                "następnie dwa do góry bądź w dół\n lub też wykonać " +
                "tę sekwencję w odwrotnej kolejności.\n\n" +
                " Wartość punktowa w grze: 3");

        explain[2]=new Label(" Goniec porusza się o dowolną ilość pól po skosie.\n\n" +
                " Wartość punktowa w grze: 3");

        explain[3]=new Label(" Wieża porusza się o dowolną ilość pól w pionie\n"+
                " lub w poziomie.\n\n"+
                " Wartość punktowa w grze: 5");

        explain[4]=new Label(" Hetman porusza się o dowolną ilość pól po skosie lub\n w pionie albo w poziomie.\n\n" +
                " Wartość punktowa w grze: 9");

        explain[5]=new Label(" Król porusza się o jedno pole w dowolnym kierunku.\n" +
                " Nie może wejść na pole atakowane przez przeciwnika.\n\n" +
                " Wartość punktowa w grze: brak");

        for (int i = 0; i < 6; i++) {
            explain[i].setStyle("-fx-background-color: #404040;-fx-border-color: black;-fx-text-fill:white;-fx-font-family: FreeMono, monospace;");
        }

        explain[0].setGraphic(new ImageView(BoardInitialization.PawnW));
        explain[1].setGraphic(new ImageView(BoardInitialization.KnightW));
        explain[2].setGraphic(new ImageView(BoardInitialization.BishopW));
        explain[3].setGraphic(new ImageView(BoardInitialization.RookW));
        explain[4].setGraphic(new ImageView(BoardInitialization.QueenW));
        explain[5].setGraphic(new ImageView(BoardInitialization.KingW));



        //tu juz takie rzeczy wizualne
        GridPane grid = new GridPane();
        VBox layout = new VBox(); // układa nas soba


        for(int i=0;i<6;i++)
        {
            layout.getChildren().add(explain[i]);
            explain[i].setMinSize(450,60);
            explain[i].setLineSpacing(10);
            explain[i].setGraphicTextGap(10);
        }

        layout.setSpacing(10);
        grid.getChildren().add(layout);
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        Scene scene = new Scene(grid);
        window.setScene(scene);
        window.showAndWait();
    }
}