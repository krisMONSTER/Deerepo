package Structure;

public class King extends Piece{
	private boolean castling_ready = true;

	public King(int a, int b, boolean c) {
		super(a,b,c);
	}
	
	public boolean is_move_possible(int a, int b) {
		if(a == x+1 || a == x-1) {
			return b == y + 1 ||
					b == y ||
					b == y - 1;
		}
		else if(b == y+1 || b == y-1) {
			return a == x;
		}
		return false;
	}

	private boolean is_castling_line_on_check(int start, int end, int y){
		for(;start<=end;start++){
			if(Board.is_check_on_field(start, y, colour)){
				return true;
			}
		}
		return false;
	}

	public boolean is_move_valid(int a, int b) {
		if(is_move_possible(a,b) && !Board.is_check_on_field(a, b, colour)) {
			return Board.what_on_field(a, b) != (colour ? 2 : 1);
		}
		else if(castling_ready && b == y){
			if(a == x+2){
				if(Board.what_on_field(7,y) == (colour ? 2 : 1)){
					Piece piece = Board.get_piece(7,y);
					if(piece instanceof Rook && piece.get_castling_readiness()){
						return !is_castling_line_on_check(x, a, y) && Board.is_space_free(x+1, 6, y, false);
					}
				}
			}
			else if(a == x-2){
				if(Board.what_on_field(0,y) == (colour ? 2 : 1)){
					Piece piece = Board.get_piece(0,y);
					if(piece instanceof Rook && piece.get_castling_readiness()){
						return !is_castling_line_on_check(a, x, y) && Board.is_space_free(1, x-1, y, false);
					}
				}
			}
		}
		return false;
	}

	public void set_data_changes(int a, int b, Data_changes data_changes) {
		if(!Board.is_field_free(a, b))
			data_changes.put_remove(a, b);
		data_changes.put_move(x, y, a, b);
		if(a == x+2){
			data_changes.put_move(7, y, a-1, b);
		}
		else if(a == x-2){
			data_changes.put_move(0, y, a+1, b);
		}
	}
}