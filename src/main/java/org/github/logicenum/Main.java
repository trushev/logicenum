package org.github.logicenum;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static java.util.Collections.unmodifiableSet;
import static org.github.logicenum.Formula.*;

public class Main {

    private static Collection<Formula> formulas() throws ExecutionException, InterruptedException {
        final var formulas = new LinkedHashSet<Formula>();
        formulas.add(var("a"));
        formulas.add(var("b"));
        formulas.add(var("c"));
//        formulas.add(var("d"));
//        formulas.add(var("e"));

        for (int i = 0; i < 3; i++) {
            final var negStepFut = CompletableFuture.supplyAsync(() -> negStep(formulas));
            final var andStepFut = CompletableFuture.supplyAsync(() -> andStep(formulas));
            final var orStepFut = CompletableFuture.supplyAsync(() -> orStep(formulas));

            final var negStep = negStepFut.get();
            final var andStep = andStepFut.get();
            final var orStep = orStepFut.get();

            formulas.addAll(negStep);
            formulas.addAll(andStep);
            formulas.addAll(orStep);
        }

        return formulas;
    }

    private static Set<Formula> negStep(final Set<Formula> formulas) {
        final var res = new LinkedHashSet<Formula>(formulas.size());
        for (final var f : formulas) {
            res.add(neg(f));
        }
        return unmodifiableSet(res);
    }

    private static Set<Formula> andStep(final Set<Formula> formulas) {
        return biStep(formulas, false);
    }

    private static Set<Formula> orStep(final Set<Formula> formulas) {
        return biStep(formulas, true);
    }

    private static Set<Formula> biStep(final Set<Formula> formulas, final boolean isOr) {
        final var res = new LinkedHashSet<Formula>(formulas.size());
        for (final var f1 : formulas) {
            for (final var f2 : formulas) {
                if (isOr) {
                    res.add(or(f1, f2));
                } else {
                    res.add(and(f1, f2));
                }
            }
        }
        return unmodifiableSet(res);
    }

    public static void main(final String... args) throws Exception {
        final var bw = Files.newBufferedWriter(Paths.get("target/out.txt"));
        final var formulas = formulas();
        formulas.forEach(f -> {
            try {
                bw.write(f.toString() + "\n");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        bw.flush();
        System.out.println("Total: " + formulas.size());
    }
}
