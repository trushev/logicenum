package org.github.trushev.logicenum.formula;

import java.util.Collections;
import java.util.Objects;
import java.util.stream.Stream;

public final class Var extends Atom implements Comparable<Var> {

    private final String name;

    Var(final String name) {
        // TODO: vars should be Collections.singleton(this)
        super(Collections.emptyList(), Collections.emptyList(), 1);
        this.name = name;
    }

    @Override
    public Stream<Formula> vars() {
        return Stream.of(this);
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
