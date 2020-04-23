package Structure;

import java.util.Objects;

public class Pawn extends Piece{
	private boolean en_passant;
	public Pawn(int a, int b, boolean c) {
		super(a,b,c);
		en_passant = false;
	}
	
	public boolean get_en_passant() {
		return en_passant;
	}
	
	public void set_en_passant(boolean x) {
		en_passant = x;
	}
	
	public boolean is_move_possible(int a, int b) {
		if(a == x) {
			if(colour) {
				if( b == y+2 && Board.is_field_free(x, y+1) && y == 1 ) {
					return true;
				}
				return b == y + 1;
			}
			else {
				if( b == y-2 && Board.is_field_free(x, y-1) && y == 6 ) {
					return true;
				}
				return b == y - 1;
			}
		}
		else if(a == x+1 || a == x-1) {
			return (colour && b == y + 1) || (!colour && b == y - 1);
		}
		return false;
	}
	
	public boolean is_move_valid(int a, int b) {
		if(is_move_possible(a,b)) {
			if(a == x) {
				return Board.is_field_free(a, b);
			}
			else {
				if(Board.what_on_field(a, b) == (colour?1:2)) {
					return true;
				}
				else if(Board.is_field_free(a, b)) {
					if(Board.what_on_field(a, y) == (colour?1:2)) {
						return Objects.requireNonNull(Board.get_piece(a, y)).get_en_passant();
					}
				}
			}
		}
		return false;
	}

	public boolean endangers_field(int a, int b){
		if(is_move_possible(a, b)){
			return a != x;
		}
		else {
			return false;
		}
	}

	public void set_data_changes(int a, int b, Data_changes data_changes) {
		if(!Board.is_field_free(a, b))
			data_changes.put_remove(a, b);
		else if(a!=x)
			data_changes.put_remove(a, y);
		else if(Math.abs(b-y)>1)
			data_changes.put_en_passant(x, y);
		data_changes.put_move(x, y, a, b);
	}
	
}
