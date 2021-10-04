package org.github.trushev.logicenum.eval;

import static java.util.Collections.unmodifiableList;
import static java.util.stream.Collectors.toUnmodifiableSet;
import static org.github.trushev.logicenum.formula.Formula.*;

import java.util.*;
import java.util.function.BiFunction;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.github.trushev.logicenum.formula.*;

public final class TruthTable {

    private final Formula f;
    private final Collection<Formula> vars;
    private final List<List<Const>> table;

    public TruthTable(Formula f) {
        this.f = f;
        vars = f.vars().toList();
        table = table(this.f, vars);
    }

    public Formula f() {
        return f;
    }

    public Collection<Formula> vars() {
        return vars;
    }

    public Stream<List<Const>> rows() {
        return table.stream();
    }

    @Override
    public String toString() {
        return new CsvTruthTable(this).toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TruthTable that)) {
            return false;
        }
        return table.equals(that.table);
    }

    @Override
    public int hashCode() {
        return Objects.hash(table);
    }

    private static List<List<Const>> table(Formula f, Collection<Formula> vars) {
        var n = vars.size();
        var res = new ArrayList<List<Const>>((int) Math.pow(3, n));
        var row = new int[n];
        while (row != null) {
            var vals = decode(row);
            var ff = assign(f, vars, vals);
            var eval = eval(ff);
            vals.add(eval);
            res.add(unmodifiableList(vals));
            row = next(row);
        }
        return unmodifiableList(res);
    }

    private static List<Const> decode(int[] row) {
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

    private static int[] next(int[] row) {
        var n = row.length;
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

    private static Formula assign(Formula f, Collection<Formula> vars, Collection<Const> vals) {
        var varsIter = vars.iterator();
        var valsIter = vals.iterator();
        var res = f;
        while (varsIter.hasNext()) {
            var var = varsIter.next();
            var val = valsIter.next();
            res = assign(res, var, val);
        }
        if (valsIter.hasNext()) throw new IllegalStateException();
        return res;
    }

    private static Formula assign(Formula f, Formula var, Const val) {
        if (f.equals(var)) {
            return val;
        }
        return switch (f) {
            case Const c -> c;
            case Var v -> v;
            case Not not -> not(assign(operand(not), var, val));
            case IsNull isNull -> isNull(assign(operand(isNull), var, val));
            case And and -> and(and.map(ff -> assign(ff, var, val)));
            case Or or -> or(or.map(ff -> assign(ff, var, val)));
        };
    }

    private static Const eval(Formula f) {
        switch (f) {
            case Const c: return c;
            case Var v: throw new IllegalStateException(v.toString());
            case Not n: return evalNot(eval(operand(n)));
            case IsNull i: return evalIsNull(eval(operand(i)));
            default:
        }
        var values = f.map(TruthTable::eval).collect(toUnmodifiableSet());
        var iterator = values.iterator();
        BiFunction<Const, Const, Const> evalConst = switch (f) {
            case And ignored -> TruthTable::evalAnd;
            case Or ignored -> TruthTable::evalOr;
            default -> throw new IllegalStateException("Unexpected value: " + f);
        };
        var v = iterator.next();
        while (iterator.hasNext()) {
            v = evalConst.apply(v, iterator.next());
        }
        return v;
    }

    private static Const evalOr(Const c1, Const c2) {
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

    private static Const evalAnd(Const c1, Const c2) {
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

    private static Const evalNot(Const c) {
        if (c == Const.True) return Const.False;
        if (c == Const.False) return Const.True;
        if (c == Const.Unknown) return Const.Unknown;

        throw new IllegalStateException(c.toString());
    }

    private static Const evalIsNull(Const c) {
        if (c == Const.True) return Const.False;
        if (c == Const.False) return Const.False;
        if (c == Const.Unknown) return Const.True;

        throw new IllegalStateException(c.toString());
    }
}
