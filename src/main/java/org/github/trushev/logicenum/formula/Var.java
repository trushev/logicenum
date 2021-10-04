package org.github.trushev.logicenum.formula;

import java.util.Collections;
import java.util.Objects;
import java.util.stream.Stream;

public final class Var extends Atom implements Comparable<Var> {

    private final String name;

    Var(String name) {
        super(Collections.emptyList(), 1);
        this.name = name;
    }

    @Override
    public Stream<Formula> vars() {
        return Stream.of(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        var var = (Var) o;
        return name.equals(var.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public int compareTo(Var o) {
        return name.compareTo(o.name);
    }
}
