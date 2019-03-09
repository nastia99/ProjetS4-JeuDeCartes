package core.gameScript.cardGame.classicalCard;

import core.gameScript.cardGame.Card;
import core.gameScript.cardGame.CardColor;

import java.util.List;

public abstract class Classical extends Card {

	// --- Start Constructor
	public Classical(CardColor color, int number){
		super(color, number+"");
	}
	// --- End Constructor


	// --- Start Method
	public int position(List<Card> board) {
	
		int value= Integer.parseInt(this.getValue()),index=0;
		
		if(board.isEmpty()) {
			return index;
		}
			if(Integer.parseInt(board.get(0).getValue())>value) {
				return index;
			}
			else if(Integer.parseInt(board.get(board.size()-1).getValue())<value){
				return board.size();
			}
	
		return -1;
	}

	public String getValue() {
		return super.getNumber();
	}
	// --- End Method
}