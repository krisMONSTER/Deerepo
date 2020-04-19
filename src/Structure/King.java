package Structure;

public class King extends Piece{
	private boolean is_moved = false;
	
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
}
