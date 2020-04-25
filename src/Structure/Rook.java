package Structure;

public class Rook extends Piece{
	private boolean castling_ready = true;
	
	public Rook(int a, int b, boolean c) {
		super(a,b,c);
	}

	public void resetCastlingReadiness(){
		castling_ready = false;
	}

	public boolean getCastlingReadiness(){
		return castling_ready;
	}

	public boolean isMovePossible(int a, int b) {
		if(a == x) {
			if(b>y) {
				return Board.isSpaceFree(y + 1, b - 1, x, true);
			}
			else {
				return Board.isSpaceFree(b + 1, y - 1, x, true);
			}
		}
		else if(b == y) {
			if(a>x) {
				return Board.isSpaceFree(x + 1, a - 1, y, false);
			}
			else {
				return Board.isSpaceFree(a + 1, x - 1, y, false);
			}
		}
		return false;
	}

	public void setDataChanges(int a, int b, DataChanges dataChanges) {
		if(!Board.isFieldFree(a, b))
			dataChanges.putAlteration(new Alteration(a, b, TypeOfAlter.capture));
		if(castling_ready)
			dataChanges.putAlteration(new Alteration(x, y, TypeOfAlter.castlingExclusion));
		dataChanges.putAlteration(new Alteration(x, y, a, b));
	}
}
