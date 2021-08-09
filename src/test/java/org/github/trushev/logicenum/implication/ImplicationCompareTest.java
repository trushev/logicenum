package org.github.trushev.logicenum.implication;

import org.github.trushev.logicenum.enumeration.FormulaEnum;
import org.junit.jupiter.api.Test;

import static org.github.trushev.logicenum.formula.Formula.var;

public class ImplicationCompareTest {

    @Test
    public void test() {
        final var l = System.currentTimeMillis();

        final var a = var("a");
        final var b = var("b");
        final var c = var("c");
        final var d = var("d");
//        final var e = var("e");

        final var formulas = FormulaEnum.get(20_000_0, a, b, c, d).formulas();

        final var dnfAlgorithm = new DnfImplication();
        final var sparkAlgorithm = new SparkImplication();

        formulas.forEach(f -> {
            final var f1 = dnfAlgorithm.ex(f, a, b);
            final var f2 = sparkAlgorithm.ex(f, a, b);
//            if (!f1.equals(f2)) {
//                System.out.println("Origin: " + f);
//
//                System.out.println("Dnf based: " + f1);
//                final var t1 = new TruthTable(f1);
//                System.out.println("Table1");
//                System.out.println(t1);
//                System.out.println();
//
//                System.out.println("Spark: " + f2);
//                final var t2 = new TruthTable(f2);
//                System.out.println("Table2");
//                System.out.println(t2);
//                System.out.println();
//                throw new AssertionError();
//            }
            if (!f1.equals(f2) && !f1.deepEquals(f2)) {
                System.out.println("Origin: " + f);
                System.out.println("Dnf based: " + f1);
                System.out.println("Spark: " + f2);
                System.out.println();
                throw new AssertionError();
            }
        });
        System.out.println("Time: " + (System.currentTimeMillis() - l) + " ms");
    }
}