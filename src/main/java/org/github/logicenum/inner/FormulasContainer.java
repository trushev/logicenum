package org.github.logicenum.inner;

import org.github.logicenum.formula.Formula;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public final class FormulasContainer {

    private final Map<Integer, Set<Formula>> formulas;

    public FormulasContainer(final Map<Integer, Set<Formula>> formulas) {
        this.formulas = formulas;
    }

    public Iterator<Formula> byLength(final int length) {
        final var fs = this.formulas.get(length);
        if (fs == null) {
            throw new IllegalArgumentException(String.valueOf(length));
        }
        return fs.iterator();
    }
}
