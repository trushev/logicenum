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
        return switch (f) {
            case final And ignored -> {
                final var formulas = f.map(ff -> exRec(ff, attrs)).filter(ff -> !ff.equals(Const.True));
                yield and(formulas);
            }
            case final Or ignored -> {
                final var formulas = f.map(ff -> exRec(ff, attrs));
                yield or(formulas);
            }
            default -> {
                if (f.consistsOnly(attrs)) {
                    yield  f;
                }
                yield Const.True;
            }
        };
    }

    Formula nnf(final Formula f) {
        return switch (f) {
            case final Atom atom -> atom;
            case final IsNull isNull -> isNull;
            case final Not not -> switch (operand(not)) {
                case final Atom atom -> not(atom);
                case final IsNull isNull -> not(isNull);
                case final And and -> or(and.map(ff -> nnf(not(ff))));
                case final Or or -> and(or.map(ff -> nnf(not(ff))));
                default -> throw new IllegalStateException("Unexpected value: " + operand(not));
            };
            case final And and -> and(and.map(this::nnf));
            case final Or or -> or(or.map(this::nnf));
        };
    }
}
