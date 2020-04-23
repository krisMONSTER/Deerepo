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
	private Piece piece_addition;
	private Piece_position en_passant_inclusion;
	private List<Piece_position> castling_exclusions = new ArrayList<>();
	private List<Piece_coordinates> pieces_to_move = new ArrayList<>();
	private List<Piece_position> pieces_to_remove = new ArrayList<>();
	
	public void put_promotion_move(int x, int y, Piece add) {
		pieces_to_remove.add(new Piece_position(x,y));
		piece_addition = add;
	}
	
	public void put_en_passant_inclusion(int x, int y) {
		en_passant_inclusion = new Piece_position(x,y);
	}

	public void put_castling_exclusion(int x, int y){
		castling_exclusions.add(new Piece_position(x,y));
	}
	
	public void put_piece_removal(int x, int y) {
		pieces_to_remove.add(new Piece_position(x,y));
	}
	
	public void put_piece_movement(int a, int b, int c, int d) {
		pieces_to_move.add(new Piece_coordinates(a,b,c,d));
	}

	public Piece get_piece_addition(){
		return piece_addition;
	}

	public Piece_position get_en_passant_inclusion() {
		return en_passant_inclusion;
	}

	public List<Piece_position> get_castling_exclusions(){
		return castling_exclusions;
	}

	public List<Piece_position> get_pieces_to_remove(){
		return pieces_to_remove;
	}

	public List<Piece_coordinates> get_pieces_to_move(){
		return pieces_to_move;
	}
	
	public void reset_data_changes() {
		piece_addition = null;
		en_passant_inclusion = null;
		castling_exclusions = new ArrayList<>();
		pieces_to_remove = new ArrayList<>();
		pieces_to_move = new ArrayList<>();
	}
}
