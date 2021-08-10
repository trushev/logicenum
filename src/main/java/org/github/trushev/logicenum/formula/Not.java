package org.github.trushev.logicenum.formula;

import java.util.Collections;
import java.util.Objects;

import static org.github.trushev.logicenum.formula.Formula.first;

public final class Not extends AbstractFormula {

    Not(final Formula f) {
        super(Collections.singleton(f), Utils.vars(f), f.length() + 1);
    }

    @Override
    public Formula not() {
        return first(this.operands);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final var not = (Not) o;
        return length() == not.length()
                && this.vars.equals(not.vars)
                && this.operands.equals(not.operands);
    }

    @Override
    public int hashCode() {
        return Objects.hash(length(), this.vars, this.operands);
    }

    @Override
    public String toString() {
        return "!" + first(this.operands).toString();
    }
}
