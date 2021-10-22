package org.github.trushev.logicenum.implication;

import static org.github.trushev.logicenum.formula.Formula.*;

import java.util.Collection;
import java.util.Set;
import org.github.trushev.logicenum.formula.*;

public final class SparkImplication implements Implication {

    @Override
    public Formula imply(Formula f, Formula... attrs) {
        return implyRec(nnf(f), Set.of(attrs));
    }

    private Formula implyRec(Formula f, Collection<Formula> attrs) {
        return switch (f) {
            case And and -> {
                var formulas = and.map(ff -> implyRec(ff, attrs)).filter(ff -> !ff.equals(Const.True));
                yield and(formulas);
            }
            case Or or -> {
                var formulas = or.map(ff -> implyRec(ff, attrs));
                yield or(formulas);
            }
            default -> {
                if (f.consistsOnly(attrs)) {
                    yield f;
                }
                yield Const.True;
            }
        };
    }

    Formula nnf(Formula f) {
        return switch (f) {
            case Atom atom -> atom;
            case IsNull isNull -> isNull;
            case Not not -> switch (operand(not)) {
                case Atom atom -> not(atom);
                case IsNull isNull -> not(isNull);
                case And and -> or(and.map(ff -> nnf(not(ff))));
                case Or or -> and(or.map(ff -> nnf(not(ff))));
                default -> throw new IllegalStateException("Unexpected value: " + operand(not));
            };
            case And and -> and(and.map(this::nnf));
            case Or or -> or(or.map(this::nnf));
        };
    }
}
