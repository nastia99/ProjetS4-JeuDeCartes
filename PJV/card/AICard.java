package card;

public class AICard extends Card {
	
	// --- Start Attributes
	private double aiValue;
	// --- End Attributes
	
	// --- Start Constructor
	public AICard(Card c) {
		super(c.getColor(), c.getNumber());
		aiValue = 0;
	}
	
	public AICard(CardColor color, String number) {
		super(color, number);
		aiValue = 0;
	}
	// --- End Constructor
	
	public AICard(int value, CardColor color, String number) {
		super(color, number);
		this.aiValue = value; 
	}

	// --- Start Methods
	public void updateAIValue(int nbPt) {
		aiValue += nbPt;
	}	
	// --- Start Setters & Getters
	// --- Start Setters 
	public void setAIValue(double value) {
		this.aiValue = value;
	}
	// --- End Setters
	// --- Start Getters
	public double getAIValue() {
		return aiValue;
	}
	// --- End Getters
	// --- End Setters & Getters	
	// --- End Methods

	public boolean isColor(String type) {
		if (color.equals(Card.convertStringToCard(type).getColor())) {
			return false;
		}
		return false;
	}

}
