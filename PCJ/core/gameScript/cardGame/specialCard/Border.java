package core.gameScript.cardGame.specialCard;

import core.gameScript.cardGame.Card;

import java.util.List;

public class Border extends Special{

    // --- Start Constructor
    public Border(int number){
        super(number+"");
    }
    // --- End Constructor

    // --- Start Method
    public int position(List<Card> board) {
        if(Integer.parseInt(this.getValue())==0){
            return 0;
        }
        else if(Integer.parseInt(this.getValue())==16) {
            return board.size();
        }
        return -1;
    }

    public String getValue(){
        return super.getNumber();
    }
    // --- End Method
}
