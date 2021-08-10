package org.github.trushev.logicenum.formula;

import org.github.trushev.logicenum.eval.CsvTruthTable;
import org.github.trushev.logicenum.eval.TruthTable;

import java.util.Collection;
import java.util.stream.Stream;

abstract class AbstractFormula implements Formula {

    private Collection<Formula> vars;

    @Override
    public Formula or(final Formula f) {
        return Or.of(this, f);
    }

    @Override
    public Formula and(final Formula f) {
        return And.of(this, f);
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
    public Stream<Formula> vars() {
        if (this.vars == null) {
            this.vars = operands()
                    .flatMap(Formula::vars)
                    .sorted()
                    .distinct()
                    .toList();
        }
        return this.vars.stream();
    }

    @Override
    public boolean consistsOnly(final Collection<Formula> fs) {
        final var vars = vars().toList();
        if (vars.isEmpty()) {
            return false; // TODO: why?
        }
        return vars.stream()
                .filter(v -> !fs.contains(v))
                .findAny()
                .isEmpty();
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
