package card;

public class NoValue extends Card {
	
	// --- Start Constructor
	public NoValue(CardColor color, String number){
		super(color,number);
	}
	// --- End Constructor


	// --- Start Method
	public void placeCard() {
		throw new UnsupportedOperationException();
	}
	// --- End Method

}
