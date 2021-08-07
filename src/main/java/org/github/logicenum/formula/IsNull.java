package org.github.logicenum.formula;

import java.util.Collection;
import java.util.Collections;
import java.util.Objects;

public final class IsNull extends Atom {

    private final Formula f;

    IsNull(final Formula f) {
        this.f = f;
    }

    @Override
    public Formula isNull() {
        // IS NULL(IS NULL(x)) => false
        return Const.False;
    }

    @Override
    public Collection<Formula> operands() {
        return Collections.singleton(this.f);
    }

    @Override
    public int length() {
        return this.f.length() + 1;
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
        return this.f.equals(isNull.f);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.f);
    }

    @Override
    public String toString() {
        return "?" + this.f.toString();
    }
}
