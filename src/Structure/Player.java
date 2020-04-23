package Structure;

public class Player {
	private static boolean colour;
	private static Piece picked_piece;
	private static Data_changes data_changes = new Data_changes();
	
	public static void set_colour(boolean color) {
		colour = color;
	}
	
	public static void reset_en_passant() {
		Board.find_and_reset_en_passant(colour);
	}
	
	public static boolean pick(int x, int y) {
		picked_piece = Board.get_piece(x,y);
		if(picked_piece == null)
			return false;
		else if(picked_piece.get_colour() == colour) {
			return true;
		}
		else {
			picked_piece = null;
			return false;
		}
	}
	
	public static Piece get_picked() {
		return picked_piece;
	}
	
	public static boolean is_move_valid_with_picked(int x, int y) {
		return picked_piece.is_move_valid(x, y);
	}
	
	public static void set_data_changes_with_picked(int x, int y) {
		picked_piece.set_data_changes(x, y, data_changes);
		Board.execute_data_changes(data_changes);
		//send data_changes to user
		data_changes.reset_data_changes();
		picked_piece=null;
	}
}
