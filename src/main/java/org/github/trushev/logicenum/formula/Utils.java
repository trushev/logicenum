package org.github.trushev.logicenum.formula;

import java.util.Iterator;
import java.util.function.BiFunction;

// TODO: discard utility class
final class Utils {

    private Utils() {}

    static Formula or(final Iterator<Formula> formulas) {
        return wrappedFormulas(formulas, Const.False, (f1, f2) -> f1.or(f2)); // TODO: IDEA is wrong about method reference
    }

    static Formula and(final Iterator<Formula> formulas) {
        return wrappedFormulas(formulas, Const.True, (f1, f2) -> f1.and(f2)); // TODO: IDEA is wrong about method reference
    }

    private static Formula wrappedFormulas(
            final Iterator<Formula> formulas,
            final Const weekConst,
            final BiFunction<Formula, Formula, Formula> fun
    )  {
        if (!formulas.hasNext()) {
            return weekConst;
        }
        var f = formulas.next();
        while (formulas.hasNext()) {
            f = fun.apply(f, formulas.next());
        }
        return f;
    }
}
