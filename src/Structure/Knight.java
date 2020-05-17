package Structure;

public class Knight extends Piece{
	public Knight(int a, int b, boolean c) {
		super(a,b,c);
	}

	public Piece copy() {
		return new Knight(x,y,colour);
	}

	public boolean equals(Object o) {
		if (o == this) { return true; }
		if (!(o instanceof Knight)) { return false; }
		Knight c = (Knight) o;
		return this.x == c.x &&
				this.y == c.y &&
				this.colour == c.colour;
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

	public boolean isAbleToMove() {
		if(y+1<=7){
			if(x+2<=7){
				if(isMoveValid(x+2,y+1)){
					return true;
				}
			}
			if(x-2>=0){
				if(isMoveValid(x-2,y+1)){
					return true;
				}
			}
			if(y+2<=7){
				if(x+1<=7){
					if(isMoveValid(x+1,y+2)){
						return true;
					}
				}
				if(x-1>=0){
					if(isMoveValid(x-1,y+2)){
						return true;
					}
				}
			}
		}
		if(y-1>=0){
			if(x+2<=7){
				if(isMoveValid(x+2,y-1)){
					return true;
				}
			}
			if(x-2>=0){
				if(isMoveValid(x-2,y-1)){
					return true;
				}
			}
			if(y-2>=0){
				if(x+1<=7){
					if(isMoveValid(x+1,y-2)){
						return true;
					}
				}
				if(x-1>=0){
					return isMoveValid(x - 1, y - 2);
				}
			}
		}
		return false;
	}
}
