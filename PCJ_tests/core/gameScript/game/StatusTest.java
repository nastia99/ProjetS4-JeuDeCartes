package core.gameScript.game;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StatusTest {

    @Test
    void getText() {
        Status s= Status.ANNULEE;
        assertEquals("ANNULEE",s.getText());
        s = Status.ATTENTE;
        assertEquals("ATTENTE",s.getText());
        s = Status.COMPLETE;
        assertEquals("COMPLETE",s.getText());
        s = Status.TERMINEE;
        assertEquals("TERMINEE",s.getText());
    }
}