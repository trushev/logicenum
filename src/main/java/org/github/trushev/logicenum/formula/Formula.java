package org.github.trushev.logicenum.formula;

import java.util.Collection;
import java.util.function.Function;
import java.util.stream.Stream;

public sealed interface Formula permits AbstractFormula {
    Stream<Formula> operands();

    Stream<Formula> vars();

    Formula or(Formula f);

    Formula and(Formula f);

    Formula not();

    Formula isNull();

    int length();

    boolean deepEquals(Formula f);

    boolean consistsOnly(Collection<Formula> fs);

    default <R> Stream<R> map(final Function<? super Formula, ? extends R> mapper) {
        return operands().map(mapper);
    }

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

    static Formula isNull(final Formula f) {
        return f.isNull();
    }

    static Formula operand(final Not not) {
        return not.operands().findAny().orElseThrow();
    }

    static Formula operand(final IsNull isNull) {
        return isNull.operands().findAny().orElseThrow();
    }

    static Formula first(final Collection<Formula> formulas) {
        return formulas.iterator().next();
    }

    static Collection<Formula> rest(final Collection<Formula> formulas) {
        return formulas.stream().skip(1).toList();
    }

    static Collection<Formula> not(final Collection<Formula> formulas) {
        return formulas.stream().map(f -> f.not()).toList();
    }

    static Formula and(final Collection<Formula> formulas) {
        return Utils.and(formulas.iterator());
    }

    static Formula and(final Stream<Formula> formulas) {
        return Utils.and(formulas.iterator());
    }

    static Formula or(final Collection<Formula> formulas) {
        return Utils.or(formulas.iterator());
    }

    static Formula or(final Stream<Formula> formulas) {
        return Utils.or(formulas.iterator());
    }
}
