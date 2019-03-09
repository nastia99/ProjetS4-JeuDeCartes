package core.gameScript.game;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GameTest {

    @Test
    void getPort() {
        List<Player> p = new ArrayList<Player>();
        Game g= new Game(156,"Partie1",p,3,2,3,2);
        assertEquals(156,g.getPort());
    }

    @Test
    void getStatus() {
        List<Player> p = new ArrayList<Player>();
        Game g= new Game(156,"Partie1",p,3,2,3,2);
        assertEquals(Status.ATTENTE,g.getStatus());
    }

    @Test
    void getGameID() {
        List<Player> p = new ArrayList<Player>();
        Game g= new Game(156,"Partie1",p,3,2,3,2);
        assertNotNull(g.getGameID());
    }

    @Test
    void getName() {
        List<Player> p = new ArrayList<Player>();
        Game g= new Game(156,"Partie1",p,3,2,3,2);
        assertEquals("Partie1",g.getName());
    }
    // a continuer


    @Test
    void getPlayers() {
        Player p1= new Player(15,"Pierre","humain");
        List<Player> p = new ArrayList<Player>();
        Game g= new Game(156,"Partie1",p,3,2,3,2);
        assertEquals(p,g.getPlayers());
        p.add(p1);
        assertEquals(p,g.getPlayers());
    }

    @Test
    void getMaxRealPlayer() {
        List<Player> p = new ArrayList<Player>();
        Game g= new Game(156,"Partie1",p,3,2,3,2);
        assertEquals(3,g.getMaxRealPlayer());
    }

    @Test
    void getMaxVirtualPlayer() {
        List<Player> p = new ArrayList<Player>();
        Game g= new Game(156,"Partie1",p,3,2,3,2);
        assertEquals(2,g.getMaxVirtualPlayer());
    }

    @Test
    void getRealPlayerNb() {
        List<Player> p = new ArrayList<Player>();
        Game g= new Game(156,"Partie1",p,3,2,3,2);
        assertEquals(3,g.getRealPlayerNb());
    }

    @Test
    void getVirtualPlayerNb() {
        List<Player> p = new ArrayList<Player>();
        Game g= new Game(156,"Partie1",p,3,2,3,2);
        assertEquals(2,g.getVirtualPlayerNb());
    }

    @Test
    void getCurrentRound() {
        List<Player> p = new ArrayList<Player>();
        Game g= new Game(156,"Partie1",p,3,2,3,2);
        assertEquals(0,g.getCurrentRound());
    }

    @Test
    void setnbMaxJR() {
        List<Player> p = new ArrayList<Player>();
        Game g= new Game(156,"Partie1",p,3,2,3,2);
        assertEquals(3,g.getMaxRealPlayer());
        g.setnbMaxJR(2);
        assertEquals(2,g.getMaxRealPlayer());

    }

    @Test
    void setnbMaxJV() {
        List<Player> p = new ArrayList<Player>();
        Game g= new Game(156,"Partie1",p,3,2,3,2);
        assertEquals(2,g.getMaxVirtualPlayer());
        g.setnbMaxJV(3);
        assertEquals(3,g.getMaxVirtualPlayer());
    }

    @Test
    void gameContinue() {
        Player p1= new Player(15,"Pierre","humain");
        Player p2= new Player(26,"Mathis","humain");
        Player p3= new Player(26,"Baptiste","humain");
        List<Player> p = new ArrayList<Player>();
        Game g= new Game(156,"Partie1",p,3,2,3,2);
        p2.addFly(20);
        p1.addFly(60);
        p3.addFly(101);
        p1.convertFlyToPoint();
        p2.convertFlyToPoint();
        p.add(p2);
        p.add(p1);
        assertFalse(g.gameContinue());
        p3.convertFlyToPoint();
        p.add(p3);
        assertTrue(g.gameContinue());
    }

    @Test
    void nextPlayer() {
        // a implémenter plus tard
    }

    @Test
    void nextRound() {
        // a implementer plus tard
    }

    @Test
    void getTurn() {
        // a implementer plus tard
    }

    @Test
    void incrementMaxRealPlayer() {
        Player p1= new Player(15,"Pierre","humain");
        Player p2= new Player(26,"Mathis","humain");
        Player p3= new Player(26,"Baptiste","humain");
        List<Player> p = new ArrayList<Player>();
        Game g= new Game(156,"Partie1",p,3,2,3,2);
        assertEquals(-1,g.incrementMaxRealPlayer());
        Game g1= new Game(156,"Partie2",p,2,2,2,2);
        assertEquals(3,g1.incrementMaxRealPlayer());

    }

    @Test
    void decrementMaxRealPlayer() {
        Player p1= new Player(15,"Pierre","humain");
        Player p2= new Player(26,"Mathis","humain");
        Player p3= new Player(26,"Baptiste","humain");
        List<Player> p = new ArrayList<Player>();
        Game g= new Game(156,"Partie1",p,0,2,3,2);
        assertEquals(-1,g.decrementMaxRealPlayer());
        Game g1= new Game(156,"Partie2",p,2,2,2,2);
        assertEquals(1,g1.decrementMaxRealPlayer());
    }

    @Test
    void incrementMaxVirtualPlayer() {
        Player p1= new Player(15,"Pierre","humain");
        Player p2= new Player(26,"Mathis","humain");
        Player p3= new Player(26,"Baptiste","humain");
        List<Player> p = new ArrayList<Player>();
        Game g= new Game(156,"Partie1",p,2,3,2,3);
        assertEquals(-1,g.incrementMaxVirtualPlayer());
        Game g1= new Game(156,"Partie2",p,2,2,2,2);
        assertEquals(3,g1.incrementMaxRealPlayer());
    }

    @Test
    void decrementMaxVirtualPlayer() {
        Player p1= new Player(15,"Pierre","humain");
        Player p2= new Player(26,"Mathis","humain");
        Player p3= new Player(26,"Baptiste","humain");
        List<Player> p = new ArrayList<Player>();
        Game g= new Game(156,"Partie1",p,3,0,3,2);
        assertEquals(-1,g.decrementMaxVirtualPlayer());
        Game g1= new Game(156,"Partie2",p,2,2,2,2);
        assertEquals(1,g1.decrementMaxVirtualPlayer());
    }

    @Test
    void canAcceptRealPlayer() {
        List<Player> p = new ArrayList<Player>();
        Game g= new Game(156,"Partie1",p,3,2,2,2);
        assertTrue(g.canAcceptRealPlayer());
        Game g1= new Game(156,"Partie2",p,2,2,2,2);
        assertFalse(g1.canAcceptRealPlayer());
    }

    @Test
    void canAcceptVirtualPlayer() {
        List<Player> p = new ArrayList<Player>();
        Game g= new Game(156,"Partie1",p,3,2,3,1);
        assertTrue(g.canAcceptVirtualPlayer());
        Game g1= new Game(156,"Partie2",p,2,2,2,2);
        assertFalse(g1.canAcceptVirtualPlayer());
    }

    @Test
    void addPlayer() {
        List<Player> p = new ArrayList<Player>();
        Game g= new Game(156,"Partie1",p,3,2,3,1);
        assertTrue(g.addPlayer(42,"Augustin","JV"));
    }

    @Test
    void getNbPlayer() {
        List<Player> p = new ArrayList<Player>();
        Game g= new Game(156,"Partie1",p,3,2,3,1);
        assertTrue(g.addPlayer(42,"Augustin","JV"));
        assertEquals(5,g.getNbPlayer());
    }

    @Test
    void getPlayersNames() {
        Player p1= new Player(15,"Pierre","JR");
        Player p2= new Player(26,"Mathis","JR");
        Player p3= new Player(26,"Baptiste","JR");
        List<Player> p = new ArrayList<Player>();
        p.add(p1);
        p.add(p2);
        p.add(p3);
        Game g= new Game(156,"Partie1",p,3,2,3,1);
        List<String> n= new ArrayList<String>();
        n.add("Pierre");
        n.add("Mathis");
        n.add("Baptiste");
        assertEquals(n,g.getPlayersNames());

    }

    @Test
    void isInHandPlayer() {
        // a implementer plus tard
    }

    @Test
    void addCard() {
        // a implementer plus tard
    }

    @Test
    void playCard() {
        // a implementer plus tard
    }

    @Test
    void getNbJRco() {
        Player p1= new Player(15,"Pierre","JR");
        Player p2= new Player(26,"Mathis","JR");
        Player p3= new Player(26,"Baptiste","JR");
        List<Player> p = new ArrayList<Player>();
        p.add(p1);
        p.add(p2);
        p.add(p3);
        Game g= new Game(156,"Partie1",p,3,2,3,1);
        assertEquals(3,g.getNbJRco());
    }

    @Test
    void getNbJVco() {
        Player p1= new Player(15,"Pierre","JR");
        Player p2= new Player(26,"Mathis","JR");
        Player p3= new Player(26,"Baptiste","JV");
        List<Player> p = new ArrayList<Player>();
        p.add(p1);
        p.add(p2);
        p.add(p3);
        Game g= new Game(156,"Partie1",p,3,2,3,1);
        assertEquals(1,g.getNbJVco());
    }

    @Test
    void getCurrentSingleRound() {
        // a implementer plus tard
    }

    @Test
    void isNext() {
        // a implementer plus tard
    }

    @Test
    void startPlayerTurn() {
        // a implementer plus tard
    }
}
