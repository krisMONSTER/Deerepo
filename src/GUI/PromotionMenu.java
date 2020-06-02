package GUI;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class PromotionMenu {

    static String typeOfPiece = null;


    public static String display(boolean color){

        Stage window = new Stage();
        Button [] figures = new Button[4];

        window.initStyle(StageStyle.TRANSPARENT);
        window.initModality(Modality.APPLICATION_MODAL);
        //blokuje mozliwosc interakcji z innymi oknami, dopoki nie zostanie obsluzone to konkretne okno
        window.setTitle("Promocja piona"); //tytul okienka
        //window.setMinWidth(300); //rozmiar okienka
        //window.setMinHeight(320);
        window.getIcons().add(new Image(MainStage.class.getResourceAsStream("img/chess_icon.png"))); //ikona okna


        figures[0]= new Button("Skoczek");
        figures[1] = new Button("Wieża");
        figures[2] = new Button("Goniec");
        figures[3] = new Button("Hetman");

        if(color==false){ //tutaj uzywam tego argumentu color jako informacja jakiego koloru obrazki podstawic
            figures[0].setGraphic(new ImageView(BoardInitialization.KnightB));
            figures[1].setGraphic(new ImageView(BoardInitialization.RookB));
            figures[2].setGraphic(new ImageView(BoardInitialization.BishopB));
            figures[3].setGraphic(new ImageView(BoardInitialization.QueenB));

            for(int i=0; i<4; i++)
            {
                figures[i].setStyle("-fx-background-color: white;-fx-border-color: black;");
            }
        }
        else{
            figures[0].setGraphic(new ImageView(BoardInitialization.KnightW));
            figures[1].setGraphic(new ImageView(BoardInitialization.RookW));
            figures[2].setGraphic(new ImageView(BoardInitialization.BishopW));
            figures[3].setGraphic(new ImageView(BoardInitialization.QueenW));
            for(int i=0; i<4; i++)
            {
                figures[i].setStyle("-fx-background-color: black;-fx-border-color: white;-fx-text-fill:white;");
            }
        }


        figures[0].setOnAction(e->{ //tutaj moja wartoscia zwracana jest ten obrazek ktory przypisalam u gory w ifie, takze tutaj sobie smialo pozmieniaj
            //na to co tam bedziesz chcial zwracac

            //dobra

            typeOfPiece = "knight";
            window.close();
        });
        figures[1].setOnAction(e->{
            typeOfPiece = "rook";
            window.close();
        });
        figures[2].setOnAction(e->{
            typeOfPiece = "bishop";
            window.close();
        });
        figures[3].setOnAction(e->{
            typeOfPiece = "queen";
            window.close();
        });

        //tu juz takie rzeczy wizualne
        VBox layout = new VBox(50); // układa nas soba

        for(int i=0;i<4;i++)
        {
            figures[i].setPrefSize(120,50);
            layout.getChildren().add(figures[i]);
        }
        layout.setAlignment(Pos.CENTER);
        layout.setSpacing(10);
        StackPane outerLayout = new StackPane();
        outerLayout.getChildren().add(layout);
        Scene scene = new Scene(outerLayout,300,320);
        window.setScene(scene);
        window.showAndWait();

        return typeOfPiece; // zwracam figure ktora zostala wybrana przez nacisniecie przycisku
    }

}
