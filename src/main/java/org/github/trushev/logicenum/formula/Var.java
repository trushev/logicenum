package org.github.trushev.logicenum.formula;

import java.util.Collection;
import java.util.Collections;
import java.util.Objects;

public final class Var extends Atom implements Comparable<Var> {

    private final String name;
    private Collection<Formula> vars;

    Var(final String name) {
        this.name = name;
    }

    @Override
    public int length() {
        return 1;
    }

    @Override
    public Collection<Formula> vars() {
        if (this.vars == null) {
            this.vars = Collections.singleton(this);
        }
        return this.vars;
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

    @Override
    public int compareTo(final Var o) {
        return this.name.compareTo(o.name);
    }
}
