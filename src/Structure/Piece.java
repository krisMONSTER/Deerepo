package Structure;

abstract public class Piece {
	protected int x; // position x
	protected int y; // position y
	protected boolean colour; // black = 0, white = 1
	
	Piece(int a, int b, boolean c){
		x = a;
		y = b;
		colour = c;
	}
	
	abstract public boolean is_move_possible(int a, int b);
	
	public void set_x(int a) {
		x = a;
	}
	
	public void set_y(int a) {
		y = a;
	}
	
	public void set_en_passant(boolean x) {}
	
	public int get_x() {
		return x;
	}
	
	public int get_y() {
		return y;
	}
	
	public boolean get_colour() {
		return colour;
	}
	
	public boolean get_en_passant() {
		return false;
	}
	
	boolean is_move_valid(int a, int b) {
		if(is_move_possible(a,b)) {
			return Board.what_on_field(a, b) != (colour ? 2 : 1);
		}
		else {
			return false;
		}
	}

	public boolean endangers_filed(int a, int b){
		return is_move_possible(a, b);
	}

	public void set_data_changes(int a, int b, Data_changes data_changes) {
		if(!Board.is_field_free(a, b))
			data_changes.put_remove(a, b);
		data_changes.put_move(x, y, a, b);
	}
}
