package Structure;

public class Bishop extends Piece{
	public Bishop(int a, int b, boolean c) {
		super(a,b,c);
	}
	
	public boolean is_move_possible(int a, int b) {
		if( Math.abs(a-x)==Math.abs(b-y) ) {
			if( a>x && b>y ) {
				return Board.is_slant_free(x + 1, a - 1, y + 1, true);
			}
			else if( a<x && b<y ) {
				return Board.is_slant_free(a + 1, x - 1, b + 1, true);
			}
			else if( a>x && b<y ) {
				return Board.is_slant_free(x + 1, a - 1, b + 1, false);
			}
			else {
				return Board.is_slant_free(a + 1, x - 1, y + 1, false);
			}
		}
		return false;
	}
}
