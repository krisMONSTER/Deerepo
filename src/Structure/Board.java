package Structure;
import javafx.scene.image.Image;

import java.util.List;
import java.util.ArrayList;

public class Board {
	private static List<Piece> pieceList;
	private static BoardStates boardStates;

	//TO SIE POTEM WYWALI
	private static Player player;

	public static void setupBoard() {
		pieceList = new ArrayList<>();
		boardStates = new BoardStates();
		//pawns
		for (int i = 0; i < 8; i++) {
			pieceList.add(new Pawn(i, 1, true));
			pieceList.add(new Pawn(i, 6, false));
		}
		//knights
		pieceList.add(new Knight(1, 0, true));
		pieceList.add(new Knight(1, 7, false));
		pieceList.add(new Knight(6, 0, true));
		pieceList.add(new Knight(6, 7, false));
		//bishops
		pieceList.add(new Bishop(2, 0, true));
		pieceList.add(new Bishop(2, 7, false));
		pieceList.add(new Bishop(5, 0, true));
		pieceList.add(new Bishop(5, 7, false));
		//rooks
		pieceList.add(new Rook(0, 0, true));
		pieceList.add(new Rook(0, 7, false));
		pieceList.add(new Rook(7, 0, true));
		pieceList.add(new Rook(7, 7, false));
		//queens
		pieceList.add(new Queen(3, 0, true));
		pieceList.add(new Queen(3, 7, false));
		//kings
		pieceList.add(new King(4, 0, true));
		pieceList.add(new King(4, 7, false));
	}

	public static Piece getPiece(int x, int y) {
		for (Piece piece : pieceList) {
			if (x == piece.getX() && y == piece.getY()) {
				return piece;
			}
		}
		return null;
	}
	//TO TEZ SIE POTEM WYWALI
	public static ClickResult clickOnBoard(int x, int y) {
		/*
		 0 - nic nie rob
		 1 - zaznacz pole (x,y)
		 2 - wyczysc wczesniej zaznaczone pole i ruch na (x,y)
		 3 - wyczysc wczesniej zaznaczone pole i zaznacz (x,y)
		 4 - wyczysc wczesniej zaznaczone pole
		 */
		if(player.getPickedPiece()==null) {
			if(player.pick(x, y))
				return ClickResult.pick;
			else
				return ClickResult.nothing;
		}
		else {
			if(player.isMoveValid(x, y)) {
				//player.makeChanges(x, y);
				return ClickResult.move;
			}
			else {
				if(player.pick(x, y))
					return ClickResult.repick;
				else
					return ClickResult.clear;
			}
		}
	}
	
	public static byte whatOnField(int x, int y) {
		Piece tmp = getPiece(x,y);
		if(tmp == null)
			return 0;
		else if(tmp.getColour())
			return 2;
		else
			return 1;
	}
	
	public static boolean isSpaceFree(int from, int to, int level, boolean direction) {
		//true - w pionie
		//false - w poziomie
		if(direction) {
			for(;from<=to;from++) {
				if(whatOnField(level,from)!=0)
					return false;
			}
		}else {
			for(;from<=to;from++) {
				if(whatOnField(from,level)!=0)
					return false;
			}
		}
		return true;
	}
	
	public static boolean isFieldFree(int x, int y) {
		return whatOnField(x,y)==0;
	}
	
	public static boolean isSlantFree(int from_x, int to_x, int bottom_y, boolean direction) {
		//true - prawy skos
		//false - lewy skos
		if(direction)
			for(;from_x<=to_x;from_x++,bottom_y++) {
				if(whatOnField(from_x,bottom_y)!=0)
					return false;
			}
		else {
			for(;from_x<=to_x;to_x--,bottom_y++) {
				if(whatOnField(to_x,bottom_y)!=0)
					return false;
			}
		}
		return true;
	}
	
	public static void executeDataChanges(DataChanges dataChanges) {
		boardStates.incrementHalfMoveClock();
		List<Alteration> alterationList = dataChanges.getAlterationList();
		for(Alteration alteration : alterationList){
			Piece alteredPiece = getPiece(alteration.getX(), alteration.getY());
			switch (alteration.getTypeOfAlter()) {
				case move -> {
					assert alteredPiece != null;
					if(alteredPiece instanceof Pawn){
						boardStates.resetHalfMoveClock();
						boardStates.resetBoardStates();
					}
					alteredPiece.setX(alteration.getNewX());
					alteredPiece.setY(alteration.getNewY());
				}
				case remove -> {
					pieceList.remove(alteredPiece);
					boardStates.resetHalfMoveClock();
					boardStates.resetBoardStates();
				}
				case promotion -> {
					pieceList.remove(alteredPiece);
					pieceList.add(alteration.getNewPiece());
					boardStates.resetBoardStates();
				}
				case enPassantInclusion -> {
					assert alteredPiece != null;
					alteredPiece.setEnPassant(true);
				}
				case castlingExclusion -> {
					assert alteredPiece != null;
					alteredPiece.resetCastlingReadiness();
				}
			}
		}
	}
	
	public static void findAndResetEnPassant(boolean baseColour) {
		for (Piece piece : pieceList) {
			if (piece.getColour() == baseColour && piece.getEnPassant()) {
				piece.setEnPassant(false);
				return;
			}
		}
	}
	
	public static boolean isCheckOnField(int a, int b, boolean baseColour) {
		for (Piece piece : pieceList) {
			if (piece.getColour() != baseColour) {
				if(piece.endangersField(a, b)){
					return true;
				}
			}
		}
		return false;
	}

	public static void addCurrentBoardState(){
		boardStates.addState(pieceList);
	}

	public static GameState checkGameState() {
		for(Piece king : pieceList){
			if(king instanceof King){
				boolean kingColour = king.getColour();
				if(isCheckOnField(king.getX(),king.getY(),kingColour)){
					if(!king.isAbleToMove()){
						if(kingColour){
							return GameState.blackWon;
						}
						else{
							return GameState.whiteWon;
						}
					}
				}
				else{
					boolean movePossible = false;
					for(Piece piece : pieceList){
						if(piece.getColour() == kingColour){
							if(piece.isAbleToMove()){
								movePossible = true;
								break;
							}
						}
					}
					if(!movePossible){
						return GameState.draw;
					}
				}
			}
		}
		if(boardStates.stackOfThreePresence()){
			return GameState.draw;
		}
		if(boardStates.getHalfMoveClock() >= 100){
			return GameState.draw;
		}
		int size = pieceList.size();
		if(size <= 4){
			if(size == 2){
				return GameState.draw;
			}
			else if(size == 3){
				for(Piece piece : pieceList){
					if(piece instanceof Bishop || piece instanceof Knight){
						return GameState.draw;
					}
				}
			}
			else {
				List<Piece> bishops = new ArrayList<>();
				for(Piece piece : pieceList){
					if(piece instanceof Bishop){
						bishops.add(piece);
					}
				}
				if(bishops.size() == 2){
					Piece b1 = bishops.get(0);
					Piece b2 = bishops.get(1);
					if(b1.getColour()!=b2.getColour()){
						if((b1.getX()+b1.getY())%2 == (b2.getX()+b2.getY())%2){
							return GameState.draw;
						}
					}
				}
			}
		}
		return GameState.active;
	}

	//to na dole to tylko testowo -------------------

	public static void test(){
		boardStates.test();
	}

	public static void display() {
		/*List<Piece> test = new ArrayList<>(pieceList);
		List<Piece> test2 = new ArrayList<>(test);
		for(Piece piece : test2){
            System.out.println(piece.getClass().getSimpleName()+" "+piece.getX()+" "+piece.getY());
        }*/
		for(int i=0;i<33;i++) {
			System.out.print("-");
		}
		System.out.println();
		for(int i=7;i>=0;i--) {
			System.out.print("|");
			outer:
			for(int ii=0;ii<8;ii++) {
				for (Piece piece : pieceList) {
					if (piece.getX() == ii && piece.getY() == i) {
						if (piece instanceof Pawn) {
							if (piece.getColour())
								System.out.print(" P |");
							else
								System.out.print(" p |");
						} else if (piece instanceof Knight) {
							if (piece.getColour())
								System.out.print(" N |");
							else
								System.out.print(" n |");
						} else if (piece instanceof Bishop) {
							if (piece.getColour())
								System.out.print(" B |");
							else
								System.out.print(" b |");
						} else if (piece instanceof Rook) {
							if (piece.getColour())
								System.out.print(" R |");
							else
								System.out.print(" r |");
						} else if (piece instanceof Queen) {
							if (piece.getColour())
								System.out.print(" Q |");
							else
								System.out.print(" q |");
						} else {
							if (piece.getColour())
								System.out.print(" K |");
							else
								System.out.print(" k |");
						}
						continue outer;
					}
				}
				System.out.print("   |");
			}
			System.out.println();
			for(int ii=0;ii<33;ii++) {
				System.out.print("-");
			}
			System.out.println();
		}
	}
}
