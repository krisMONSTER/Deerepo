package Structure;

public class King extends Piece{
	private boolean is_moved = false;
	
	public King(int a, int b, boolean c) {
		super(a,b,c);
	}
	
	public boolean is_move_possible(int a, int b) {
		if(a == x+1 || a == x-1) {
			return b == y + 1 ||
					b == y ||
					b == y - 1;
		}
		else if(b == y+1 || b == y-1) {
			return a == x;
		}
		return false;
	}

	boolean is_move_valid(int a, int b) {
		if(is_move_possible(a,b)) {
			return Board.what_on_field(a, b) != (colour ? 2 : 1);
		}
		/*else if(!is_moved){
			if(b == y) {
				if(a == x+2){
					if(Piece piece = Board.get_piece()){

					}
				}
				else if(a == x-2){

				}
			}
		}*/
		else {
			return false;
		}
	}
}
