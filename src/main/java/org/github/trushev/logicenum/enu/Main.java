package org.github.trushev.logicenum.enu;

import org.github.trushev.logicenum.enu.iterators.LimitedFormulasEnum;

import static org.github.trushev.logicenum.formula.Formula.var;

public class Main {
    public static void main(final String... args) {
        final var a = var("a");
        final var b = var("b");
        final var c = var("c");
        final var d = var("d");
        final var formulas = new LimitedFormulasEnum(50, a, b, c, d);
        formulas.forEachRemaining(f -> {
            final var x = f.length() + ": " + f;
            System.out.println(x);
        });
    }
}
