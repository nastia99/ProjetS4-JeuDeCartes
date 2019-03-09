package core.gameScript.cardGame;

import core.gameScript.Exception.WrongNetworkCodeException;
import core.gameScript.cardGame.classicalCard.*;
import core.gameScript.cardGame.specialCard.Acrobat;
import core.gameScript.cardGame.specialCard.Border;
import core.gameScript.cardGame.specialCard.NoValue;

import java.util.List;

public abstract class Card {

    // --- Start Constructor
    public Card(CardColor color, String number) {
        this.cardColor = color;
        this.number = number;
    }
    // --- End Constructor


    // --- Start Attributes
    private CardColor cardColor;
    private String number;
    // --- End Attributes


    // --- Start Methods
    // --- Start Getter Methods
    public String getNumber() {
        return number;
    }

    public CardColor getCardColor(){
        return cardColor;
    }

    public String getColor(){
        return cardColor.getCardColor();
    }

    public int getFly() {
        return cardColor.getFlyCount();
    }
    // --- End Getter Methods

    public boolean equalsToCard(Card otherCard) {

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
        return number.charAt(0)>otherCard.getNumber().charAt(0);
    }

    public abstract int position(List<Card> board);

    public int place(List<Card> board){
        int cardPosition;  
        cardPosition = this.position(board);
        if(cardPosition == -1) {
        	return -1;
        }
        board.add(cardPosition,this);
        return cardPosition;
    }

    public boolean isPlaceable(List<Card> board){
        if(this.position(board)==-1){
            return false;
        }
        return true;
    }

    public static Card convertStringToCard(String netCode) throws WrongNetworkCodeException {
        String[] s=netCode.split("\\.");
        int value=0;
        switch(s[0]) {
            case "V":
                value=Integer.parseInt(s[1]);
                return new ZeroFlieClassical(value);
            case "J" :
                value=Integer.parseInt(s[1]);
                return new OneFlieClassical(value);
            case "O":
                value=Integer.parseInt(s[1]);
                return new TwoFlieClassical(value);
            case "R":
                value=Integer.parseInt(s[1]);
                return new ThreeFlieClassical(value);
            case "S":
                if(s[1].charAt(0)=='R'){
                    int value2= Integer.parseInt(s[1].substring(1));
                    return new NoValue(value2);
                }
                else if(s[1].charAt(s[1].length()-1)=='A'){
                    int value3= Integer.parseInt( s[1].substring(0,1));
                    return new Acrobat(value3);
                }
                else{
                    value= Integer.parseInt(s[1]);
                    return new Border(value);
                }
            default:
                throw new WrongNetworkCodeException();
        }
    }

    public abstract String getValue();

    public String getNetworkCode(){
        return cardColor.getColor()+"."+number;
    }

    public boolean isSpecial() {
        return cardColor.equals(CardColor.S);
    }
    // --- Start Override Method
    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Card){
            Card c=(Card)obj;
            return (c.getFly() == cardColor.getFlyCount() && c.getNumber().equals(number));
        }
        return false;
    }
    // --- End Override Method
    // --- End Methods
}