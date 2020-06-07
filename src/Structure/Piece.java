package Structure;

import java.util.ArrayList;
import java.util.List;

abstract public class Piece {

	protected int x; // position x
	protected int y; // position y
	protected boolean colour; // black = 0, white = 1
	
	Piece(int a, int b, boolean c){
		x = a;
		y = b;
		colour = c;
	}

	abstract public boolean equals(Object o);

	abstract public boolean isMovePossible(int a, int b);

	abstract public Piece copy();
	
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

	public boolean isAbleToMove(){
		for(int i = 1; i>=-1 && (y+i)>=0; i--){
			if(y+i>7)
				continue;
			for(int ii = -1; ii<=1 && (x+ii)<=7; ii++){
				if(x+ii<0)
					continue;
				if(isMoveValid(x+ii,y+i))
					return true;
			}
		}
		return false;
	}

	public boolean endangersField(int a, int b){
		return isMovePossible(a, b);
	}

	public void setDataChanges(int a, int b, DataChanges dataChanges, ToDisplay toDisplay) {
		toDisplay.addCoordinates(new int[]{x,y});
		toDisplay.addCoordinates(new int[]{a,b});

		if(!Board.isFieldFree(a, b)){
			dataChanges.putAlteration(new Alteration(a, b, TypeOfAlter.remove));
			toDisplay.setTypeOfAction(TypeOfAction.capture);
		}
		else{
			toDisplay.setTypeOfAction(TypeOfAction.move);
		}
		dataChanges.putAlteration(new Alteration(x, y, a, b));
	}

	public List<int[]> getPossiblePositions() {

		List<int[]> ret = new ArrayList<>();

		for(int x = 0; x < 8; x++) {
			for(int y = 0; y < 8; y++) {
				if ((this.x != x && this.y != y) && (isMoveValid(x,y) )) {
					ret.add(new int[]{x,y});
				}
			}
		}

		return ret;
	}

}
