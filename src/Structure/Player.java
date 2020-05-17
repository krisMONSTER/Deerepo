package Structure;


public class Player {
	private final boolean colour;
	private Piece pickedPiece;

	public Player(boolean colour){
		this.colour = colour;
	}
	//ZAMIEN NA PRIVATE
	public boolean pick(int x, int y) {
		pickedPiece = Board.getPiece(x,y);
		if(pickedPiece == null)
			return false;
		else if(pickedPiece.getColour() == colour) {
			return true;
		}
		else {
			pickedPiece = null;
			return false;
		}
	}

	public ClickResult performOnClick(int x, int y){
		if(pickedPiece==null) {
			if(pick(x, y))
				return ClickResult.pick;
			else
				return ClickResult.nothing;
		}
		else {
			if(pickedPiece.isMoveValid(x, y)) {
				return ClickResult.move;
			}
			else {
				if(pick(x, y))
					return ClickResult.repick;
				else
					return ClickResult.clear;
			}
		}
	}
	
	public Piece getPickedPiece() {
		return pickedPiece;
	}
	
	public boolean isMoveValid(int x, int y) {
		return pickedPiece.isMoveValid(x, y);
	}
	
	public void makeChanges(int x, int y, DataChanges dataChanges, ToDisplay toDisplay) {
		pickedPiece.setDataChanges(x, y, dataChanges, toDisplay);
		dataChanges.setToDisplay(toDisplay);
		pickedPiece = null;
		Board.findAndResetEnPassant(!colour);
	}
}