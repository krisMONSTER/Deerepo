package Structure;

public class player {
	private boolean colour;
	private Piece pickedPiece;
	private DataChanges dataChanges = new DataChanges();

	public player(boolean colour){
		this.colour = colour;
	}

	public void setColour(boolean color) {
		colour = color;
	}

	public boolean getColour() { return colour; }

	public void resetEnPassant() {
		Board.findAndResetEnPassant(colour);
	}
	
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
	
	public Piece getPickedPiece() {
		return pickedPiece;
	}
	
	public boolean isMoveValid(int x, int y) {
		return pickedPiece.isMoveValid(x, y);
	}
	
	public void setDataChanges(int x, int y) {
		pickedPiece.setDataChanges(x, y, dataChanges);
		Board.executeDataChanges(dataChanges);
		//send data_changes to user
		dataChanges.resetDataChanges();
		pickedPiece =null;
	}
}
