package org.github.logicenum.enu;

import org.github.logicenum.formula.Formula;

import java.util.Iterator;
import java.util.stream.Stream;

public interface Formulas {

    Stream<Formula> enumeration(Formula... vars);

    Iterator<Formula> lazyEnumeration(final Formula... vars);

    static Formulas getSet() {
        return new FormulasSet();
    }

    static Formulas getLazy() {
        return new FormulasLazy();
    }
}
