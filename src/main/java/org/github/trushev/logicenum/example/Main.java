package org.github.trushev.logicenum.example;

import static org.github.trushev.logicenum.formula.Formula.*;

import org.github.trushev.logicenum.eval.CsvTruthTable;
import org.github.trushev.logicenum.eval.TruthTable;

public final class Main {

    public static void main(String... args) {
        var a = var("a");
        var b = var("b");
        var f = and(a, b, or(not(a), isNull(b)));
        System.out.println(f);

        var table = new CsvTruthTable(new TruthTable(f));
        System.out.println(table);
    }
}
