package org.github.logicenum.enu;

import org.github.logicenum.enu.outer.FormulasEnum;

import static org.github.logicenum.formula.Formula.var;

public class Main {
    public static void main(final String... args) {
        final var a = var("a");
        final var b = var("b");
        final var c = var("c");
        final var d = var("d");
        final var formulas = new FormulasEnum(5_000_000, a, b, c, d);
        formulas.forEachRemaining(f -> System.out.println(f.length() + ": " + f));
    }
}
