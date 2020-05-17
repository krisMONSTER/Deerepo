package GUI;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class PromotionMenu {

    static ImageView pawn;


    public static ImageView display(String color){

        Stage window = new Stage();
        Label label1 = new Label();
        Button [] figures = new Button[4];

        window.initModality(Modality.APPLICATION_MODAL);
        //blokuje mozliwosc interakcji z innymi oknami, dopoki nie zostanie obsluzone to konkretne okno
        window.setTitle("Promocja pionka"); //tytul okienka
        window.setMinWidth(300); //rozmiar okienka
        window.setMinHeight(320);
        window.getIcons().add(new Image(MainStage.class.getResourceAsStream("img/chess_icon.png"))); //ikona okna


        figures[0]= new Button("Knight");
        figures[1] = new Button("Rook");
        figures[2] = new Button("Bishop");
        figures[3] = new Button("Queen");

        if(color=="black"){ //tutaj uzywam tego argumentu color jako informacja jakiego koloru obrazki podstawic
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
            pawn=(ImageView)figures[0].getGraphic();
            window.close();
        });
        figures[1].setOnAction(e->{
            pawn=(ImageView)figures[1].getGraphic();
            window.close();
        });
        figures[2].setOnAction(e->{
            pawn=(ImageView)figures[2].getGraphic();
            window.close();
        });
        figures[3].setOnAction(e->{
            pawn=(ImageView)figures[3].getGraphic();
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
        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.showAndWait();

        return pawn; // zwracam figure ktora zostala wybrana przez nacisniecie przycisku
    }

}
