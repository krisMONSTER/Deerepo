package Structure;

import java.util.List;
import java.util.ArrayList;

class Piece_position {
	protected int x;
	protected int y;
	Piece_position(int a, int b){
		x = a;
		y = b;
	}
	public int get_x() {
		return x;
	}
	public int get_y() {
		return y;
	}
}

class Piece_coordinates extends Piece_position {
	private int new_x;
	private int new_y;
	Piece_coordinates(int a, int b, int c, int d){
		super(a,b);
		new_x = c;
		new_y = d;
	}
	public int get_new_x() {
		return new_x;
	}
	public int get_new_y() {
		return new_y;
	}
}

public class Data_changes {
	private Piece add_piece;
	private Piece_position en_passant;
	private List<Piece_position> castling = new ArrayList<>();
	private List<Piece_coordinates> moved_pieces = new ArrayList<>();
	private List<Piece_position> remove_pieces = new ArrayList<>();
	
	public void put_exchange(int x, int y, Piece add) {
		remove_pieces.add(new Piece_position(x,y));
		add_piece = add;
	}
	
	public void put_en_passant(int x, int y) {
		en_passant = new Piece_position(x,y);
	}

	public void put_castling(int x, int y){
		castling.add(new Piece_position(x,y));
	}
	
	public void put_remove(int x, int y) {
		remove_pieces.add(new Piece_position(x,y));
	}
	
	public void put_move(int a, int b, int c, int d) {
		moved_pieces.add(new Piece_coordinates(a,b,c,d));
	}

	public Piece get_add_piece(){
		return add_piece;
	}

	public Piece_position get_en_passant() {
		return en_passant;
	}

	public List<Piece_position> get_castling(){
		return castling;
	}

	public List<Piece_position> get_remove_pieces(){
		return remove_pieces;
	}

	public List<Piece_coordinates> get_moved_pieces(){
		return moved_pieces;
	}
	
	public void reset_data_changes() {
		add_piece = null;
		en_passant = null;
		castling = new ArrayList<>();
		remove_pieces = new ArrayList<>();
		moved_pieces = new ArrayList<>();
	}
}
