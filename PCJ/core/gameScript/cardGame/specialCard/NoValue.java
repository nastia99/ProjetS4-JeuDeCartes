package core.gameScript.cardGame.specialCard;

import core.gameScript.cardGame.Card;

import java.util.List;

public class NoValue extends Special {

    // --- Start Constructor
    public NoValue(int number){
        super("R"+number);
    }
    // --- End Constructor


    // --- Start Method
    public int position(List<Card> board) {
        int index=0;
        for(int i=0;i<board.size();i++) {
            if((board.get(i).getValue().equals("R"))) {
                if(Integer.parseInt(board.get(i+1).getValue())-Integer.parseInt(board.get(i-1).getValue()) >2){
                    index =i+1;
                    return index;
                }
                else if((Integer.parseInt(board.get(i+1).getValue())-Integer.parseInt(board.get(i-1).getValue()) ==2)) {
                    index = -1;
                }
            }
            else{
                if(i+1==board.size()) {
                    return -1;
                }
                if( !board.get(i+1).getValue().equals("R")) {
                    if((Integer.parseInt(board.get(i+1).getValue())-Integer.parseInt(board.get(i).getValue()) >=2)) {
                        index= i+1;
                        return index;
                    }
                    else {
                        index=-1;
                    }
                }
                else {
                    if((Integer.parseInt(board.get(i+2).getValue())-Integer.parseInt(board.get(i).getValue()) >=3)) {
                        index= i+2;
                        return index;
                    }
                    else {
                        index=-1;
                    }
                }
            }
        }
        return index;
    }

    public String getValue(){
        return super.getNumber().charAt(0)+"";
    }
    // --- End Method

}

