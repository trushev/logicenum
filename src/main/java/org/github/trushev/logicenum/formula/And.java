package org.github.trushev.logicenum.formula;

import java.util.Collection;

public final class And extends BiFormula {

    private And(final Collection<Formula> fs) {
        super(fs);
    }

    @Override
    protected Symbol symbol() {
        return Symbol.AND;
    }

    static Formula of(final Formula f1, final Formula f2) {
        return BiFormula.of(
                f1,
                f2,
                Const.True,
                Const.False,
                f -> (f instanceof And),
                And::new
        );
    }
}
