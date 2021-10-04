package org.github.trushev.logicenum.formula;

import java.util.Collection;

public final class And extends BiFormula {

    private And(Collection<Formula> fs) {
        super(fs);
    }

    @Override
    protected Symbol symbol() {
        return Symbol.AND;
    }

    static Formula of(Formula f1, Formula f2) {
        return BiFormula.of(f1, f2, Const.True, Const.False, f -> (f instanceof And), And::new);
    }
}
