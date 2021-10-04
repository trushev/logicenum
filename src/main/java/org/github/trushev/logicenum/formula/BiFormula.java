package org.github.trushev.logicenum.formula;

import static java.util.Collections.unmodifiableSet;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toCollection;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;

abstract sealed class BiFormula extends AbstractVarsFormula permits And, Or {

    protected BiFormula(Collection<Formula> operands) {
        super(operands, Utils.vars(operands), Utils.length(operands));
    }

    protected abstract Symbol symbol();

    protected static Formula of(
        Formula f1,
        Formula f2,
        Const weekConst,
        Const strongConst,
        Predicate<Formula> p,
        Function<Collection<Formula>, Formula> fun
    ) {
        var formulas = unmodifiableSet(
            (Set<Formula>) Stream
                .of(f1, f2)
                .flatMap(f -> p.test(f) ? f.operands() : Stream.of(f))
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
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BiFormula bf)) {
            return false;
        }
        return (
            symbol() == bf.symbol() &&
            length() == bf.length() &&
            vars.equals(bf.vars) &&
            operands.equals(bf.operands)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(symbol(), length(), vars, operands);
    }

    @Override
    public String toString() {
        return map(Object::toString).collect(joining(" " + symbol() + " ", "(", ")"));
    }

    protected enum Symbol {
        AND("&"),
        OR("|");

        private final String name;

        Symbol(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return name;
        }
    }
}
