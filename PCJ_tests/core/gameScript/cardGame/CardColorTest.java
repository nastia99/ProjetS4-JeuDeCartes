package core.gameScript.cardGame;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CardColorTest {

    @Test
    void getFlyCount() {
        CardColor c= CardColor.V;
        assertEquals(c.getFlyCount(),0);
        c= CardColor.J;
        assertEquals(c.getFlyCount(),1);
        c= CardColor.O;
        assertEquals(c.getFlyCount(),2);
        c= CardColor.R;
        assertEquals(c.getFlyCount(),3);
        c= CardColor.S;
        assertEquals(c.getFlyCount(),5);

    }

    @Test
    void getColor() {
        CardColor c= CardColor.V;
        assertEquals(c.getColor(),"V");
        c= CardColor.J;
        assertEquals(c.getColor(),"J");
        c= CardColor.O;
        assertEquals(c.getColor(),"O");
        c= CardColor.R;
        assertEquals(c.getColor(),"R");
        c= CardColor.S;
        assertEquals(c.getColor(),"S");
    }

}