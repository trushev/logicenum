package org.github.trushev.logicenum.eval;

import org.github.trushev.logicenum.formula.*;

import java.util.*;
import java.util.stream.Collectors;

import static java.util.Collections.unmodifiableList;
import static org.github.trushev.logicenum.formula.Formula.*;

public final class TruthTable {

    private final Formula f;
    private final List<List<Const>> table;

    public TruthTable(final Formula f) {
        this.f = f;
        this.table = table(f);
    }

    @Override
    public String toString() {
        final String s = "TruthTable\nf:" + this.f + "\n";
        final var sb = new StringBuilder();
        this.table.forEach(r -> sb.append(r.toString()).append("\n"));
        return s + sb;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (!(o instanceof TruthTable that)) return false;
        return this.table.equals(that.table);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.table);
    }

    private static List<List<Const>> table(final Formula f) {
        final var vars = f.vars();
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
        return Arrays.stream(row).mapToObj(i -> switch (i) {
            case 0 -> Const.False;
            case 1 -> Const.Unknown;
            case 2 -> Const.True;
            default -> throw new IllegalStateException(String.valueOf(i));
        }).collect(Collectors.toList());
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
        if (f instanceof Const) {
            return f;
        }
        if (f instanceof Var) {
            return f;
        }
        if (f instanceof Not n) {
            return not(assign(operand(n), var, val));
        }
        if (f instanceof IsNull) {
            return isNull(assign(f.operands().iterator().next(), var, val));
        }
        final var formulas = f.operands()
                .stream()
                .map(ff -> assign(ff, var, val))
                .collect(Collectors.toUnmodifiableSet());
        if (f instanceof And) {
            return and(formulas);
        }
        if (f instanceof Or) {
            return or(formulas);
        }
        throw new IllegalStateException(f.getClass().toString());
    }

    private static Const eval(final Formula f) {
        if (f instanceof Const c) {
            return c;
        }
        if (f instanceof Var) {
            throw new IllegalStateException(f.toString());
        }
        if (f instanceof Not n) {
            final var arg = operand(n);
            return evalNot(eval(arg));
        }
        if (f instanceof IsNull) {
            final var arg = f.operands().iterator().next();
            return evalIsNull(eval(arg));
        }
        final var values = f.operands()
                .stream()
                .map(TruthTable::eval)
                .collect(Collectors.toUnmodifiableSet());
        final var iterator = values.iterator();
        var v = iterator.next();
        if (f instanceof And) {
            while (iterator.hasNext()) {
                v = evalAnd(v, iterator.next());
            }
            return v;
        }
        if (f instanceof Or) {
            while (iterator.hasNext()) {
                v = evalOr(v, iterator.next());
            }
            return v;
        }

        throw new IllegalStateException(f.toString());
    }

    private static Const evalOr(final Const c1, final Const c2) {
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
