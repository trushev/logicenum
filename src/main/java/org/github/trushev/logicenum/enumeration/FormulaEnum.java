package org.github.trushev.logicenum.enumeration;

import java.util.stream.Stream;
import org.github.trushev.logicenum.formula.Formula;

public interface FormulaEnum {
    Stream<Formula> formulas();

    static FormulaEnum get(final long limit, final Formula... fs) {
        return new FormulaEnumImpl(new LimitedFormulasEnum(new FormulasEnumInner(new Formulas(fs)), limit));
    }
}
