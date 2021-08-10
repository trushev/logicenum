package org.github.trushev.logicenum.formula;

import java.util.Objects;
import java.util.stream.Stream;

public final class Not extends AbstractFormula {

    private final Formula f;
    private final int length;

    Not(final Formula f) {
        this.f = f;
        this.length = this.f.length() + 1;
    }

    @Override
    public int length() {
        return this.length;
    }

    @Override
    public Formula not() {
        return this.f;
    }

    @Override
    public Stream<Formula> operands() {
        return Stream.of(this.f);
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
        return this.f.equals(not.f);
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
