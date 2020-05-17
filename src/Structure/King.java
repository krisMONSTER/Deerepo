package Structure;

public class King extends Piece{
	private boolean castling_ready = true;

	public Piece copy() {
		return new King(x,y,colour);
	}

	public King(int a, int b, boolean c) {
		super(a,b,c);
	}

	public boolean equals(Object o) {
		if (o == this) { return true; }
		if (!(o instanceof King)) { return false; }
		King c = (King) o;
		return this.x == c.x &&
				this.y == c.y &&
				this.colour == c.colour;
	}

	public void resetCastlingReadiness(){
		castling_ready = false;
	}

	public boolean isMovePossible(int a, int b) {
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

	private boolean isLineCheckFree(int start, int end, int y){
		for(;start<=end;start++){
			if(Board.isCheckOnField(start, y, colour)){
				return false;
			}
		}
		return true;
	}

	public boolean isMoveValid(int a, int b) {
		if(isMovePossible(a,b) && !Board.isCheckOnField(a, b, colour)) {
			return Board.whatOnField(a, b) != (colour ? 2 : 1);
		}
		else if(castling_ready && b == y){
			if(a == x+2){
				if(Board.whatOnField(7,y) == (colour ? 2 : 1)){
					Piece piece = Board.getPiece(7,y);
					if(piece instanceof Rook && piece.getCastlingReadiness()){
						return isLineCheckFree(x, a, y) && Board.isSpaceFree(5, 6, y, false);
					}
				}
			}
			else if(a == x-2){
				if(Board.whatOnField(0,y) == (colour ? 2 : 1)){
					Piece piece = Board.getPiece(0,y);
					if(piece instanceof Rook && piece.getCastlingReadiness()){
						return isLineCheckFree(a, x, y) && Board.isSpaceFree(1, 3, y, false);
					}
				}
			}
		}
		return false;
	}

	public void setDataChanges(int a, int b, DataChanges dataChanges, ToDisplay toDisplay) {
		toDisplay.addCoordinates(new int[]{x,y});
		toDisplay.addCoordinates(new int[]{a,b});

		if(castling_ready)
			dataChanges.putAlteration(new Alteration(x, y, TypeOfAlter.castlingExclusion));
		if(!Board.isFieldFree(a, b)){
			dataChanges.putAlteration(new Alteration(a, b, TypeOfAlter.remove));
			toDisplay.setTypeOfAction(TypeOfAction.capture);
		}
		else {
			toDisplay.setTypeOfAction(TypeOfAction.move);
		}
		dataChanges.putAlteration(new Alteration(x, y, a, b));
		if(a == x+2){
			dataChanges.putAlteration(new Alteration(7, y, TypeOfAlter.castlingExclusion));
			dataChanges.putAlteration(new Alteration(7, y, a-1, b));

			toDisplay.setTypeOfAction(TypeOfAction.castling);
			toDisplay.addCoordinates(new int[]{7,y});
			toDisplay.addCoordinates(new int[]{a-1,b});
		}
		else if(a == x-2){
			dataChanges.putAlteration(new Alteration(0, y, TypeOfAlter.castlingExclusion));
			dataChanges.putAlteration(new Alteration(0, y, a+1, b));

			toDisplay.setTypeOfAction(TypeOfAction.castling);
			toDisplay.addCoordinates(new int[]{0,y});
			toDisplay.addCoordinates(new int[]{a+1,b});
		}
	}
}
