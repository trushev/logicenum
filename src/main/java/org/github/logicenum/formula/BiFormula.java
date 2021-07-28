package org.github.logicenum.formula;

import java.util.Collection;
import java.util.Objects;

import static java.util.stream.Collectors.joining;

abstract class BiFormula extends AbstractFormula {

    private final int length;
    private final Symbol symbol;
    private final Collection<Formula> operands;

    protected BiFormula(final Symbol symbol, final Collection<Formula> operands) {
        this.symbol = symbol;
        this.operands = operands;
        this.length = this.operands.stream().mapToInt(Formula::length).sum() + 1;
    }

    @Override
    public Collection<Formula> operands() {
        return this.operands;
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
        return this.length == bf.length && this.symbol == bf.symbol && this.operands.equals(bf.operands);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.length, this.symbol, this.operands);
    }

    @Override
    public String toString() {
        return this.operands.stream()
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
