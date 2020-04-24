package Structure;
import java.util.List;
import java.util.ArrayList;
import java.util.Objects;

public class Board {
	private static List<Piece> piece_list = new ArrayList<>();
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
	
	public static Piece get_piece(int x, int y) {
		for (Piece piece : piece_list) {
			if (x == piece.get_x() && y == piece.get_y()) {
				return piece;
			}
		}
		return null;
	}
	
	public static byte click_on_board(int x, int y) {
		/*
		 0 - nic nie rob
		 1 - zaznacz pole (x,y)
		 2 - wyczysc wczesniej zaznaczone pole i ruch na (x,y)
		 3 - wyczysc wczesniej zaznaczone pole i zaznacz (x,y)
		 4 - wyczysc wczesniej zaznaczone pole
		 */
		if(Player.get_picked_piece()==null) {
			if(Player.pick(x, y))
				return 1;
			else
				return 0;
		}
		else {
			if(Player.is_move_valid(x, y)) {
				Player.set_data_changes(x, y);
				return 2;
			}
			else {
				if(Player.pick(x, y))
					return 3;
				else
					return 4;
			}
		}
	}
	
	public static byte what_on_field(int x, int y) {
		Piece tmp = get_piece(x,y);
		if(tmp == null)
			return 0;
		else if(tmp.get_colour())
			return 2;
		else
			return 1;
	}
	
	public static boolean is_space_free(int from, int to, int level, boolean direction) {
		//true - w pionie
		//false - w poziomie
		if(direction) {
			for(;from<=to;from++) {
				if(what_on_field(level,from)!=0)
					return false;
			}
		}else {
			for(;from<=to;from++) {
				if(what_on_field(from,level)!=0)
					return false;
			}
		}
		return true;
	}
	
	public static boolean is_field_free(int x, int y) {
		return what_on_field(x,y)==0;
	}
	
	public static boolean is_slant_free(int from_x, int to_x, int bottom_y, boolean direction) {
		//true - prawy skos
		//false - lewy skos
		if(direction)
			for(;from_x<=to_x;from_x++,bottom_y++) {
				if(what_on_field(from_x,bottom_y)!=0)
					return false;
			}
		else {
			for(;from_x<=to_x;to_x--,bottom_y++) {
				if(what_on_field(to_x,bottom_y)!=0)
					return false;
			}
		}
		return true;
	}
	
	public static void execute_data_changes(Data_changes data_changes) {
		//Obiekt, w ktorym sa dane o pozycji figury, ktora uzyla en passant
		Piece_position en_passant_inclusion = data_changes.get_en_passant_inclusion();
		if(en_passant_inclusion!=null) {
			Objects.requireNonNull(get_piece(en_passant_inclusion.get_x(), en_passant_inclusion.get_y())).set_en_passant(true);
		}

		//Lista obiektow, w ktorych sa dane o pozycjach figur,
		//w ktorych usuwana jest gotowosc do castlingu
		List<Piece_position> castling_exclusions = data_changes.get_castling_exclusions();
		if(!castling_exclusions.isEmpty()) {
			for (Piece_position castling_exclusion : castling_exclusions) {
				Objects.requireNonNull(get_piece(castling_exclusion.get_x(), castling_exclusion.get_y())).reset_castling_readiness();
			}
		}

		//Lista obiektow, w ktorych sa dane o pozycjach figur do usuniecia
		List<Piece_position> pieces_to_remove = data_changes.get_pieces_to_remove();
		if(!pieces_to_remove.isEmpty()) {
			for (Piece_position piece_to_remove : pieces_to_remove) {
				Piece piece = get_piece(piece_to_remove.get_x(), piece_to_remove.get_y());
				piece_list.remove(piece);
				//cos tam graficzengo
			}
		}

		//Lista obiektow, w ktorych sa dane o przemieszczeniu
		List<Piece_coordinates> pieces_to_move = data_changes.get_pieces_to_move();
		for (Piece_coordinates piece_to_move : pieces_to_move) {
			Piece piece = get_piece(piece_to_move.get_x(), piece_to_move.get_y());
			assert piece != null;
			piece.set_x(piece_to_move.get_new_x());
			piece.set_y(piece_to_move.get_new_y());
			//cos tam graficzengo
		}

		//Piece - klasa abstrakcyjna, nadrzedna wzgledem reszty figur
		//Obiekt, w ktorym przechowywany jest nowa figura do dodania
		Piece piece_to_add = data_changes.get_piece_addition();
		if(piece_to_add!=null) {
			piece_list.add(piece_to_add);
			//cos tam graficznego
		}
	}
	
	public static void find_and_reset_en_passant(boolean base_colour) {
		for (Piece piece : piece_list) {
			if (piece.get_colour() == base_colour && piece.get_en_passant()) {
				piece.set_en_passant(false);
				return;
			}
		}
	}
	
	public static boolean is_check_on_field(int a, int b, boolean base_colour) {
		for (Piece piece : piece_list) {
			if (piece.get_colour() != base_colour) {
				if(piece.endangers_field(a, b)){
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
					if (piece.get_x() == ii && piece.get_y() == i) {
						if (piece instanceof Pawn) {
							if (piece.get_colour())
								System.out.print(" P |");
							else
								System.out.print(" p |");
						} else if (piece instanceof Knight) {
							if (piece.get_colour())
								System.out.print(" N |");
							else
								System.out.print(" n |");
						} else if (piece instanceof Bishop) {
							if (piece.get_colour())
								System.out.print(" B |");
							else
								System.out.print(" b |");
						} else if (piece instanceof Rook) {
							if (piece.get_colour())
								System.out.print(" R |");
							else
								System.out.print(" r |");
						} else if (piece instanceof Queen) {
							if (piece.get_colour())
								System.out.print(" Q |");
							else
								System.out.print(" q |");
						} else {
							if (piece.get_colour())
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
