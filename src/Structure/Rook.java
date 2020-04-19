package Structure;

public class Rook extends Piece{
	private boolean is_moved = false;
	
	public Rook(int a, int b, boolean c) {
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
		return false;
	}

}
