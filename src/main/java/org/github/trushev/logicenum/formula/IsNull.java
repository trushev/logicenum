package org.github.trushev.logicenum.formula;

import java.util.Collections;
import java.util.Objects;

import static org.github.trushev.logicenum.formula.Formula.first;

public final class IsNull extends AbstractFormula {

    IsNull(final Formula f) {
        super(Collections.singleton(f), Utils.vars(f), f.length() + 1);
    }

    @Override
    public Formula isNull() {
        // IS NULL(IS NULL(x)) => false
        return Const.False;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final var isNull = (IsNull) o;
        return length() == isNull.length()
                && this.vars.equals(isNull.vars)
                && this.operands.equals(isNull.operands);
    }

    @Override
    public int hashCode() {
        return Objects.hash(length(), this.vars, this.operands);
    }

    @Override
    public String toString() {
        return "?" + first(this.operands).toString();
    }
}
