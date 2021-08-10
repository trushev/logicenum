package org.github.trushev.logicenum.formula;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;

import static java.util.Collections.unmodifiableSet;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toCollection;

abstract class BiFormula extends AbstractFormula {

    protected BiFormula(final Collection<Formula> operands) {
        super(
                operands,
                operands.stream()
                        .flatMap(Formula::vars)
                        .sorted()
                        .distinct()
                        .toList(),

                // TODO: possible bug
                //  length of (a | b | c) should be 4 or 5?
                operands.stream()
                        .mapToInt(Formula::length)
                        .sum() + 1
        );
    }

    protected abstract Symbol symbol();

    protected static Formula of(
            final Formula f1,
            final Formula f2,
            final Const weekConst,
            final Const strongConst,
            final Predicate<Formula> p,
            final Function<Collection<Formula>, Formula> fun
    ) {
        final var formulas = unmodifiableSet(
                (Set<Formula>) Stream.of(f1, f2)
                        .flatMap(f -> p.test(f)
                                ? f.operands()
                                : Stream.of(f))
                        .filter(f -> !f.equals(weekConst))
                        .collect(toCollection(LinkedHashSet::new))
        );
        if (formulas.isEmpty()) {
            return weekConst;
        }
        if (formulas.size() == 1) {
            return formulas.iterator().next();
        }
        if (formulas.contains(strongConst)) {
            return strongConst;
        }
        return fun.apply(formulas);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BiFormula bf)) {
            return false;
        }
        return symbol() == bf.symbol()
                && length() == bf.length()
                && this.vars.equals(bf.vars)
                && this.operands.equals(bf.operands);
    }

    @Override
    public int hashCode() {
        return Objects.hash(symbol(), length(), this.vars, this.operands);
    }

    @Override
    public String toString() {
        return operands()
                .map(Object::toString)
                .collect(joining(" " + symbol() + " ", "(", ")"));
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
