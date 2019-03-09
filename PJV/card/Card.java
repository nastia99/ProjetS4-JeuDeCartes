package card;

public abstract class Card {

    // --- Start Constructor
    public Card(CardColor color, String number) {
        this.color= color;
        this.number = number;
    }
    // --- End Constructor


    // --- Start Attributes
    protected CardColor color;
    protected String number;
    // --- End Attributes


    // --- Start Methods
    // --- Start Getter Methods
    public String getNumber() {
        return number;
    }

    public CardColor getColor(){
        return color;
    }

    public int getFly() {
        return color.getFlyCount();
    }
    // --- End Getter Methods

    public boolean equals(Card otherCard) {

        return otherCard.getNumber().equals(number);
    }

    /**
     * @param otherCard
     */
    public boolean lower(Card otherCard) {
        return otherCard.getNumber().charAt(0)<number.charAt(0);
    }

    /**
     * @param otherCard
     */
    public boolean upper(Card otherCard) {
        return otherCard.getNumber().charAt(0)>number.charAt(0);
    }

    public static Card convertStringToCard(String netCode){
        String[] s = netCode.split("\\.");
        switch(s[0]) {
            case "V":
                return new Classical(CardColor.V, s[1]);
            case "J":
                return new Classical(CardColor.J, s[1]);
            case "O":
                return new Classical(CardColor.O, s[1]);
            case "R":
                return new Classical(CardColor.R, s[1]);
            case "S":
                if(s[1].charAt(0)=='R'){
                    return new NoValue(CardColor.S, s[1]);
                }
                else if(s[1].charAt(s[1].length()-1)=='A'){
                    return new Acrobat(CardColor.S, s[1]);
                }
                else{
                    return new Classical(CardColor.S, s[1]);
                }
            default:
            	return null;
        }
    }

    public String getNetworkCode(){
        return color.getColor()+"."+number;
    }
   
    public boolean isSpecial() {
        return color.equals(CardColor.S);
    }

    // --- Start Override Method
    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Card){
            Card c=(Card)obj;
            return (c.getFly() == color.getFlyCount() && c.getNumber().equals(number));
        }
        return false;
    }
    
    @Override
    public String toString() {
    	return number + " de couleur " + color;
    }
    // --- End Override Method
    // --- End Methods

	public int getValue() {
		String str = getNumber().charAt(0)+"";
		if (str.equals("R")) {
			return -1;
		}
		else {
			if (getNumber().length() != 1) {
				if ((getNumber().charAt(1)+"").equals("A")) {
					str = getNumber().charAt(0) + "";
					return Integer.parseInt(str);
				}
			}
			return Integer.parseInt(getNumber());
		}
	}
}
