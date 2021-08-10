package org.github.trushev.logicenum.formula;

import org.junit.jupiter.api.Test;

import static org.github.trushev.logicenum.formula.Formula.*;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class FormulaTest {

    @Test
    public void testDeepEquals1() {
        final var a = var("a");
        assertTrue(and(a, not(a), not(isNull(a))).deepEquals(Const.False));
    }

    @Test
    public void testDeepEquals2() {
        final var a = var("a");
        assertTrue(Const.False.deepEquals(and(a, not(a), not(isNull(a)))));
    }

    @Test
    public void testDeepEquals3() {
        assertFalse(var("a").deepEquals(Const.True));
    }
}
