package Structure;

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
    private List<BoardState> boardStates = new LinkedList<>();
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
        boardStates.add(new BoardState(addedState));
    }

    public void resetBoardStates(){
        boardStates = new LinkedList<>();
    }

    public boolean stackOfThreePresence(){
        for(BoardState boardState : boardStates){
            if(boardState.getAmountofState()==3){
                return true;
            }
        }
        return false;
    }
}