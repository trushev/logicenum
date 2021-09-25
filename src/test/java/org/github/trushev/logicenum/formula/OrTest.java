package org.github.trushev.logicenum.formula;

import static org.github.trushev.logicenum.formula.Const.False;
import static org.github.trushev.logicenum.formula.Const.True;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class OrTest extends TestBase {

    @Test
    public void testOr1() {
        assertEquals(a, a.or(a));
    }

    @Test
    public void testOr2() {
        assertEquals("(a | b)", a.or(b).toString());
    }

    @Test
    public void testOr3() {
        assertEquals("(a | b | !c)", a.or(b).or(c.not()).toString());
    }

    @Test
    public void testOr4() {
        assertEquals("(a | b | ?c)", a.or(b).or(c.isNull()).toString());
    }

    @Test
    public void testOr5() {
        assertEquals("(a | b | c | d)", a.or(b).or(c.or(d)).toString());
    }

    @Test
    public void testOr6() {
        assertEquals(a, a.or(False));
    }

    @Test
    public void testOr7() {
        assertEquals(True, a.or(True));
    }

    @Test
    public void testOr8() {
        assertEquals(False, False.or(False));
    }
}
