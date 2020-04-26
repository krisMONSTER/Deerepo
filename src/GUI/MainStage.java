package GUI;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


public class MainStage extends Application{

    Stage window;
    Scene scene1, scene2;
    GridPane Board=new GridPane();
    public static void main(String[] args) {
        launch(args);
    }

    public void start(Stage primaryStage){

        window=primaryStage;
        Label label1 = new Label("Zagrajmy w szachy!");
        window.setTitle("Chess_Project");
        window.getIcons().add(new Image(MainStage.class.getResourceAsStream("img/chess_icon.png")));


        //Button 1 Rozpoczynanie gry
        Button button1=new Button("Rozpocznij gre");
        button1.setOnAction(e-> window.setScene(scene2));
        button1.setStyle("-fx-background-color: white;-fx-border-color: black;");
        // wyrażenie lambda - znacznie skraca kod, dostępne od Java 8


        //Button3 AlertBox test
        Button button3=new Button("Czy można oszukiwać w tej grze?");
        button3.setOnAction(e -> AlertBox.display("Oszukiwanie","Nie można oszukiwać!"));
        button3.setStyle("-fx-background-color: black;-fx-border-color: white;-fx-text-fill:white;");


        //Button4 Zamykanie programu
        Button button4=new Button( "Opuść grę");
        button4.setOnAction(e -> closeProgram());
        button4.setStyle("-fx-background-color: white;-fx-border-color: black;");
        window.setOnCloseRequest(e->
        {
            e.consume(); // sama metoda setOnCloseRequest zamyka program niezależnie jakiej odpowiedzi użytkownik udzieli
            closeProgram(); // uzywajac e.consume() przejmujemy kontrole nad tym co sie stanie np. uzywajac okreslonej metody
        });


        //Layout 1
        VBox layout1 = new VBox(20); // układa obiekty "w kolumnie"
        layout1.getChildren().addAll(label1, button1, button3,button4);
        layout1.setAlignment(Pos.CENTER);
        scene1 = new Scene(layout1, 300,300);


        //Button2 Przycisk powrotu do glownego menu
        Button button2=new Button("Menu");
        button2.setStyle("-fx-background-color: black;-fx-border-color: white;-fx-text-fill:white;");
        button2.setOnAction(e -> window.setScene(scene1));


        //Layout 2
        BorderPane border = new BorderPane(); // dzieli scenę na funkcjonalne części
        VBox layout2=new VBox(20);
        layout2.getChildren().add(button2);
        border.setTop(layout2);


        //Plansza
        GridPane Board=new GridPane();
        ChessBoard board=new ChessBoard(Board);
        board.BlankSpace(8);
        board.InitChessBoard();
        Move move =new Move(board);
        move.execute_move_mouse(); //Wykonywanie ruchu na planszy poprzez wybor pionka na planszy
        //move.execute_move_console(); //Wykonywanie ruchu na planszy po podaniu wczesniejszych wartosci w konsoli
        //move.execute_move_on_data_changes(3,1,3,3); //Wykonywanie ruchu na planszy po wprowadzeniu wartosci do funkcji
        border.setCenter(Board);
        scene2 = new Scene(border,600,600);


        //Uruchomienie gry w konsoli
        Game game = new Game();
        Thread watek_gry = new Thread(game);
        watek_gry.start();


        window.setScene(scene1);
        window.show();
    }


    private void closeProgram()
    {
        Boolean answer;
        answer=ConfirmBox.display("Wyjscie z gry", "Czy na pewno chcesz opuscic gre?\nPrzecież jest super!");
        if(answer==true) window.close();
    }

}
