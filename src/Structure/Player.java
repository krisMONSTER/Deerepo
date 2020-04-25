package Structure;

public class Player {
	private static boolean colour;
	private static Piece pickedPiece;
	private static DataChanges dataChanges = new DataChanges();
	
	public static void setColour(boolean color) {
		colour = color;
	}
	
	public static void resetEnPassant() {
		Board.findAndResetEnPassant(colour);
	}
	
	public static boolean pick(int x, int y) {
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
	
	public static Piece getPickedPiece() {
		return pickedPiece;
	}
	
	public static boolean isMoveValid(int x, int y) {
		return pickedPiece.isMoveValid(x, y);
	}
	
	public static void setDataChanges(int x, int y) {
		pickedPiece.setDataChanges(x, y, dataChanges);
		Board.executeDataChanges(dataChanges);
		//send data_changes to user
		dataChanges.resetDataChanges();
		pickedPiece =null;
	}
}
