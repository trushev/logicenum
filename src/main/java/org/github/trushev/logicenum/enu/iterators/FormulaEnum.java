package org.github.trushev.logicenum.enu.iterators;

import org.github.trushev.logicenum.formula.Formula;

import java.util.stream.Stream;

public interface FormulaEnum {

    Stream<Formula> formulas();

    static FormulaEnum get(final long limit, final Formula... formulas) {
        return new FormulaEnumImpl();
    }

    static FormulaEnum get(final Formula... formulas) {
        return get(Long.MAX_VALUE, formulas);
    }
}
