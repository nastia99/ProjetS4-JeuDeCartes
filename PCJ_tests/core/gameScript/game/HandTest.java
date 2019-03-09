package core.gameScript.game;

import core.gameScript.Exception.HandFullException;
import core.gameScript.cardGame.Card;
import core.gameScript.cardGame.classicalCard.OneFlieClassical;
import core.gameScript.cardGame.classicalCard.ThreeFlieClassical;
import core.gameScript.cardGame.classicalCard.TwoFlieClassical;
import core.gameScript.cardGame.specialCard.Border;
import core.gameScript.cardGame.specialCard.NoValue;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class HandTest {

    @Test
    void getCards() {

        Hand h= new Hand();
        List<Card> c = new ArrayList<Card>(5);
        assertEquals(c,h.getCards());

    }

    @Test
    void addCard() throws HandFullException {
        Hand h= new Hand();
        h.addCard(new OneFlieClassical(5));
        assertEquals(1,h.getCards().size());
        h.addCard(new OneFlieClassical(6));
        assertEquals(2,h.getCards().size());
        h.addCard(new TwoFlieClassical(2));
        assertEquals(3,h.getCards().size());
        h.addCard(new NoValue(1));
        assertEquals(4,h.getCards().size());
        h.addCard(new ThreeFlieClassical(9));
        assertEquals(5,h.getCards().size());

    }

    @Test
    void isInList() throws HandFullException {
        Hand h= new Hand();
        h.addCard(new OneFlieClassical(5));
        assertTrue(h.isInList(new OneFlieClassical(5)));
    }

    @Test
    void playCard() throws HandFullException {
        Hand h= new Hand();
        h.addCard(new OneFlieClassical(5));
        assertEquals(1,h.getCards().size());
        h.addCard(new OneFlieClassical(6));
        assertEquals(2,h.getCards().size());
        h.addCard(new TwoFlieClassical(2));
        assertEquals(3,h.getCards().size());
        h.playCard(new TwoFlieClassical(2));
        assertEquals(2,h.getCards().size());
    }


}