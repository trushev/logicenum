package org.github.trushev.logicenum;

import org.github.trushev.logicenum.extract.DnfAlgorithm;
import org.github.trushev.logicenum.extract.SparkAlgorithm;
import org.github.trushev.logicenum.enu.outer.FormulasEnum;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.github.trushev.logicenum.formula.Formula.var;

public class Main {

    private static final boolean WRITE = false;

    public static void main(final String... args) throws Exception {
        final var a = var("a");
        final var b = var("b");
        final var c = var("c");
        final var d = var("d");
        final var e = var("e");

        final var formulas = new FormulasEnum(20_000_000, a, b, c, d);

        final var dnfAlgorithm = new DnfAlgorithm();
        final var sparkAlgorithm = new SparkAlgorithm();

        formulas.forEachRemaining(f -> {
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
//                throw new RuntimeException();
//            }
            if (!f1.equals(f2) && !f1.deepEquals(f2)) {
                System.out.println("Origin: " + f);
                System.out.println("Dnf based: " + f1);
                System.out.println("Spark: " + f2);
                System.out.println();
                throw new RuntimeException();
            }
        });

        if (WRITE) {
            final var bw = Files.newBufferedWriter(Paths.get("target/out.txt"));
            final int[] size = {0};
            formulas.forEachRemaining(f -> {
                try {
                    size[0]++;
                    bw.write(f.length() + ", " + f + "\n");
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            });
            bw.flush();
            System.out.println("Total: " + size[0]);
        }
    }
}
