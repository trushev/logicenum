package org.github.trushev.logicenum.formula;

import org.junit.jupiter.api.Test;

import static org.github.trushev.logicenum.formula.Const.False;
import static org.github.trushev.logicenum.formula.Const.True;
import static org.github.trushev.logicenum.formula.Formula.*;
import static org.junit.jupiter.api.Assertions.*;

public class FormulaTest {

    private final Formula a = var("a");
    private final Formula b = var("b");
    private final Formula c = var("c");
    private final Formula d = var("d");

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

    @Test
    public void testNot1() {
        assertEquals(a, a.not().not());
    }

    @Test
    public void testNot2() {
        assertEquals("!a", a.not().toString());
    }

    @Test
    public void testIsNull1() {
        assertEquals(False, a.isNull().isNull());
    }

    @Test
    public void testIsNull2() {
        assertEquals("?a", a.isNull().toString());
    }

    @Test
    public void testDeepEquals1() {
        assertTrue(and(a, not(a), not(isNull(a))).deepEquals(False));
    }

    @Test
    public void testDeepEquals2() {
        assertTrue(False.deepEquals(and(a, not(a), not(isNull(a)))));
    }

    @Test
    public void testDeepEquals3() {
        assertFalse(a.deepEquals(True));
    }
}
