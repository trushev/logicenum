package org.github.logicenum;

import org.github.logicenum.enu.Formulas;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.github.logicenum.formula.Formula.var;

public class Main {
    public static void main(final String... args) throws Exception {
        final var a = var("a");
        final var b = var("b");
        final var c = var("c");
        final var d = var("d");
        final var e = var("e");

        final var formulas = Formulas.get().enumeration(a, b, c);

        final var bw = Files.newBufferedWriter(Paths.get("target/out.txt"));
        final int[] size = {0};
        formulas.forEach(f -> {
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
