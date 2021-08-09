package org.github.trushev.logicenum.enumeration.iterators;

import org.github.trushev.logicenum.formula.Formula;

import java.util.Iterator;
import java.util.NoSuchElementException;

public final class LimitedFormulasEnum implements Iterator<Formula> {

    private final int limit;
    private final Iterator<Formula> iterator;
    private int counter;

    public LimitedFormulasEnum(final int limit, final Formula... fs) {
        this.limit = limit;
        this.iterator = new FormulasEnumInner(fs);
        this.counter = 0;
    }

    public LimitedFormulasEnum(final Formula... fs) {
        this(Integer.MAX_VALUE, fs);
    }

    @Override
    public boolean hasNext() {
        return this.counter < this.limit;
    }

    @Override
    public Formula next() {
        if (this.counter < this.limit) {
            this.counter++;
            return this.iterator.next();
        }
        throw new NoSuchElementException();
    }
}
