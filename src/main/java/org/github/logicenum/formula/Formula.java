package org.github.logicenum.formula;


import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import static java.util.Collections.unmodifiableList;

public interface Formula {

    Collection<Formula> operands();

    int length();

    Formula or(Formula f);

    Formula and(Formula f);

    Formula not();

    boolean deepEquals(Formula f);

    Collection<Formula> vars();

    boolean consistsOnly(Collection<Formula> fs);

    static Formula var(final String name) {
        return new Var(name);
    }

    static Formula or(final Formula f1, final Formula f2, final Formula... fs) {
        var res = f1.or(f2);
        for (final var f : fs) {
            res = res.or(f);
        }
        return res;
    }

    static Formula and(final Formula f1, final Formula f2, final Formula... fs) {
        var res = f1.and(f2);
        for (final var f : fs) {
            res = res.and(f);
        }
        return res;
    }

    static Formula not(final Formula f) {
        return f.not();
    }

    static Collection<Formula> operands(final Formula f) {
        return f.operands();
    }

    static Formula operand(final Not not) {
        return first(not.operands());
    }

    static Formula first(final Collection<Formula> formulas) {
        return formulas.iterator().next();
    }

    static Collection<Formula> rest(final Collection<Formula> formulas) {
        final var iterator = formulas.iterator();
        iterator.next();
        final var rest = new ArrayList<Formula>();
        while (iterator.hasNext()) {
            rest.add(iterator.next());
        }
        return unmodifiableList(rest);
    }

    static Collection<Formula> not(final Collection<Formula> formulas) {
        final var res = new ArrayList<Formula>();
        for (final var f : formulas) {
            res.add(f.not());
        }
        return unmodifiableList(res);
    }

    static Formula and(final Collection<Formula> formulas) {
        final var iterator = formulas.iterator();
        var f = iterator.next();
        while (iterator.hasNext()) {
            f = f.and(iterator.next());
        }
        return f;
    }

    static Formula or(final Collection<Formula> formulas) {
        final var iterator = formulas.iterator();
        var f = iterator.next();
        while (iterator.hasNext()) {
            f = f.or(iterator.next());
        }
        return f;
    }

    static Collection<Formula> disjunctions(final Formula f) {
        if (f instanceof Or) {
            return f.operands();
        } else {
            return Collections.singletonList(f);
        }
    }
}
