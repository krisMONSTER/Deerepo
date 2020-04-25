package Structure;

import java.util.Objects;

public class Pawn extends Piece{
	private boolean en_passant;
	public Pawn(int a, int b, boolean c) {
		super(a,b,c);
		en_passant = false;
	}
	
	public boolean getEnPassant() {
		return en_passant;
	}
	
	public void setEnPassant(boolean x) {
		en_passant = x;
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

	public void setDataChanges(int a, int b, DataChanges dataChanges) {
		if(!Board.isFieldFree(a, b))
			dataChanges.putAlteration(new Alteration(a, b, TypeOfAlter.capture));
		else if(a!=x)
			dataChanges.putAlteration(new Alteration(a, y, TypeOfAlter.capture));
		else if(Math.abs(b-y)>1)
			dataChanges.putAlteration(new Alteration(x, y, TypeOfAlter.enPassantInclusion));
		dataChanges.putAlteration(new Alteration(x, y, a, b));
	}
	
}
