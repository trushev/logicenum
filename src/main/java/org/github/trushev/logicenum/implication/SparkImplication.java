package org.github.trushev.logicenum.implication;

import static org.github.trushev.logicenum.formula.Formula.*;

import java.util.Collection;
import java.util.Set;
import org.github.trushev.logicenum.formula.*;

public class SparkImplication implements Implication {

    @Override
    public Formula ex(final Formula f, final Formula... attrs) {
        return exRec(nnf(f), Set.of(attrs));
    }

    private Formula exRec(final Formula f, final Collection<Formula> attrs) {
        if (f instanceof And) {
            final var formulas = f.operands().map(ff -> exRec(ff, attrs)).filter(ff -> !ff.equals(Const.True));
            return and(formulas);
        }
        if (f instanceof Or) {
            final var formulas = f.operands().map(ff -> exRec(ff, attrs));
            return or(formulas);
        }
        if (f.consistsOnly(attrs)) {
            return f;
        }
        return Const.True;
    }

    Formula nnf(final Formula f) {
        if (f instanceof Atom || f instanceof IsNull) {
            return f;
        }
        if (f instanceof Not n) {
            final var arg = operand(n);
            if (arg instanceof Atom || arg instanceof IsNull) {
                return not(arg);
            }
            final var formulas = arg.operands().map(ff -> nnf(not(ff)));
            if (arg instanceof And) {
                return or(formulas);
            }
            if (arg instanceof Or) {
                return and(formulas);
            }
        }
        final var formulas = f.operands().map(this::nnf);
        if (f instanceof And) {
            return and(formulas);
        }
        if (f instanceof Or) {
            return or(formulas);
        }

        throw new IllegalStateException(f.toString());
    }
}
