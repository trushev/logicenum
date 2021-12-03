package org.github.trushev.logicenum.eval;

import static org.github.trushev.logicenum.formula.Formula.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.github.trushev.logicenum.formula.Const;
import org.junit.jupiter.api.Test;

public final class CsvTruthTableTest {

    @Test
    public void testSingleTable() {
        var f = var("a").and(var("b"));
        var table = new CsvTruthTable(new SingleTruthTable(f));
        assertEquals(
            """
                a, b, f
                0.0, 0.0, 0.0
                0.5, 0.0, 0.0
                1.0, 0.0, 0.0
                0.0, 0.5, 0.0
                0.5, 0.5, 0.5
                1.0, 0.5, 0.5
                0.0, 1.0, 0.0
                0.5, 1.0, 0.5
                1.0, 1.0, 1.0""",
            table.toString()
        );
    }

    @Test
    public void testSingleTable1() {
        var f = var("a").or(var("b"));
        var table = new CsvTruthTable(new SingleTruthTable(f), " ", false);
        assertEquals(
            """
                a b f
                False False False
                Unknown False Unknown
                True False True
                False Unknown Unknown
                Unknown Unknown Unknown
                True Unknown True
                False True True
                Unknown True True
                True True True""",
            table.toString()
        );
    }

    @Test
    public void testSingleTable2() {
        var table = new CsvTruthTable(new SingleTruthTable(Const.False));
        assertEquals("""
                f
                0.0""", table.toString());
    }

    @Test
    public void test4() {
        var f = and(var("a"), var("b"));
        var f1 = or(var("a"), var("b"));
        var table = new CsvTruthTable(new MergedTruthTables(f, f1));
        System.out.println(table);
    }
}
