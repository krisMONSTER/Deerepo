package Structure;

abstract public class Piece {
	protected int x; // position x
	protected int y; // position y
	protected boolean colour; // black = 0, white = 1
	
	Piece(int a, int b, boolean c){
		x = a;
		y = b;
		colour = c;
	}
	
	abstract public boolean isMovePossible(int a, int b);
	
	public void setX(int a) {
		x = a;
	}
	
	public void setY(int a) {
		y = a;
	}
	
	public void setEnPassant(boolean x) {}

	public void resetCastlingReadiness() {}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public boolean getColour() {
		return colour;
	}
	
	public boolean getEnPassant() {
		return false;
	}

	public boolean getCastlingReadiness(){ return false; }
	
	public boolean isMoveValid(int a, int b) {
		if(isMovePossible(a,b)) {
			return Board.whatOnField(a, b) != (colour ? 2 : 1);
		}
		else {
			return false;
		}
	}

	public boolean endangersField(int a, int b){
		return isMovePossible(a, b);
	}

	public void setDataChanges(int a, int b, DataChanges dataChanges) {
		if(!Board.isFieldFree(a, b))
			dataChanges.putAlteration(new Alteration(a, b, TypeOfAlter.capture));
		dataChanges.putAlteration(new Alteration(x, y, a, b));
	}
}
