package org.github.logicenum;


interface Formula {

    static Formula var(final String name) {
        return new Var(name);
    }

    static Formula or(final Formula... fs) {
        final var flatten = BiFormula.flatten(BiFormula.Op.or, fs);
        if (flatten.size() == 1) {
            return flatten.iterator().next();
        }
        return new Or(flatten);
    }

    static Formula and(final Formula... fs) {
        final var flatten = BiFormula.flatten(BiFormula.Op.and, fs);
        if (flatten.size() == 1) {
            return flatten.iterator().next();
        }
        return new And(flatten);
    }

    static Formula neg(final Formula f) {
        if (f instanceof Neg) {
            return f;
        }
        if (f instanceof Const c) {
            return c.neg();
        }
        return new Neg(f);
    }
}
