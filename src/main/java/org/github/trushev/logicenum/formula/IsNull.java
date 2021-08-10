package org.github.trushev.logicenum.formula;

public final class IsNull extends UnaFormula {

    IsNull(final Formula f) {
        super(f);
    }

    @Override
    protected Symbol symbol() {
        return Symbol.ISNULL;
    }

    @Override
    public Formula isNull() {
        return Const.False;
    }
}
