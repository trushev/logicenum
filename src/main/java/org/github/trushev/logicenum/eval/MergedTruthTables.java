package org.github.trushev.logicenum.eval;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;
import org.github.trushev.logicenum.formula.Const;
import org.github.trushev.logicenum.formula.Formula;

public final class MergedTruthTables implements TruthTable {

    private final List<Formula> fs;

    public MergedTruthTables(Formula... fs) {
        this.fs = Arrays.asList(fs);
    }

    @Override
    public Stream<Formula> formulas() {
        return fs.stream();
    }

    @Override
    public Stream<Formula> vars() {
        // TODO: merge fs vars
        return fs.get(0).vars();
    }

    @Override
    public Stream<List<Const>> rows() {
        var tables = fs.stream().map(SingleTruthTable::new).toList();
        var mergedTableBuilder = tables.get(0).rows().map(ArrayList::new).toList();
        for (int i = 1; i < tables.size(); i++) {
            var table = tables.get(i);
            var rows = table.rows().toList();
            for (int j = 0; j < rows.size(); j++) {
                var row = rows.get(j);
                var fVal = row.get(row.size() - 1);
                mergedTableBuilder.get(j).add(fVal);
            }
        }
        var mergedTable = mergedTableBuilder.stream().map(Collections::unmodifiableList).toList();
        return mergedTable.stream();
    }
}
