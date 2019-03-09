package core.gameScript.cardGame;

import core.gameScript.cardGame.classicalCard.OneFlieClassical;
import core.gameScript.cardGame.classicalCard.ThreeFlieClassical;
import core.gameScript.cardGame.classicalCard.TwoFlieClassical;
import core.gameScript.cardGame.classicalCard.ZeroFlieClassical;
import core.gameScript.cardGame.specialCard.Acrobat;
import core.gameScript.cardGame.specialCard.Border;
import core.gameScript.cardGame.specialCard.NoValue;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CardFactoryTest {

    @Test
    void createCard() {
        CardFactory cf = new CardFactory();
        assertEquals(null,cf.createCard("", 0));
        assertEquals(null,cf.createCard("Classical", 5));
        assertEquals(new ZeroFlieClassical(9),cf.createCard("ZeroFlieClassical", 9));
        assertEquals(new OneFlieClassical(9),cf.createCard("OneFlieClassical", 9));
        assertEquals(new TwoFlieClassical(9),cf.createCard("TwoFlieClassical", 9));
        assertEquals(new ThreeFlieClassical(9),cf.createCard("ThreeFlieClassical", 9));
        assertEquals(new NoValue(1),cf.createCard("NoValue", 1));
        assertEquals(new Acrobat(9),cf.createCard("Acrobat", 9));
        assertEquals(new Border(16),cf.createCard("Border", 16));
    }
}