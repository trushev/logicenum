package org.github.trushev.logicenum.enumeration;

import java.util.*;
import java.util.stream.Stream;
import org.github.trushev.logicenum.formula.Formula;

final class Formulas {

    private final Map<Integer, Set<Formula>> formulas;
    private final int formulaLength;

    Formulas(int formulaLength, Formula... fs) {
        this.formulas = new HashMap<>();
        this.formulaLength = formulaLength;
        put(fs);
    }

    Formulas(int formulaLength) {
        this(formulaLength, new Formula[0]);
    }

    Formulas(Formula... fs) {
        this(-1, fs);
    }

    boolean put(Formula f) {
        var length = f.length();
        if (this.formulaLength != -1 && this.formulaLength != length) {
            var errMessage =
                "Length of formula should be %s, passed formula length: %s, passed formula %s".formatted(
                        this.formulaLength,
                        length,
                        f
                    );
            throw new IllegalArgumentException(errMessage);
        }
        this.formulas.computeIfAbsent(length, k -> new HashSet<>());
        return this.formulas.get(length).add(f);
    }

    void put(Formula... formulas) {
        for (var formula : formulas) {
            put(formula);
        }
    }

    Stream<Formula> formulasWithLength(int length) {
        if (this.formulas.get(length) == null) {
            return Stream.empty();
        }
        return this.formulas.get(length).stream();
    }

    void merge(Formulas formulas) {
        for (var entry : formulas.formulas.entrySet()) {
            this.formulas.merge(
                    entry.getKey(),
                    entry.getValue(),
                    (formulas1, formulas2) -> {
                        formulas1.addAll(formulas2);
                        return formulas1;
                    }
                );
        }
    }

    int size() {
        return this.formulas.values().stream().mapToInt(Set::size).sum();
    }

    @Override
    public String toString() {
        var sb = new StringBuilder();
        sb.append("Formulas\n");
        this.formulas.entrySet()
            .stream()
            .sorted(Comparator.comparingInt(Map.Entry::getKey))
            .forEach(e -> sb.append(e.getKey()).append(": ").append(e.getValue()).append("\n"));
        return sb.toString().trim();
    }
}
