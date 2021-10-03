package org.github.trushev.logicenum.eval;

import static java.util.Collections.unmodifiableList;
import static java.util.stream.Collectors.toUnmodifiableSet;
import static org.github.trushev.logicenum.formula.Formula.*;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.github.trushev.logicenum.formula.*;

public final class TruthTable {

    private final Formula f;
    private final Collection<Formula> vars;
    private final List<List<Const>> table;

    public TruthTable(final Formula f) {
        this.f = f;
        this.vars = f.vars().toList();
        this.table = table(this.f, this.vars);
    }

    public Formula f() {
        return this.f;
    }

    public Collection<Formula> vars() {
        return this.vars;
    }

    public Stream<List<Const>> rows() {
        return this.table.stream();
    }

    @Override
    public String toString() {
        return new CsvTruthTable(this).toString();
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (!(o instanceof TruthTable that)) {
            return false;
        }
        return this.table.equals(that.table);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.table);
    }

    private static List<List<Const>> table(final Formula f, final Collection<Formula> vars) {
        final var n = vars.size();
        final var res = new ArrayList<List<Const>>((int) Math.pow(3, n));
        var row = new int[n];
        while (row != null) {
            final var vals = decode(row);
            final var ff = assign(f, vars, vals);
            final var eval = eval(ff);
            vals.add(eval);
            res.add(unmodifiableList(vals));
            row = next(row);
        }
        return unmodifiableList(res);
    }

    private static List<Const> decode(final int[] row) {
        return Arrays
            .stream(row)
            .mapToObj(
                i ->
                    switch (i) {
                        case 0 -> Const.False;
                        case 1 -> Const.Unknown;
                        case 2 -> Const.True;
                        default -> throw new IllegalStateException(String.valueOf(i));
                    }
            )
            .collect(Collectors.toList());
    }

    private static int[] next(final int[] row) {
        final var n = row.length;
        int i = 0;
        while (i < n && row[i] == 2) {
            row[i] = 0;
            i++;
        }
        if (i == n) {
            return null;
        }
        if (row[i] == 1) {
            row[i] = 2;
            return row;
        }
        if (row[i] == 0) {
            row[i] = 1;
            return row;
        }
        throw new IllegalStateException(Arrays.toString(row));
    }

    private static Formula assign(final Formula f, final Collection<Formula> vars, final Collection<Const> vals) {
        final var varsIter = vars.iterator();
        final var valsIter = vals.iterator();
        var res = f;
        while (varsIter.hasNext()) {
            final var var = varsIter.next();
            final var val = valsIter.next();
            res = assign(res, var, val);
        }
        if (valsIter.hasNext()) throw new IllegalStateException();
        return res;
    }

    private static Formula assign(final Formula f, final Formula var, final Const val) {
        if (f.equals(var)) {
            return val;
        }
        return switch (f) {
            case final Const c -> c;
            case final Var v -> v;
            case final Not not -> not(assign(operand(not), var, val));
            case final IsNull isNull -> isNull(assign(operand(isNull), var, val));
            case final And and -> and(and.map(ff -> assign(ff, var, val)));
            case final Or or -> or(or.map(ff -> assign(ff, var, val)));
        };
    }

    private static Const eval(final Formula f) {
        switch (f) {
            case final Const c: return c;
            case final Var ignored: throw new IllegalStateException(f.toString());
            case final Not n: return evalNot(eval(operand(n)));
            case final IsNull i: return evalIsNull(eval(operand(i)));
            default:
        }
        final var values = f.map(TruthTable::eval).collect(toUnmodifiableSet());
        final var iterator = values.iterator();
        var v = iterator.next();
        return switch (f) {
            case final And ignored -> {
                while (iterator.hasNext()) {
                    v = evalAnd(v, iterator.next());
                }
                yield v;
            }
            case final Or ignored -> {
                while (iterator.hasNext()) {
                    v = evalOr(v, iterator.next());
                }
                yield v;
            }
            default -> throw new IllegalStateException("Unexpected value: " + f);
        };
    }

    private static Const evalOr(final Const c1, final Const c2) {
        // return max(c1, c2)

        if (c1 == Const.True && c2 == Const.True) return Const.True;
        if (c1 == Const.True && c2 == Const.False) return Const.True;
        if (c1 == Const.True && c2 == Const.Unknown) return Const.True;

        if (c1 == Const.False && c2 == Const.True) return Const.True;
        if (c1 == Const.False && c2 == Const.False) return Const.False;
        if (c1 == Const.False && c2 == Const.Unknown) return Const.Unknown;

        if (c1 == Const.Unknown && c2 == Const.True) return Const.True;
        if (c1 == Const.Unknown && c2 == Const.False) return Const.Unknown;
        if (c1 == Const.Unknown && c2 == Const.Unknown) return Const.Unknown;

        throw new IllegalStateException(c1.toString() + " " + c2.toString());
    }

    private static Const evalAnd(final Const c1, final Const c2) {
        // return min(c1, c2)

        if (c1 == Const.True && c2 == Const.True) return Const.True;
        if (c1 == Const.True && c2 == Const.False) return Const.False;
        if (c1 == Const.True && c2 == Const.Unknown) return Const.Unknown;

        if (c1 == Const.False && c2 == Const.True) return Const.False;
        if (c1 == Const.False && c2 == Const.False) return Const.False;
        if (c1 == Const.False && c2 == Const.Unknown) return Const.False;

        if (c1 == Const.Unknown && c2 == Const.True) return Const.Unknown;
        if (c1 == Const.Unknown && c2 == Const.False) return Const.False;
        if (c1 == Const.Unknown && c2 == Const.Unknown) return Const.Unknown;

        throw new IllegalStateException(c1.toString() + " " + c2.toString());
    }

    private static Const evalNot(final Const c) {
        if (c == Const.True) return Const.False;
        if (c == Const.False) return Const.True;
        if (c == Const.Unknown) return Const.Unknown;

        throw new IllegalStateException(c.toString());
    }

    private static Const evalIsNull(final Const c) {
        if (c == Const.True) return Const.False;
        if (c == Const.False) return Const.False;
        if (c == Const.Unknown) return Const.True;

        throw new IllegalStateException(c.toString());
    }
}
