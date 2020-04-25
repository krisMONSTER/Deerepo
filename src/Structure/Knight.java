package Structure;

public class Knight extends Piece{
	public Knight(int a, int b, boolean c) {
		super(a,b,c);
	}
	public boolean isMovePossible(int a, int b) {
		if(a == x - 2 || a == x + 2) {
			return b == y + 1 || b == y - 1;
		}
		else if(a == x - 1 || a == x + 1) {
			return b == y + 2 || b == y - 2;
		}
		return false;
	}
}
