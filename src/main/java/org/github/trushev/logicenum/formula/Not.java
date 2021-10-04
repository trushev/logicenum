package org.github.trushev.logicenum.formula;

public final class Not extends UnaFormula {

    Not(Formula f) {
        super(f);
    }

    @Override
    protected Symbol symbol() {
        return Symbol.NOT;
    }

    @Override
    public Formula not() {
        return f;
    }
}
