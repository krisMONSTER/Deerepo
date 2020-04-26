package Structure;
import java.util.List;
import java.util.ArrayList;

public class Board {
	private static List<Piece> piece_list = new ArrayList<>();
	private static Player player;
	static {
		//pawns
		for(int i=0;i<8;i++) {
			piece_list.add(new Pawn(i,1,true));
			piece_list.add(new Pawn(i,6,false));
		}
		//knights
		piece_list.add(new Knight(1,0,true));
		piece_list.add(new Knight(1,7,false));
		piece_list.add(new Knight(6,0,true));
		piece_list.add(new Knight(6,7,false));
		//bishops
		piece_list.add(new Bishop(2,0,true));
		piece_list.add(new Bishop(2,7,false));
		piece_list.add(new Bishop(5,0,true));
		piece_list.add(new Bishop(5,7,false));
		//rooks
		piece_list.add(new Rook(0,0,true));
		piece_list.add(new Rook(0,7,false));
		piece_list.add(new Rook(7,0,true));
		piece_list.add(new Rook(7,7,false));
		//queens
		piece_list.add(new Queen(3,0,true));
		piece_list.add(new Queen(3,7,false));
		//kings
		piece_list.add(new King(4,0,true));
		piece_list.add(new King(4,7,false));
	}

	public static void setPlayer(Player player) {
		Board.player = player;
	}

	public static Piece getPiece(int x, int y) {
		for (Piece piece : piece_list) {
			if (x == piece.getX() && y == piece.getY()) {
				return piece;
			}
		}
		return null;
	}

	enum clickResult{
		nothing,
		pick,
		move,
		repick,
		clear
	}

	public static clickResult clickOnBoard(int x, int y) {
		/*
		 0 - nic nie rob
		 1 - zaznacz pole (x,y)
		 2 - wyczysc wczesniej zaznaczone pole i ruch na (x,y)
		 3 - wyczysc wczesniej zaznaczone pole i zaznacz (x,y)
		 4 - wyczysc wczesniej zaznaczone pole
		 */
		if(player.getPickedPiece()==null) {
			if(player.pick(x, y))
				return clickResult.pick;
			else
				return clickResult.nothing;
		}
		else {
			if(player.isMoveValid(x, y)) {
				player.setDataChanges(x, y);
				return clickResult.move;
			}
			else {
				if(player.pick(x, y))
					return clickResult.repick;
				else
					return clickResult.clear;
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
		List<Alteration> alterationList = dataChanges.getAlterationList();
		for(Alteration alteration : alterationList){
			Piece alteredPiece = getPiece(alteration.getX(), alteration.getY());
			switch (alteration.getTypeOfAlter()){
				case move:
					assert alteredPiece != null;
					alteredPiece.setX(alteration.getNewX());
					alteredPiece.setY(alteration.getNewY());
					break;
				case capture:
					piece_list.remove(alteredPiece);
					break;
				case promotion:
					piece_list.remove(alteredPiece);
					piece_list.add(alteration.getNewPiece());
					break;
				case enPassantInclusion:
					assert alteredPiece != null;
					alteredPiece.setEnPassant(true);
					break;
				case castlingExclusion:
					assert alteredPiece != null;
					alteredPiece.resetCastlingReadiness();
					break;
			}
		}
	}
	
	public static void findAndResetEnPassant(boolean baseColour) {
		for (Piece piece : piece_list) {
			if (piece.getColour() == baseColour && piece.getEnPassant()) {
				piece.setEnPassant(false);
				return;
			}
		}
	}
	
	public static boolean isCheckOnField(int a, int b, boolean baseColour) {
		for (Piece piece : piece_list) {
			if (piece.getColour() != baseColour) {
				if(piece.endangersField(a, b)){
					return true;
				}
			}
		}
		return false;
	}
	
	//to na dole bedzie do wywalenia
	
	public static void display() {
		for(int i=0;i<33;i++) {
			System.out.print("-");
		}
		System.out.println();
		for(int i=7;i>=0;i--) {
			System.out.print("|");
			outer:
			for(int ii=0;ii<8;ii++) {
				for (Piece piece : piece_list) {
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
