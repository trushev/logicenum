package org.github.logicenum.enu;

import org.github.logicenum.enu.outer.FormulasEnum;

import java.util.stream.Stream;

import static org.github.logicenum.formula.Formula.var;

public class Main {
    public static void main(final String... args) {
        final var a = var("a");
        final var b = var("b");
        final var c = var("c");
        final var d = var("d");
        final var formulas = new FormulasEnum(50, a, b, c, d);
        formulas.forEachRemaining(f -> {
            final var x = f.length() + ": " + f;
            System.out.println(x);
        });
    }
}
