package Structure;

public class Queen extends Piece{
	public Queen(int a, int b, boolean c) {
		super(a,b,c);
	}

	public Piece copy() {
		return new Queen(x,y,colour);
	}

	public boolean equals(Object o) {
		if (o == this) { return true; }
		if (!(o instanceof Queen)) { return false; }
		Queen c = (Queen) o;
		return this.x == c.x &&
				this.y == c.y &&
				this.colour == c.colour;
	}
	
	public boolean isMovePossible(int a, int b) {
		if(a == x) {
			if(b>y) {
				return Board.isSpaceFree(y + 1, b - 1, x, true);
			}
			else if(b<y) {
				return Board.isSpaceFree(b + 1, y - 1, x, true);
			}
		}
		else if(b == y) {
			if(a>x) {
				return Board.isSpaceFree(x + 1, a - 1, y, false);
			}
			else if(a<x) {
				return Board.isSpaceFree(a + 1, x - 1, y, false);
			}
		}
		else if( Math.abs(a-x)==Math.abs(b-y) ) {
			if( a>x && b>y ) {
				return Board.isSlantFree(x + 1, a - 1, y + 1, true);
			}
			else if( a<x && b<y ) {
				return Board.isSlantFree(a + 1, x - 1, b + 1, true);
			}
			else if(a > x) {
				return Board.isSlantFree(x + 1, a - 1, b + 1, false);
			}
			else {
				return Board.isSlantFree(a + 1, x - 1, y + 1, false);
			}
		}
		return false;
	}
}
