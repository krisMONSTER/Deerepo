package Structure;

import java.util.List;
import java.util.ArrayList;

class Piece_for_utilization{
	protected int x;
	protected int y;
	Piece_for_utilization(int a, int b){
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

class Moved_piece extends Piece_for_utilization{
	private int new_x;
	private int new_y;
	Moved_piece(int a, int b, int c, int d){
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
	private Piece_for_utilization en_passant;
	private List<Moved_piece> moved_pieces = new ArrayList<>();
	private List<Piece_for_utilization> remove_pieces = new ArrayList<>();
	
	public void put_exchange(int x, int y, Piece add) {
		remove_pieces.add(new Piece_for_utilization(x,y));
		add_piece = add;
	}
	
	public void put_en_passant(int x, int y) {
		en_passant = new Piece_for_utilization(x,y);
	}
	
	public void put_remove(int x, int y) {
		remove_pieces.add(new Piece_for_utilization(x,y));
	}
	
	public void put_move(int a, int b, int c, int d) {
		moved_pieces.add(new Moved_piece(a,b,c,d));
	}
	
	public List<Moved_piece> get_moved_pieces(){
		return moved_pieces;
	}
	
	public List<Piece_for_utilization> get_remove_pieces(){
		return remove_pieces;
	}
	
	public Piece get_add_piece(){
		return add_piece;
	}
	
	public Piece_for_utilization get_en_passant() {
		return en_passant;
	}
	
	public void reset_data_changes() {
		add_piece = null;
		en_passant = null;
		moved_pieces = new ArrayList<>();
		remove_pieces = new ArrayList<>();
	}
}
