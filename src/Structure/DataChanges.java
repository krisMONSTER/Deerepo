package Structure;

import java.util.ArrayList;
import java.util.List;

enum TypeOfAlter {
	move,
	capture,
	promotion,
	enPassantInclusion,
	castlingExclusion
}

class Alteration{
	private int x, y, newX, newY;
	private Piece newPiece;
	private TypeOfAlter typeOfAlter;
	Alteration(int x, int y, TypeOfAlter typeOfAlter){
		this.x = x;
		this.y = y;
		this.typeOfAlter = typeOfAlter;
	}
	Alteration(int x, int y, int newX, int newY){
		this(x, y, TypeOfAlter.move);
		this.newX = newX;
		this.newY = newY;
	}
	Alteration(int x, int y, Piece newPiece){
		this(x, y, TypeOfAlter.promotion);
		this.newPiece = newPiece;
	}
	public int getX() {
		return x;
	}
	public int getY() {
		return y;
	}
	public int getNewX() {
		return newX;
	}
	public int getNewY() {
		return newY;
	}
	public Piece getNewPiece() {
		return newPiece;
	}
	public TypeOfAlter getTypeOfAlter() {
		return typeOfAlter;
	}
}

public class DataChanges {
	List<Alteration> alterationList = new ArrayList<>();

	public void putAlteration(Alteration alteration){
		alterationList.add(alteration);
	}

	public List<Alteration> getAlterationList(){
		return alterationList;
	}

	public void resetDataChanges(){
		alterationList = new ArrayList<>();
	}
}
