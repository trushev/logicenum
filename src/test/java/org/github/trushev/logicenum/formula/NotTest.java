package org.github.trushev.logicenum.formula;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public final class NotTest extends TestBase {

    @Test
    public void testNot1() {
        assertEquals(a, a.not().not());
    }

    @Test
    public void testNot2() {
        assertEquals("!a", a.not().toString());
    }
}
