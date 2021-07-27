package org.github.logicenum.formula;


import java.util.HashSet;
import java.util.Set;

import static java.util.Collections.unmodifiableSet;

abstract class AbstractFormula implements Formula {

    @Override
    public Formula or(final Formula f) {
        return flattenOr(this, f);
    }

    @Override
    public Formula and(final Formula f) {
        return flattenAnd(this, f);
    }

    @Override
    public Formula neg() {
        return new Neg(this);
    }

    private static Formula flattenOr(final Formula f1, final Formula f2) {
        if (f1 instanceof Or or1 && f2 instanceof Or or2) {
            final var formulas = new HashSet<>(or1.fs());
            formulas.addAll(or2.fs());
            return new Or(unmodifiableSet(formulas));
        } else if (f1 instanceof Or or1) {
            final var formulas = new HashSet<>(or1.fs());
            formulas.add(f2);
            return new Or(unmodifiableSet(formulas));
        } else if (f2 instanceof Or or2) {
            final var formulas = new HashSet<>(or2.fs());
            formulas.add(f1);
            return new Or(unmodifiableSet(formulas));
        } else if (f1.equals(f2)) {
            return f1;
        } else {
            return new Or(Set.of(f1, f2));
        }
    }

    private static Formula flattenAnd(final Formula f1, final Formula f2) {
        if (f1 instanceof And and1 && f2 instanceof And and2) {
            final var formulas = new HashSet<>(and1.fs());
            formulas.addAll(and2.fs());
            return new And(unmodifiableSet(formulas));
        } else if (f1 instanceof And and1) {
            final var formulas = new HashSet<>(and1.fs());
            formulas.add(f2);
            return new And(unmodifiableSet(formulas));
        } else if (f2 instanceof And and2) {
            final var formulas = new HashSet<>(and2.fs());
            formulas.add(f1);
            return new And(unmodifiableSet(formulas));
        } else if (f1.equals(f2)) {
            return f1;
        } else {
            return new And(Set.of(f1, f2));
        }
    }
}
