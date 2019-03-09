package core.gameScript.game;

import core.gameScript.Exception.HandFullException;
import core.gameScript.cardGame.Card;
import core.gameScript.cardGame.classicalCard.ThreeFlieClassical;
import core.gameScript.cardGame.classicalCard.TwoFlieClassical;
import core.gameScript.cardGame.specialCard.Border;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {

    @Test
    void getPort() {
        Player p = new Player(145,"Michel", "type");
        assertEquals(145,p.getPort());
    }

    @Test
    void getName() {
        Player p = new Player(145,"Michel", "type");
        assertEquals("Michel",p.getName());
    }

    @Test
    void getType() {
        Player p = new Player(145,"Michel", "type");
        assertEquals("type",p.getType());
    }

    @Test
    void getPoints() {
        Player p = new Player(145,"Michel", "type");
        assertEquals(0,p.getPoints());
    }

    @Test
    void setType() {
        Player p = new Player(145,"Michel", "type");
        assertEquals("type",p.getType());
        p.setType("type2");
        assertEquals("type2",p.getType());
    }

    @Test
    void getHand() {
        Player p = new Player(145,"Michel", "type");
        List<Card> c= new ArrayList<Card>();
        assertEquals(c,p.getHand());
    }

    @Test
    void addCard() throws HandFullException {
        Player p = new Player(145,"Michel", "type");
        List<Card> c= new ArrayList<Card>();
        assertEquals(c,p.getHand());
        p.addCard(new Border(16));
        c.add(new Border(16));
        assertEquals(c,p.getHand());
    }

    @Test
    void isInHand() throws HandFullException {
        Player p = new Player(145,"Michel", "type");
        p.addCard(new Border(16));
        assertTrue(p.isInHand(new Border(16)));
    }

    @Test
    void playCard() throws HandFullException {
        Player p = new Player(145,"Michel", "type");
        List<Card> c= new ArrayList<Card>();
        assertEquals(c,p.getHand());
        p.addCard(new Border(16));
        p.addCard(new TwoFlieClassical(6));
        p.addCard(new ThreeFlieClassical(9));
        p.playCard(new ThreeFlieClassical(9));
        assertEquals(2,p.getHand().size());
    }

    @Test
    void addFly() {
        Player p = new Player(145,"Michel", "type");
        p.addFly(15);
        assertEquals(15,p.getNbFly());
    }

    @Test
    void convertFlyToPoint() {
        Player p = new Player(145,"Michel", "type");
        p.addFly(15);
        p.convertFlyToPoint();
        assertEquals(15,p.getPoints());
    }
}
