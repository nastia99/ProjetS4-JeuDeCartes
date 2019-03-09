package core.gameScript.game;

import core.gameScript.Exception.HandFullException;
import core.gameScript.cardGame.classicalCard.ThreeFlieClassical;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RoundTest {

    @Test
    void getRoundID() {
        List<Player> p = new ArrayList<Player>();
        Round r = new Round(1,p,3);
        assertEquals(1,r.getRoundID());
    }

    @Test
    void getTurn() {
        List<Player> p = new ArrayList<Player>();
        Round r = new Round(1,p,3);
        assertEquals(1,r.getTurn());
    }

    @Test
    void getCurrentPlayer() {
        List<Player> p = new ArrayList<Player>();
        Round r = new Round(1,p,3);
        assertEquals(3,r.getCurrentPlayer());
    }

    @Test
    void getDirection() {
        List<Player> p = new ArrayList<Player>();
        Round r = new Round(1,p,3);
        assertTrue(r.getDirection());
    }

    @Test
    void setSleeveID() {
        List<Player> p = new ArrayList<Player>();
        Round r = new Round(1,p,3);
        assertEquals(1,r.getRoundID());
        r.setSleeveID(2);
        assertEquals(2,r.getRoundID());
    }

    @Test
    void isInCurrentPlayerHand() throws HandFullException {
        List<Player> p = new ArrayList<Player>();
        Player p1 = new Player(545,"Kevin","kikoo");
        Player p2 = new Player(545,"Dylan","kikoo");
        Player p3 = new Player(545,"Arthur","kikoo");
        p1.addCard(new ThreeFlieClassical(9));
        p.add(p1);
        p.add(p2);
        p.add(p3);
        Round r = new Round(1,p,0);
        assertTrue(r.isInCurrentPlayerHand(new ThreeFlieClassical(9)));
    }

    @Test
    void currentPlayerPlayCard() throws HandFullException {
        List<Player> p = new ArrayList<Player>();
        Player p1 = new Player(545,"Kevin","kikoo");
        Player p2 = new Player(545,"Dylan","kikoo");
        Player p3 = new Player(545,"Arthur","kikoo");
        p1.addCard(new ThreeFlieClassical(9));
        p.add(p1);
        p.add(p2);
        p.add(p3);
        Round r = new Round(1,p,1);
        r.currentPlayerPlayCard(new ThreeFlieClassical(9));
        assertFalse(r.isInCurrentPlayerHand(new ThreeFlieClassical(9)));
    }

    @Test
    void currentPlayerAddCard() throws HandFullException {
        List<Player> p = new ArrayList<Player>();
        Player p1 = new Player(545,"Kevin","kikoo");
        Player p2 = new Player(545,"Dylan","kikoo");
        Player p3 = new Player(545,"Arthur","kikoo");
        p.add(p1);
        p.add(p2);
        p.add(p3);
        Round r = new Round(1,p,1);
        r.currentPlayerAddCard(new ThreeFlieClassical(9));
        assertTrue(r.isInCurrentPlayerHand(new ThreeFlieClassical(9)));
    }

    @Test
    void nextPlayer() {
        List<Player> p = new ArrayList<Player>();
        Player p1 = new Player(545,"Kevin","kikoo");
        Player p2 = new Player(545,"Dylan","kikoo");
        Player p3 = new Player(545,"Arthur","kikoo");
        p.add(p1);
        p.add(p2);
        p.add(p3);
        Round r = new Round(1,p,1);
        r.nextPlayer();
        assertEquals(2,r.getCurrentPlayer());
    }

    @Test
    void nextTurn() {
        List<Player> p = new ArrayList<Player>();
        Player p1 = new Player(545,"Kevin","kikoo");
        Player p2 = new Player(545,"Dylan","kikoo");
        Player p3 = new Player(545,"Arthur","kikoo");
        p.add(p1);
        p.add(p2);
        p.add(p3);
        Round r = new Round(1,p,1);
        r.nextTurn();
        assertEquals(2,r.getTurn());
    }

    @Test
    void changeDirection() {
        List<Player> p = new ArrayList<Player>();
        Player p1 = new Player(545,"Kevin","kikoo");
        Player p2 = new Player(545,"Dylan","kikoo");
        Player p3 = new Player(545,"Arthur","kikoo");
        p.add(p1);
        p.add(p2);
        p.add(p3);
        Round r = new Round(1,p,1);
        r.changeDirection();
        assertFalse(r.getDirection());
    }

    @Test
    void getNextPlayerId() {
        List<Player> p = new ArrayList<Player>();
        Player p1 = new Player(545,"Kevin","kikoo");
        Player p2 = new Player(545,"Dylan","kikoo");
        Player p3 = new Player(545,"Arthur","kikoo");
        p.add(p1);
        p.add(p2);
        p.add(p3);
        Round r = new Round(1,p,1);
        assertEquals("Arthur",r.getNextPlayerId());
    }

    @Test
    void getPlayer() {
        List<Player> p = new ArrayList<Player>();
        Player p1 = new Player(545,"Kevin","kikoo");
        Player p2 = new Player(545,"Dylan","kikoo");
        Player p3 = new Player(545,"Arthur","kikoo");
        p.add(p1);
        p.add(p2);
        p.add(p3);
        Round r = new Round(1,p,1);
        assertEquals(p1,r.getPlayer("Kevin"));
        assertNull(r.getPlayer("Stéphane"));
    }
}