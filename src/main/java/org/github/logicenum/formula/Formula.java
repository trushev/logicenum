package org.github.logicenum.formula;


public interface Formula {

    int length();

    Formula or(Formula f);

    Formula and(Formula f);

    Formula neg();

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

    static Formula neg(final Formula f) {
        return f.neg();
    }
}
