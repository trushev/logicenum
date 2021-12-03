package org.github.trushev.logicenum.formula;

import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.function.BiFunction;
import org.github.trushev.logicenum.eval.CsvTruthTable;
import org.github.trushev.logicenum.eval.SingleTruthTable;
import org.github.trushev.logicenum.eval.TruthTable;

// TODO: discard utility class
final class Utils {

    private Utils() {}

    static Collection<Formula> vars(Formula f) {
        return switch (f) {
            case AbstractVarsFormula a -> a.vars;
            case Var v -> Collections.singleton(v);
            default -> Collections.emptyList();
        };
    }

    static Collection<Formula> vars(Collection<Formula> fs) {
        return fs.stream().flatMap(Formula::vars).sorted().distinct().toList();
    }

    static int length(Collection<Formula> fs) {
        // TODO: possible bug
        //  length of (a | b | c) should be 4 or 5?
        return fs.stream().mapToInt(Formula::length).sum() + 1;
    }

    static Formula or(Iterator<Formula> formulas) {
        return wrappedFormulas(formulas, Const.False, (f1, f2) -> f1.or(f2)); // TODO: IDEA is wrong about method reference
    }

    static Formula and(Iterator<Formula> formulas) {
        return wrappedFormulas(formulas, Const.True, (f1, f2) -> f1.and(f2)); // TODO: IDEA is wrong about method reference
    }

    private static Formula wrappedFormulas(
        Iterator<Formula> formulas,
        Const weekConst,
        BiFunction<Formula, Formula, Formula> fun
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

    static boolean deepEquals(Formula f1, Formula f2) {
        var t1 = new SingleTruthTable(f1);
        var t2 = new SingleTruthTable(f2);
        boolean result;
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

    private static boolean tableEqualsToConst(TruthTable tt, Const c) {
        return tt.rows().map(r -> r.get(r.size() - 1)).filter(r -> !c.equals(r)).findAny().isEmpty();
    }
}
