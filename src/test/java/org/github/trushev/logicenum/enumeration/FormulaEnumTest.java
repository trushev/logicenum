package org.github.trushev.logicenum.enumeration;

import org.junit.jupiter.api.Test;

import static org.github.trushev.logicenum.formula.Formula.var;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class FormulaEnumTest {

    @Test
    public void test() {
        final var a = var("a");
        final var formulas = FormulaEnum.get(6, a).formulas().toList();
        assertEquals("[a, !a, ?a, !?a, ?!a, !?!a]", formulas.toString());
    }
}
