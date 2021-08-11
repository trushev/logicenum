package org.github.trushev.logicenum.formula;

import org.junit.jupiter.api.Test;

import static org.github.trushev.logicenum.formula.Const.False;
import static org.github.trushev.logicenum.formula.Const.True;
import static org.junit.jupiter.api.Assertions.*;

public class AndTest extends TestBase {

    @Test
    public void testAnd1() {
        assertEquals(a, a.and(a));
    }

    @Test
    public void testAnd2() {
        assertEquals("(a & b)", a.and(b).toString());
    }

    @Test
    public void testAnd3() {
        assertEquals("(a & b & !c)", a.and(b).and(c.not()).toString());
    }

    @Test
    public void testAnd4() {
        assertEquals("(a & b & ?c)", a.and(b).and(c.isNull()).toString());
    }

    @Test
    public void testAnd5() {
        assertEquals("(a & b & c & d)", a.and(b).and(c.and(d)).toString());
    }

    @Test
    public void testAnd6() {
        assertEquals(a, a.and(True));
    }

    @Test
    public void testAnd7() {
        assertEquals(False, a.and(False));
    }

    @Test
    public void testAnd8() {
        assertEquals(True, True.and(True));
    }
}
