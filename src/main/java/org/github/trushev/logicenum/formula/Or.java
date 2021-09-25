package org.github.trushev.logicenum.formula;

import java.util.Collection;

public final class Or extends BiFormula {

    private Or(final Collection<Formula> fs) {
        super(fs);
    }

    @Override
    protected Symbol symbol() {
        return Symbol.OR;
    }

    static Formula of(final Formula f1, final Formula f2) {
        return BiFormula.of(f1, f2, Const.False, Const.True, f -> (f instanceof Or), Or::new);
    }
}
