package core.gameScript.cardGame.specialCard;

import core.gameScript.cardGame.Card;

import java.util.List;

public class Acrobat extends Special{

	// --- Start Constructor
	public Acrobat(int number){
		super(number+"A");
	}
	// --- End Constructor


	// --- Start Method
	public int position(List<Card> board) {
		for(Card c : board) {
			if(c.getValue().equals("7") && this.getValue().equals("7")){
				return board.indexOf(c)+1;
			}
			if( c.getValue().equals("9") && this.getValue().equals("9")) {
				return board.indexOf(c)+1;
			}
		}
		return -1;
	}

	public String getValue(){
		return super.getNumber().charAt(0)+"";
	}
	// --- End Method

}