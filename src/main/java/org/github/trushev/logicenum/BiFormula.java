package org.github.trushev.logicenum;

import java.util.*;
import java.util.stream.Collectors;

import static java.util.Collections.unmodifiableSet;

abstract class BiFormula implements Formula {

    private final Op op;
    private final Collection<Formula> fs;

    BiFormula(final Op op, final Collection<Formula> fs) {
        this.op = op;
        this.fs = fs;
    }

    static Collection<Formula> flatten(final Op op, final Formula... fs) {
        final var formulas = new ArrayList<Formula>(fs.length);
        for (final var f : fs) {
            if ((f instanceof BiFormula bf) && bf.op == op) {
                formulas.addAll(bf.fs);
            } else {
                formulas.add(f);
            }
        }
        return unmodifiableSet(new LinkedHashSet<>(formulas));
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BiFormula biFormula)) {
            return false;
        }
        return this.op.equals(biFormula.op) && this.fs.equals(biFormula.fs);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.op, this.fs);
    }

    @Override
    public String toString() {
        return this.fs.stream()
                .map(Object::toString)
                .collect(Collectors.joining(" " + this.op + " ", "(", ")"));
    }

    enum Op {
        and("&"),
        or("|"),
        ;

        private final String name;

        Op(final String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return this.name;
        }
    }
}
