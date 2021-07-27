package org.github.logicenum.formula;

import java.util.Collection;
import java.util.Objects;

import static java.util.stream.Collectors.joining;

abstract class BiFormula extends AbstractFormula {

    private final int length;
    private final Symbol symbol;
    private final Collection<Formula> fs;

    protected BiFormula(final Symbol symbol, final Collection<Formula> fs) {
        this.symbol = symbol;
        this.fs = fs;
        this.length = this.fs.stream().mapToInt(Formula::length).sum() + 1;
    }

    Collection<Formula> fs() {
        return this.fs;
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
        return this.length == bf.length && this.symbol == bf.symbol && this.fs.equals(bf.fs);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.length, this.symbol, this.fs);
    }

    @Override
    public String toString() {
        return this.fs.stream()
                .map(Object::toString)
                .collect(joining(" " + this.symbol + " ", "(", ")"));
    }

    protected enum Symbol {
        AND("&"),
        OR("|"),
        ;

        private final String name;

        Symbol(final String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return this.name;
        }
    }
}
