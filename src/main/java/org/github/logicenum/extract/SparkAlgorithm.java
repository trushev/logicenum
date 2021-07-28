package org.github.logicenum.extract;

import org.github.logicenum.formula.*;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

import static org.github.logicenum.formula.Formula.*;

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
                    .collect(Collectors.toSet());
            if (formulas.isEmpty()) {
                return Const.True;
            }
            return and(formulas);
        }
        if (f instanceof Or) {
            final var formulas = f.operands()
                    .stream()
                    .map(ff -> exRec(ff, attrs))
                    .collect(Collectors.toSet());
            return or(formulas);
        }
        if (f.consistsOnly(attrs)) {
            return f;
        }
        return Const.True;
    }

    Formula nnf(final Formula f) {
        if (f instanceof Not n) {
            final var arg = operand(n);
            final var formulas = arg.operands()
                    .stream()
                    .map(ff -> nnf(not(ff)))
                    .collect(Collectors.toSet());
            if (arg instanceof And) {
                return or(formulas);
            }
            if (arg instanceof Or) {
                return and(formulas);
            }
        }
        return f;
    }
}
