package org.github.logicenum.next;

import org.github.logicenum.formula.Formula;

import java.util.*;

public class Formulas {

    private final Map<Integer, Set<Formula>> formulas;

    Formulas() {
        this.formulas = new HashMap<>();
    }

    public void put(final Formula formula) {
        this.formulas.computeIfAbsent(formula.length(), k -> new HashSet<>());
        this.formulas.get(formula.length()).add(formula);
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
