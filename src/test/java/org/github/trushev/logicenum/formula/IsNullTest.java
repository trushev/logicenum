package org.github.trushev.logicenum.formula;

import static org.github.trushev.logicenum.formula.Const.False;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class IsNullTest extends TestBase {

    @Test
    public void testIsNull1() {
        assertEquals(False, a.isNull().isNull());
    }

    @Test
    public void testIsNull2() {
        assertEquals("?a", a.isNull().toString());
    }
}
