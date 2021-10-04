package org.github.trushev.logicenum.formula;

import static org.github.trushev.logicenum.formula.Const.False;
import static org.github.trushev.logicenum.formula.Const.True;
import static org.github.trushev.logicenum.formula.Formula.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public final class FormulaTest extends TestBase {

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
