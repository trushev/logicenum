package org.github.logicenum.enu;

import org.github.logicenum.formula.Formula;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;

import static java.util.Collections.unmodifiableSet;
import static org.github.logicenum.formula.Formula.*;

final class FormulasImpl implements Formulas {

    private static final int EPOCH = 4;
    private static final boolean PARALLEL = true;
    private static final boolean SKIP = true;


    private int negLength = 0;
    private int biLength = 0;

    @Override
    public Stream<Formula> enumeration(final Formula... vars) {
        final var formulas = new LinkedHashSet<>(Arrays.asList(vars));
        for (int i = 0; i < EPOCH; i++) {
            if (PARALLEL) {
                final var negStepFut = CompletableFuture.supplyAsync(() -> negStep(formulas));
                final var andStepFut = CompletableFuture.supplyAsync(() -> andStep(formulas));
                final var orStepFut = CompletableFuture.supplyAsync(() -> orStep(formulas));

                try {
                    final var negStep = negStepFut.get();
                    final var andStep = andStepFut.get();
                    final var orStep = orStepFut.get();

                    formulas.addAll(negStep);
                    formulas.addAll(andStep);
                    formulas.addAll(orStep);

                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            } else {

                final var negStep = negStep(formulas);
                final var andStep = andStep(formulas);
                final var orStep = orStep(formulas);

                this.negLength = length(negStep);
                this.biLength = length(andStep);
                length(orStep);

                formulas.addAll(negStep);
                formulas.addAll(andStep);
                formulas.addAll(orStep);
            }
        }
        return formulas.stream();
    }

    private int length(final Set<Formula> formulas) {
        return formulas.stream().mapToInt(Formula::length).min().orElseThrow();
    }

    private Set<Formula> negStep(final Set<Formula> formulas) {
        final var res = new LinkedHashSet<Formula>(formulas.size());
        for (final var f : formulas) {
            if (SKIP && f.length() < this.negLength) {
                continue;
            }
            res.add(neg(f));
        }
        return unmodifiableSet(res);
    }

    private Set<Formula> andStep(final Set<Formula> formulas) {
        return biStep(formulas, false);
    }

    private Set<Formula> orStep(final Set<Formula> formulas) {
        return biStep(formulas, true);
    }

    private Set<Formula> biStep(final Set<Formula> formulas, final boolean isOr) {
        final var res = new LinkedHashSet<Formula>(formulas.size());
        for (final var f1 : formulas) {
            for (final var f2 : formulas) {
                Formula f;
                if (isOr) {
                    f = or(f1, f2);
                } else {
                    f = and(f1, f2);
                }
                if (SKIP && f.length() < this.biLength) {
                    continue;
                }
                res.add(f);
            }
        }
        return unmodifiableSet(res);
    }
}
