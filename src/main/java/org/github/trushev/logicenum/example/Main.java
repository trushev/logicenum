package org.github.trushev.logicenum.example;

import org.github.trushev.logicenum.eval.CsvTruthTable;
import org.github.trushev.logicenum.eval.TruthTable;

import java.util.Arrays;

import static org.github.trushev.logicenum.formula.Formula.*;

public final class Main {
    public static void main(final String... args) {
        var a = var("a");
        var b = var("b");
        var f = and(a, b, or(not(a), isNull(b)));
        System.out.println(f);

        var table = new CsvTruthTable(new TruthTable(f));
        System.out.println(table);

        final var ints = new int[]{1, 2, 3};
        final var ints1 = new int[]{1, 2, 3};
        System.out.println(Arrays.equals(ints, ints1));
    }
}
