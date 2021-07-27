package org.github.logicenum.formula;


import java.util.*;
import java.util.function.Function;

import static java.util.Collections.unmodifiableSet;
import static org.github.logicenum.formula.BiFormula.Op;

abstract class AbstractFormula implements Formula {

    @Override
    public Formula or(final Formula f) {
        return unwrapped(flatten(Op.OR, this, f), Or::new);
    }

    @Override
    public Formula and(final Formula f) {
        return unwrapped(flatten(Op.AND, this, f), And::new);
    }

    @Override
    public Formula neg() {
        return new Neg(this);
    }

    private Formula unwrapped(final Collection<Formula> fs, final Function<Collection<Formula>, Formula> fun) {
        if (fs.size() == 1) {
            return fs.iterator().next();
        }
        return fun.apply(fs);
    }

    static Collection<Formula> flatten(final Op op, final Formula f1, final Formula f2) {
        if ((f1 instanceof BiFormula bf1) && bf1.op == op && (f2 instanceof BiFormula bf2) && bf2.op == op) {
            final var formulas = new HashSet<>(bf1.fs);
            formulas.addAll(bf2.fs);
            return unmodifiableSet(formulas);
        } else if ((f1 instanceof BiFormula bf1) && bf1.op == op) {
            final var formulas = new HashSet<>(bf1.fs);
            formulas.add(f2);
            return unmodifiableSet(formulas);
        } else if ((f2 instanceof BiFormula bf2) && bf2.op == op) {
            final var formulas = new HashSet<>(bf2.fs);
            formulas.add(f1);
            return unmodifiableSet(formulas);
        } else {
            if (f1.equals(f2)) {
                return Set.of(f1);
            }
            return Set.of(f1, f2);
        }
    }
}
