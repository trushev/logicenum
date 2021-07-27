package org.github.logicenum.formula;


import java.util.*;

import static java.util.Collections.unmodifiableSet;
import static org.github.logicenum.formula.BiFormula.*;
import static org.github.logicenum.formula.BiFormula.Op.AND;
import static org.github.logicenum.formula.BiFormula.Op.OR;

abstract class AbstractFormula implements Formula {

    @Override
    public Formula or(final Formula f) {
        final var flatten = flatten(OR, this, f);
        if (flatten.size() == 1) {
            return flatten.iterator().next();
        }
        return new Or(flatten);
    }

    @Override
    public Formula and(final Formula f) {
        final var flatten = flatten(AND, this, f);
        if (flatten.size() == 1) {
            return flatten.iterator().next();
        }
        return new And(flatten);
    }

    @Override
    public Formula neg() {
        return new Neg(this);
    }

    static Collection<Formula> flatten(final Op op, final Formula f1, final Formula f2) {
        if ((f1 instanceof BiFormula bf1) && bf1.op == op && (f2 instanceof BiFormula bf2) && bf2.op == op) {
            final var formulas = new ArrayList<Formula>(bf1.fs.size() + bf2.fs.size());
            formulas.addAll(bf1.fs);
            formulas.addAll(bf2.fs);
            return unmodifiableSet(new LinkedHashSet<>(formulas));
        } else if ((f1 instanceof BiFormula bf1) && bf1.op == op) {
            final var formulas = new ArrayList<Formula>(bf1.fs.size() + 1);
            formulas.addAll(bf1.fs);
            formulas.add(f2);
            return unmodifiableSet(new LinkedHashSet<>(formulas));
        } else if ((f2 instanceof BiFormula bf2) && bf2.op == op) {
            final var formulas = new ArrayList<Formula>(1 + bf2.fs.size());
            formulas.add(f1);
            formulas.addAll(bf2.fs);
            return unmodifiableSet(new LinkedHashSet<>(formulas));
        } else {
            final var formulas = new ArrayList<Formula>(2);
            formulas.add(f1);
            formulas.add(f2);
            return unmodifiableSet(new LinkedHashSet<>(formulas));
        }
    }
}
