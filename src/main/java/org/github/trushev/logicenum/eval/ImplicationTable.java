package org.github.trushev.logicenum.eval;

import java.util.List;
import java.util.stream.Stream;
import org.github.trushev.logicenum.formula.Const;
import org.github.trushev.logicenum.formula.Formula;

public class ImplicationTable implements TruthTable {

    private final TruthTable table;

    public ImplicationTable(TruthTable table) {
        this.table = table;
    }

    @Override
    public Stream<Formula> formulas() {
        return table.formulas();
    }

    @Override
    public Stream<Formula> vars() {
        return table.vars();
    }

    @Override
    public Stream<List<Const>> rows() {
        return table(table);
    }

    private static Stream<List<Const>> table(TruthTable table) {
        var count = table.formulas().count();
        if (count != 2) {
            // TODO: support count != 2
            throw new AssertionError("Currently, only two formulas implication check is supported");
        }
        return table
            .rows()
            .filter(r -> {
                var size = r.size();
                var f1 = r.get(size - 2);
                var f2 = r.get(size - 1);
                return f1.equals(Const.True) && !f2.equals(Const.True);
            });
    }
}
