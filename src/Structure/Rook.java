package Structure;

public class Rook extends Piece{
	private boolean castling_ready = true;
	
	public Rook(int a, int b, boolean c) {
		super(a,b,c);
	}

	public void reset_castling_readiness(){
		castling_ready = false;
	}

	public boolean get_castling_readiness(){
		return castling_ready;
	}

	public boolean is_move_possible(int a, int b) {
		if(a == x) {
			if(b>y) {
				return Board.is_space_free(y + 1, b - 1, x, true);
			}
			else {
				return Board.is_space_free(b + 1, y - 1, x, true);
			}
		}
		else if(b == y) {
			if(a>x) {
				return Board.is_space_free(x + 1, a - 1, y, false);
			}
			else {
				return Board.is_space_free(a + 1, x - 1, y, false);
			}
		}
		return false;
	}

	public void set_data_changes(int a, int b, Data_changes data_changes) {
		if(!Board.is_field_free(a, b))
			data_changes.put_piece_removal(a, b);
		if(castling_ready)
			data_changes.put_castling_exclusion(x, y);
		data_changes.put_piece_movement(x, y, a, b);
	}
}
