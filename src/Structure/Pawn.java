package Structure;

import java.util.Objects;

public class Pawn extends Piece{
	private boolean enPassant;

	public Pawn(int a, int b, boolean c) {
		super(a,b,c);
		enPassant = false;
	}

	public Piece copy() {
		return new Pawn(x,y,colour);
	}

	public boolean equals(Object o) {
		if (o == this) { return true; }
		if (!(o instanceof Pawn)) { return false; }
		Pawn c = (Pawn) o;
		return this.x == c.x &&
				this.y == c.y &&
				this.colour == c.colour;
	}
	
	public boolean getEnPassant() {
		return enPassant;
	}
	
	public void setEnPassant(boolean x) {
		enPassant = x;
	}
	
	public boolean isMovePossible(int a, int b) {
		if(a == x) {
			if(colour) {
				if( b == y+2 && Board.isFieldFree(x, y+1) && y == 1 ) {
					return true;
				}
				return b == y + 1;
			}
			else {
				if( b == y-2 && Board.isFieldFree(x, y-1) && y == 6 ) {
					return true;
				}
				return b == y - 1;
			}
		}
		else if(a == x+1 || a == x-1) {
			return (colour && b == y + 1) || (!colour && b == y - 1);
		}
		return false;
	}
	
	public boolean isMoveValid(int a, int b) {
		if(isMovePossible(a,b)) {
			if(a == x) {
				return Board.isFieldFree(a, b);
			}
			else {
				if(Board.whatOnField(a, b) == (colour?1:2)) {
					return true;
				}
				else if(Board.isFieldFree(a, b)) {
					if(Board.whatOnField(a, y) == (colour?1:2)) {
						return Objects.requireNonNull(Board.getPiece(a, y)).getEnPassant();
					}
				}
			}
		}
		return false;
	}

	public boolean endangersField(int a, int b){
		if(isMovePossible(a, b)){
			return a != x;
		}
		else {
			return false;
		}
	}

	public void setDataChanges(int a, int b, DataChanges dataChanges, ToDisplay toDisplay) {
		toDisplay.addCoordinates(new int[]{x,y});
		toDisplay.addCoordinates(new int[]{a,b});

		if(!Board.isFieldFree(a, b)) {
			dataChanges.putAlteration(new Alteration(a, b, TypeOfAlter.remove));
			toDisplay.setTypeOfAction(TypeOfAction.capture);
		}
		else if(a!=x) {
			dataChanges.putAlteration(new Alteration(a, y, TypeOfAlter.remove));
			toDisplay.addCoordinates(new int[]{a,y});
			toDisplay.setTypeOfAction(TypeOfAction.enPassant);
		}
		else if(Math.abs(b-y)>1) {
			dataChanges.putAlteration(new Alteration(x, y, TypeOfAlter.enPassantInclusion));
			toDisplay.setTypeOfAction(TypeOfAction.move);
		}
		else {
			toDisplay.setTypeOfAction(TypeOfAction.move);
		}
		dataChanges.putAlteration(new Alteration(x, y, a, b));
		if(b == 7 || b == 0) {
			dataChanges.putAlteration(new Alteration(a, b, new Queen(a, b, colour)));
			if(!Board.isFieldFree(a, b)){
				toDisplay.setTypeOfAction(TypeOfAction.promotionToQueenWithCapture);
			}
			else{
				toDisplay.setTypeOfAction(TypeOfAction.promotionToQueen);
			}
		}
	}
	
}
