package org.github.trushev.logicenum.formula;

import java.util.Collections;
import java.util.Objects;

abstract sealed class UnaFormula extends AbstractVarsFormula permits IsNull, Not {

    protected final Formula f;

    protected UnaFormula(Formula f) {
        super(Collections.singleton(f), Utils.vars(f), f.length() + 1);
        this.f = f;
    }

    protected abstract Symbol symbol();

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UnaFormula uf)) {
            return false;
        }
        return symbol() == uf.symbol() && length() == uf.length() && vars.equals(uf.vars) && f.equals(uf.f);
    }

    @Override
    public int hashCode() {
        return Objects.hash(symbol(), length(), vars, f);
    }

    @Override
    public String toString() {
        return symbol().toString() + f.toString();
    }

    protected enum Symbol {
        NOT("!"),
        ISNULL("?");

        private final String name;

        Symbol(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return name;
        }
    }
}
