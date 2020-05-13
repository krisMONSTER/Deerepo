package GUI;

public class Game extends Thread {
    //zaremowalem zeby bledow nie bylo
/*
    private static Scanner sc = new Scanner(System.in);
    private static Player white = new Player("BornToFight",true);
    private static Player black = new Player("Deer",false);
    private static Player current = black;

    public void run(){
        System.out.println("Zaczynaja biale!");
        while(true) {
            if(current==white) current = black;
            else current = white;
            Board.setPlayer(current);
            Board.display();
            int x,y;
            Structure.clickResult tmp;
            do {
                System.out.print("Podaj x:");
                x = sc.nextInt();
                sc.nextLine();
                System.out.print("Podaj y:");
                y = sc.nextInt();
                sc.nextLine();
                tmp = Board.clickOnBoard(x, y);
                System.out.println(tmp);
            }while(tmp!= clickResult.move);
            Board.findAndResetEnPassant(!current.getColour());
        }
    }*/
}
