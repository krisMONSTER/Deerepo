package Structure;

import java.util.concurrent.ArrayBlockingQueue;

public class Player {
	private final String name;
	private final boolean colour;
	private Piece pickedPiece;
	private DataChanges dataChanges = new DataChanges();
	private ToDisplay toDisplay = new ToDisplay();
	private final ArrayBlockingQueue<ToDisplay> display;

	public Player(String name, boolean colour, ArrayBlockingQueue<ToDisplay> display){
		this.name = name;
		this.colour = colour;
		this.display = display;
	}

	public boolean getColour() { return colour; }

	public ToDisplay getToDisplay(){
		return toDisplay;
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
	
	public void makeChanges(int x, int y) {
		pickedPiece.setDataChanges(x, y, dataChanges, toDisplay);
		Board.executeDataChanges(dataChanges);
		try{
			display.put(toDisplay);
		}catch (InterruptedException e){
			e.printStackTrace();
		}
		//DODAJ TO DISPLAY DO DATA CHANGES
		//send data_changes to player
		dataChanges = new DataChanges();
		toDisplay = new ToDisplay();
		pickedPiece = null;
	}
}
