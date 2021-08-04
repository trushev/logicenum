package org.github.logicenum.enu;

import org.github.logicenum.formula.Formula;
import org.junit.jupiter.api.Test;

import java.util.Iterator;

import static org.github.logicenum.formula.Formula.var;
import static org.junit.jupiter.api.Assertions.*;

class FormulasLazyTest {

    private final Formula a = var("a");
    private final Formula b = var("b");
    private final Formula c = var("c");

    @Test
    public void test() {
        final var formulas = Formulas.getLazy();
        final var iterator = formulas.lazyEnumeration(a, b, c);
        for (int i = 0; i < 10; i++) {
            System.out.println(iterator.next());
        }
    }
}
