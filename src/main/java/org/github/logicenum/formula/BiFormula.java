package org.github.logicenum.formula;

import java.util.Collection;
import java.util.Objects;
import java.util.stream.Collectors;

abstract class BiFormula extends AbstractFormula {

    private final int length;
    protected final Op op;
    protected final Collection<Formula> fs;

    protected BiFormula(final Op op, final Collection<Formula> fs) {
        this.op = op;
        this.fs = fs;
        this.length = this.fs.stream().mapToInt(Formula::length).sum() + 1;
    }

    @Override
    public int length() {
        return this.length;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BiFormula bf)) {
            return false;
        }
        return this.length == bf.length && this.op.equals(bf.op) && this.fs.equals(bf.fs);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.length, this.op, this.fs);
    }

    @Override
    public String toString() {
        return this.fs.stream()
                .map(Object::toString)
                .collect(Collectors.joining(" " + this.op + " ", "(", ")"));
    }

    enum Op {
        AND("&"),
        OR("|"),
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
