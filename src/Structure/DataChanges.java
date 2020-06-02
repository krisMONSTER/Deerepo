package Structure;

import java.util.ArrayList;
import java.util.List;
import java.io.Serializable;

enum TypeOfAlter {
	move,
	remove,
	promotion,
	enPassantInclusion,
	castlingExclusion
}

class Alteration implements Serializable {
	private final int x;
	private final int y;
	private int newX;
	private int newY;
	private Piece newPiece;
	private final TypeOfAlter typeOfAlter;
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

	@Override
	public String toString() {
		return "Alteration{" +
				"x=" + x +
				", y=" + y +
				", newX=" + newX +
				", newY=" + newY +
				", newPiece=" + newPiece +
				", typeOfAlter=" + typeOfAlter +
				'}';
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

public class DataChanges implements Serializable {
	private final List<Alteration> alterationList = new ArrayList<>();


	public void putAlteration(Alteration alteration){
		alterationList.add(alteration);
	}

	public List<Alteration> getAlterationList(){
		return alterationList;
	}

	public void test() {
		alterationList.add(new Alteration(1, 1, TypeOfAlter.move));
	}

	@Override
	public String toString() {
		return "DataChanges{" +
				"alterationList=" + alterationList +
				'}';
	}
}
