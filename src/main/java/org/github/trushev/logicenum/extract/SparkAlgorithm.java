package org.github.trushev.logicenum.extract;

import org.github.trushev.logicenum.formula.*;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

public class SparkAlgorithm implements Algorithm {

    @Override
    public Formula ex(final Formula f, final Formula... attrs) {
        return exRec(nnf(f), Set.of(attrs));
    }

    private Formula exRec(final Formula f, final Collection<Formula> attrs) {
        if (f instanceof And) {
            final var formulas = f.operands()
                    .stream()
                    .map(ff -> exRec(ff, attrs))
                    .filter(ff -> !ff.equals(Const.True))
                    .collect(Collectors.toUnmodifiableSet());
            if (formulas.isEmpty()) {
                return Const.True;
            }
            return Formula.and(formulas);
        }
        if (f instanceof Or) {
            final var formulas = f.operands()
                    .stream()
                    .map(ff -> exRec(ff, attrs))
                    .collect(Collectors.toUnmodifiableSet());
            return Formula.or(formulas);
        }
        if (f.consistsOnly(attrs)) {
            return f;
        }
        return Const.True;
    }

    Formula nnf(final Formula f) {
        if (f instanceof Atom) {
            return f;
        }
        if (f instanceof Not n) {
            final var arg = Formula.operand(n);
            if (arg instanceof Atom) {
                return Formula.not(arg);
            }
            final var formulas = arg.operands()
                    .stream()
                    .map(ff -> nnf(Formula.not(ff)))
                    .collect(Collectors.toUnmodifiableSet());
            if (arg instanceof And) {
                return Formula.or(formulas);
            }
            if (arg instanceof Or) {
                return Formula.and(formulas);
            }
        }
        final var formulas = f.operands()
                .stream().map(this::nnf)
                .collect(Collectors.toUnmodifiableSet());
        if (f instanceof And) {
            return Formula.and(formulas);
        }
        if (f instanceof Or) {
            return Formula.or(formulas);
        }

        throw new IllegalStateException(f.toString());
    }
}
