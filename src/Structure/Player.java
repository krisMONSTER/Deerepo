package Structure;

public class Player {
	private String name;
	private boolean colour;
	private Piece pickedPiece;
	private DataChanges dataChanges = new DataChanges();

	public Player(String name, boolean colour){
		this.name = name;
		this.colour = colour;
	}

	public boolean getColour() { return colour; }
	
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
