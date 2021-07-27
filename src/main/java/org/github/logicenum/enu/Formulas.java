package org.github.logicenum.enu;

import org.github.logicenum.formula.Formula;

import java.util.stream.Stream;

public interface Formulas {

    Stream<Formula> enumeration(Formula... vars);

    static Formulas get() {
        return new FormulasImpl();
    }
}
