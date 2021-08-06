package org.github.logicenum.next;

import org.github.logicenum.next.mid.FixedLengthFormulas;

import static org.github.logicenum.formula.Formula.var;

public class Main {
    public static void main(final String... args) {
        final var formulas = new Formulas();
        final var a = var("a");
        final var b = var("b");
        final var c = var("c");
        formulas.put(a, b, c, a.not(), b.not(), c.not());
        System.out.println(formulas);
        final var iterator = new FixedLengthFormulas(formulas, 3);
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }
    }
}
