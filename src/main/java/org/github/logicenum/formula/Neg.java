package org.github.logicenum.formula;

import java.util.Objects;

final class Neg extends AbstractFormula {

    final Formula f;

    Neg(final Formula f) {
        this.f = f;
    }

    @Override
    public Formula neg() {
        return this.f;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final var neg = (Neg) o;
        return this.f.equals(neg.f);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.f);
    }

    @Override
    public String toString() {
        return "!" + this.f.toString();
    }
}
