package org.github.logicenum.inner;

import org.github.logicenum.formula.Formula;
import org.github.logicenum.inner.InnerIterator.InnerNotIterator;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import static org.github.logicenum.formula.Formula.var;

public final class Main {
    public static void main(final String... args) {

        final var a = var("a");
        final var b = var("b");
        final var c = var("c");

        final var formulas = new HashMap<Integer, Set<Formula>>();
        formulas.put(1, Set.of(a, b, c));

        var currLength = 2;

        final var container = new FormulasContainer(formulas);

        final var iterator = container.byLength(2);

        final var notIter = new InnerNotIterator(iterator);


    }
}
