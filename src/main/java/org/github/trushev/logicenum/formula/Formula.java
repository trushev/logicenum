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

    default <R> Stream<R> map(Function<? super Formula, ? extends R> mapper) {
        return operands().map(mapper);
    }

    static Formula var(String name) {
        return new Var(name);
    }

    static Formula or(Formula f1, Formula f2, Formula... fs) {
        var res = f1.or(f2);
        for (var f : fs) {
            res = res.or(f);
        }
        return res;
    }

    static Formula and(Formula f1, Formula f2, Formula... fs) {
        var res = f1.and(f2);
        for (var f : fs) {
            res = res.and(f);
        }
        return res;
    }

    static Formula not(Formula f) {
        return f.not();
    }

    static Formula isNull(Formula f) {
        return f.isNull();
    }

    static Formula operand(Not not) {
        return not.operands().findAny().orElseThrow();
    }

    static Formula operand(IsNull isNull) {
        return isNull.operands().findAny().orElseThrow();
    }

    static Formula first(Collection<Formula> formulas) {
        return formulas.iterator().next();
    }

    static Collection<Formula> rest(Collection<Formula> formulas) {
        return formulas.stream().skip(1).toList();
    }

    static Collection<Formula> not(Collection<Formula> formulas) {
        return formulas.stream().map(f -> f.not()).toList();
    }

    static Formula and(Collection<Formula> formulas) {
        return Utils.and(formulas.iterator());
    }

    static Formula and(Stream<Formula> formulas) {
        return Utils.and(formulas.iterator());
    }

    static Formula or(Collection<Formula> formulas) {
        return Utils.or(formulas.iterator());
    }

    static Formula or(Stream<Formula> formulas) {
        return Utils.or(formulas.iterator());
    }
}
