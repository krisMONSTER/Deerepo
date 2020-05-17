package Structure;

public class Bishop extends Piece{
	public Bishop(int a, int b, boolean c) {
		super(a,b,c);
	}

	public Piece copy() {
		return new Bishop(x,y,colour);
	}

	public boolean equals(Object o) {
		if (o == this) { return true; }
		if (!(o instanceof Bishop)) { return false; }
		Bishop c = (Bishop) o;
		return this.x == c.x &&
				this.y == c.y &&
				this.colour == c.colour;
	}

	public boolean isMovePossible(int a, int b) {
		if( Math.abs(a-x)==Math.abs(b-y) ) {
			if( a>x && b>y ) {
				return Board.isSlantFree(x + 1, a - 1, y + 1, true);
			}
			else if( a<x && b<y ) {
				return Board.isSlantFree(a + 1, x - 1, b + 1, true);
			}
			else if( a>x && b<y ) {
				return Board.isSlantFree(x + 1, a - 1, b + 1, false);
			}
			else {
				return Board.isSlantFree(a + 1, x - 1, y + 1, false);
			}
		}
		return false;
	}
}
