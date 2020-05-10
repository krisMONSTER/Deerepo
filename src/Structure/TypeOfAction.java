package Structure;

/*
0 - x0, y1
1 - x1, y1
.
.
.
x - xn, yn
*/

public enum TypeOfAction {
    nothing,    //brak danych
    pick,       //zaznacz 0
    repick,     //odznacz 0, zaznacz 1
    clear,      //odznacz 0
    move,       //ruch z 0 na 1
    capture,    //ruch z 0 na 1, zbij 1
    enPassant,  //ruch z 0 na 1, zbij 2
    castling,   //ruch z 0 na 1, ruch z 2 na 3
    promotionToBishop,      //ruch z 0 na 1, nowy bishop na 1
    promotionToKnight,      //ruch z 0 na 1, nowy knight na 1
    promotionToRook,        //ruch z 0 na 1, nowy rook na 1
    promotionToQueen,       //ruch z 0 na 1, nowy queen na 1
    promotionToBishopWithCapture,   //ruch z 0 na 1, zbij 1, nowy bishop na 1
    promotionToKnightWithCapture,   //ruch z 0 na 1, zbij 1, nowy bishop na 1
    promotionToRookWithCapture,     //ruch z 0 na 1, zbij 1, nowy bishop na 1
    promotionToQueenWithCapture,    //ruch z 0 na 1, zbij 1, nowy bishop na 1
}
