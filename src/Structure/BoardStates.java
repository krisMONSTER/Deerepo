package Structure;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

class BoardState {
    private int amount;
    private final List<Piece> state;

    public BoardState(List<Piece> state) {
        this.state = state;
        amount = 1;
    }

    public void stackState() {
        amount++;
    }

    public int getAmountofState(){
        return amount;
    }

    public List<Piece> getState() {
        return state;
    }
}

public class BoardStates {
    private List<BoardState> boardStates = new ArrayList<>();
    private int halfMoveClock = 0;

    public void incrementHalfMoveClock(){
        halfMoveClock++;
    }

    public void resetHalfMoveClock(){
        halfMoveClock = 0;
    }

    public int getHalfMoveClock() {
        return halfMoveClock;
    }

    public void addState(List<Piece> addedState) {
        for(BoardState boardState : boardStates) {
            if(addedState.equals(boardState.getState())) {
                boardState.stackState();
                return;
            }
        }
        List<Piece> clone = new ArrayList<>(addedState.size());
        for(Piece piece : addedState){
            clone.add(piece.copy());
        }
        boardStates.add(new BoardState(clone));
    }

    public void resetBoardStates(){
        boardStates = new ArrayList<>();
    }

    public boolean stackOfThreePresence(){
        for(BoardState boardState : boardStates){
            if(boardState.getAmountofState()>=3){
                return true;
            }
        }
        return false;
    }

    public void test(){
        System.out.println("halfMoveClock:"+halfMoveClock);
        System.out.println("stackedStates:");
        for(BoardState boardState : boardStates){
            System.out.println(boardState.getAmountofState());
        }
    }
}