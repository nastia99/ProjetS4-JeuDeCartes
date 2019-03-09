package core.gameScript.cardGame;

import core.gameScript.Exception.WrongNetworkCodeException;
import core.gameScript.cardGame.classicalCard.OneFlieClassical;
import core.gameScript.cardGame.classicalCard.ThreeFlieClassical;
import core.gameScript.cardGame.classicalCard.TwoFlieClassical;
import core.gameScript.cardGame.classicalCard.ZeroFlieClassical;
import core.gameScript.cardGame.specialCard.Acrobat;
import core.gameScript.cardGame.specialCard.Border;
import core.gameScript.cardGame.specialCard.NoValue;

import static org.junit.jupiter.api.Assertions.*;

class CardTest {

    @org.junit.jupiter.api.BeforeEach
    void setUp() {
        Card c= new ThreeFlieClassical(9);
    }

    @org.junit.jupiter.api.Test
    void getNumber() {
        Card c= new ThreeFlieClassical(9);
        assertEquals(9+"",c.getNumber());
    }

    @org.junit.jupiter.api.Test
    void getColor() {
        Card c= new ThreeFlieClassical(9);
        assertEquals(CardColor.R,c.getCardColor());
    }

    @org.junit.jupiter.api.Test
    void getFly() {
        Card c= new ThreeFlieClassical(9);
        assertEquals(3,c.getFly());
    }

    @org.junit.jupiter.api.Test
    void equals() {
        Card c1= new ThreeFlieClassical(9);
        Card c2= new TwoFlieClassical(9);
        assertEquals(true,c1.equalsToCard(c2));
    }

    @org.junit.jupiter.api.Test
    void lower() {
        Card c1= new ThreeFlieClassical(9);
        Card c2= new TwoFlieClassical(12);
        assertEquals(true,c1.lower(c2));
    }

    @org.junit.jupiter.api.Test
    void upper() {
        Card c1= new ThreeFlieClassical(9);
        Card c2= new TwoFlieClassical(5);
        assertEquals(true,c1.upper(c2));
    }

    @org.junit.jupiter.api.Test
    void placeCard() {
    }

    @org.junit.jupiter.api.Test
    void convertStringToCard() throws WrongNetworkCodeException {
        Card c1= new ThreeFlieClassical(9);
        assertTrue(c1.equalsToCard(Card.convertStringToCard("R.9")));
        c1 = new OneFlieClassical(8);
        assertTrue(c1.equalsToCard(Card.convertStringToCard("J.8")));
        c1 = new TwoFlieClassical(8);
        assertTrue(c1.equalsToCard(Card.convertStringToCard("O.8")));
        c1 = new ZeroFlieClassical(8);
        assertTrue(c1.equalsToCard(Card.convertStringToCard("V.8")));
        c1 = new Border(16);
        assertTrue(c1.equalsToCard(Card.convertStringToCard("S.16")));
        c1 = new NoValue(1);
        assertTrue(c1.equalsToCard(Card.convertStringToCard("S.R1")));
        c1 = new Acrobat(7);
        assertTrue(c1.equalsToCard(Card.convertStringToCard("S.7A")));
    }

    @org.junit.jupiter.api.Test
    void getNetworkCode() {
        Card c1= new ThreeFlieClassical(9);
        assertTrue(c1.getNetworkCode().equals("R.9"));
        c1 = new OneFlieClassical(8);
        assertTrue(c1.getNetworkCode().equals("J.8"));
        c1 = new TwoFlieClassical(8);
        assertTrue(c1.getNetworkCode().equals("O.8"));
        c1 = new ZeroFlieClassical(8);
        assertTrue(c1.getNetworkCode().equals("V.8"));
        c1 = new Border(16);
        assertTrue(c1.getNetworkCode().equals("S.16"));
        c1 = new NoValue(1);
        assertTrue(c1.getNetworkCode().equals("S.R1"));
        c1 = new Acrobat(7);
        assertTrue(c1.getNetworkCode().equals("S.7A"));
    }

    @org.junit.jupiter.api.Test
    void equals1() {
        Card c1= new ThreeFlieClassical(9);
        Card c2= new TwoFlieClassical(9);
        assertEquals(false,c1.equals(c2));
    }
}