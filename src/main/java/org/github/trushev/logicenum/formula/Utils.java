package org.github.trushev.logicenum.formula;

import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.function.BiFunction;
import org.github.trushev.logicenum.eval.CsvTruthTable;
import org.github.trushev.logicenum.eval.TruthTable;

// TODO: discard utility class
final class Utils {

    private Utils() {}

    static Collection<Formula> vars(final Formula f) {
        if (f instanceof AbstractVarsFormula a) {
            return a.vars;
        }
        if (f instanceof Var) {
            return Collections.singleton(f);
        }
        return Collections.emptyList();
    }

    static Collection<Formula> vars(final Collection<Formula> fs) {
        return fs.stream().flatMap(Formula::vars).sorted().distinct().toList();
    }

    static int length(final Collection<Formula> fs) {
        // TODO: possible bug
        //  length of (a | b | c) should be 4 or 5?
        return fs.stream().mapToInt(Formula::length).sum() + 1;
    }

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
    ) {
        if (!formulas.hasNext()) {
            return weekConst;
        }
        var f = formulas.next();
        while (formulas.hasNext()) {
            f = fun.apply(f, formulas.next());
        }
        return f;
    }

    static boolean deepEquals(final Formula f1, final Formula f2) {
        final var t1 = new TruthTable(f1);
        final var t2 = new TruthTable(f2);
        final boolean result;
        if (f1 instanceof Const c1 && !(f2 instanceof Const)) {
            result = tableEqualsToConst(t2, c1);
        } else if (f2 instanceof Const c2 && !(f1 instanceof Const)) {
            result = tableEqualsToConst(t1, c2);
        } else {
            result = t1.equals(t2);
        }
        if (!result) {
            System.out.println(new CsvTruthTable(t1, " ", false));
            System.out.println();
            System.out.println(new CsvTruthTable(t2, " ", false));
            System.out.println();
        }
        return result;
    }

    private static boolean tableEqualsToConst(final TruthTable tt, final Const c) {
        return tt.rows().map(r -> r.get(r.size() - 1)).filter(r -> !c.equals(r)).findAny().isEmpty();
    }
}
