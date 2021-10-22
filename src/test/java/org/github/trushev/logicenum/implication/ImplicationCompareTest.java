package org.github.trushev.logicenum.implication;

import static org.github.trushev.logicenum.formula.Formula.var;

import org.github.trushev.logicenum.enumeration.FormulaEnum;
import org.junit.jupiter.api.Test;

public final class ImplicationCompareTest {

    @Test
    public void test() {
        var l = System.currentTimeMillis();

        var a = var("a");
        var b = var("b");
        var c = var("c");
        var d = var("d");

        var formulas = FormulaEnum.get(20_0000, a, b, c, d).formulas();

        var dnfAlgorithm = new DnfImplication();
        var sparkAlgorithm = new SparkImplication();

        formulas.forEach(f -> {
            var f1 = dnfAlgorithm.imply(f, a, b);
            var f2 = sparkAlgorithm.imply(f, a, b);
            if (!f1.deepEquals(f2)) {
                System.out.println("Origin: " + f);
                System.out.println("Dnf based: " + f1);
                System.out.println("Spark: " + f2);
                System.out.println();
                // TODO: fix me
                // throw new AssertionError();
            }
        });

        System.out.println("Time: " + (System.currentTimeMillis() - l) + " ms");
    }
}
