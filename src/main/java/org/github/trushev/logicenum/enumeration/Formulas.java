package org.github.trushev.logicenum.enumeration;

import org.github.trushev.logicenum.formula.Formula;

import java.util.*;
import java.util.stream.Stream;

final class Formulas {

    private final Map<Integer, Set<Formula>> formulas;
    private final int formulaLength;

    Formulas(final int formulaLength, final Formula... fs) {
        this.formulas = new HashMap<>();
        this.formulaLength = formulaLength;
        put(fs);
    }

    Formulas(final int formulaLength) {
        this(formulaLength, new Formula[0]);
    }

    Formulas(final Formula... fs) {
        this(-1, fs);
    }

    boolean put(final Formula f) {
        final var length = f.length();
        if (this.formulaLength != -1 && this.formulaLength != length) {
            final var errMessage = "Length of formula should be %s, passed formula length: %s, passed formula %s"
                    .formatted(this.formulaLength, length, f);
            throw new IllegalArgumentException(errMessage);
        }
        this.formulas.computeIfAbsent(length, k -> new HashSet<>());
        return this.formulas.get(length).add(f);
    }

    void put(final Formula... formulas) {
        for (final var formula : formulas) {
            put(formula);
        }
    }

    Stream<Formula> formulasWithLength(final int length) {
        if (this.formulas.get(length) == null) {
            return Stream.empty();
        }
        return this.formulas.get(length).stream();
    }

    void merge(final Formulas formulas) {
        for (final var entry : formulas.formulas.entrySet()) {
            this.formulas.merge(entry.getKey(), entry.getValue(), (formulas1, formulas2) -> {
                formulas1.addAll(formulas2);
                return formulas1;
            });
        }
    }

    @Override
    public String toString() {
        final var sb = new StringBuilder();
        sb.append("Formulas\n");
        this.formulas.entrySet()
                .stream()
                .sorted(Comparator.comparingInt(Map.Entry::getKey))
                .forEach(e -> sb
                        .append(e.getKey())
                        .append(": ")
                        .append(e.getValue())
                        .append("\n"));
        return sb.toString().trim();
    }
}