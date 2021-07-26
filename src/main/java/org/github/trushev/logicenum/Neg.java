package org.github.trushev.logicenum;

import java.util.Objects;

final class Neg implements Formula {

    final Formula f;

    Neg(final Formula f) {
        this.f = f;
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
