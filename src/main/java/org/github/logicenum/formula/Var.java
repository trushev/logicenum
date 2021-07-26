package org.github.logicenum.formula;

import java.util.Objects;

final class Var implements Formula {

    private final String name;

    Var(final String name) {
        this.name = name;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final var var = (Var) o;
        return this.name.equals(var.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.name);
    }

    @Override
    public String toString() {
        return this.name;
    }
}
