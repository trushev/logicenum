package org.github.trushev.logicenum.formula;


import org.github.trushev.logicenum.eval.CsvTruthTable;
import org.github.trushev.logicenum.eval.TruthTable;

import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.Collections.unmodifiableSet;

abstract class AbstractFormula implements Formula {

    private Collection<Formula> vars;

    @Override
    public Formula or(final Formula f) {
        return orConstFolding(flattenOr(this, f));
    }

    @Override
    public Formula and(final Formula f) {
        return andConstFolding(flattenAnd(this, f));
    }

    @Override
    public Formula not() {
        return new Not(this);
    }

    @Override
    public Formula isNull() {
        return new IsNull(this);
    }

    @Override
    public boolean deepEquals(final Formula f) {
        return this.equals(f) || deepEquals(this, f);
    }

    @Override
    public Collection<Formula> vars() {
        if (this.vars == null) {
            this.vars = operands().stream()
                    .flatMap(ff -> ff.vars().stream())
                    .sorted()
                    .distinct()
                    .toList();
        }
        return this.vars;
    }

    @Override
    public boolean consistsOnly(final Collection<Formula> fs) {
        final var vars = vars();
        if (vars.isEmpty()) {
            return false; // TODO: why?
        }
        for (final var var : vars) {
            if (!fs.contains(var)) {
                return false;
            }
        }
        return true;
    }

    private static Formula orConstFolding(final Formula f) {
        if (f.operands().contains(Const.True)) {
            return Const.True;
        }
        return f;
    }

    private static Formula andConstFolding(final Formula f) {
        if (f.operands().contains(Const.False)) {
            return Const.False;
        }
        return f;
    }

    private static Formula flattenOr(final Formula f1, final Formula f2) {
        return flatten(f1, f2, f -> (f instanceof Or), Or::new);
    }

    private static Formula flattenAnd(final Formula f1, final Formula f2) {
        return flatten(f1, f2, f -> (f instanceof And), And::new);
    }

    private static Formula flatten(
            final Formula f1,
            final Formula f2,
            final Predicate<Formula> p,
            final Function<Collection<Formula>, Formula> fun
    ) {
        final var formulas = Stream.of(f1, f2)
                .flatMap(f -> p.test(f)
                        ? f.operands().stream()
                        : Stream.of(f))
                .collect(Collectors.toCollection(LinkedHashSet::new));
        if (formulas.size() == 1) {
            return formulas.iterator().next();
        }
        return fun.apply(unmodifiableSet(formulas));
    }

    private static boolean deepEquals(final Formula f1, final Formula f2) {
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
        final var any = tt.rows()
                .map(r -> r.get(r.size() - 1))
                .filter(r -> !c.equals(r))
                .findAny();
        return any.isEmpty();
    }
}
