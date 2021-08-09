package org.github.trushev.logicenum.enumeration;

import org.github.trushev.logicenum.formula.Formula;

import java.util.*;

public final class Formulas {

    private final Map<Integer, Set<Formula>> formulas;

    public Formulas() {
        this.formulas = new HashMap<>();
    }

    public boolean put(final Formula f) {
        this.formulas.computeIfAbsent(f.length(), k -> new HashSet<>());
        return this.formulas.get(f.length()).add(f);
    }

    public void put(final Formula... formulas) {
        for (final var formula : formulas) {
            put(formula);
        }
    }

    public Iterator<Formula> getWithLength(final int length) {
        if (this.formulas.get(length) == null) {
            return Collections.emptyIterator();
        }
        return this.formulas.get(length).iterator();
    }

    public void merge(final Formulas formulas) {
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
