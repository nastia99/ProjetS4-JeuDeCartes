package core.gameScript.cardGame;

import core.gameScript.Exception.WrongNetworkCodeException;
import core.gameScript.cardGame.classicalCard.Classical;
import core.gameScript.cardGame.classicalCard.ThreeFlieClassical;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DeckTest {

    @BeforeEach
    void setUp() {
        Deck d= new Deck();
    }
    private Deck d= new Deck();

    @Test
    void isEmpty() {
        assertFalse(d.isEmpty());
    }

    @Test
    void isInDeck() throws WrongNetworkCodeException {
        assertTrue(d.isInDeck("V.5"));
    }

    @Test
    void nbCardInDeck() {
        assertTrue(d.nbCardInDeck()==48);
    }

    @Test
    void pickCard() {
        String s=d.pickCard();
        assertTrue(d.nbCardInDeck()==47);
    }

    @Test
    void resetDeck() {
        d.resetDeck();
        assertTrue(d.nbCardInDeck()==48);
    }
}