package Structure;

public class Queen extends Piece{
	public Queen(int a, int b, boolean c) {
		super(a,b,c);
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
		else if( Math.abs(a-x)==Math.abs(b-y) ) {
			if( a>x && b>y ) {
				return Board.is_slant_free(x + 1, a - 1, y + 1, true);
			}
			else if( a<x && b<y ) {
				return Board.is_slant_free(a + 1, x - 1, b + 1, true);
			}
			else if(a > x) {
				return Board.is_slant_free(x + 1, a - 1, b + 1, false);
			}
			else {
				return Board.is_slant_free(a + 1, x - 1, y + 1, false);
			}
		}
		return false;
	}
}
