package org.github.trushev.logicenum.formula;

import java.util.Collections;
import java.util.Objects;

abstract class UnaFormula extends AbstractVarsFormula {

    protected final Formula f;

    protected UnaFormula(final Formula f) {
        super(Collections.singleton(f), Utils.vars(f), f.length() + 1);
        this.f = f;
    }

    protected abstract Symbol symbol();

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UnaFormula uf)) {
            return false;
        }
        return symbol() == uf.symbol() && length() == uf.length() && this.vars.equals(uf.vars) && this.f.equals(uf.f);
    }

    @Override
    public int hashCode() {
        return Objects.hash(symbol(), length(), this.vars, this.f);
    }

    @Override
    public String toString() {
        return symbol().toString() + this.f.toString();
    }

    protected enum Symbol {
        NOT("!"),
        ISNULL("?");

        private final String name;

        Symbol(final String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return this.name;
        }
    }
}
