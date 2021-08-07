package org.github.logicenum.next.outer;

import org.github.logicenum.formula.Formula;

import java.util.Iterator;
import java.util.NoSuchElementException;

public final class FormulasEnum implements Iterator<Formula> {

    private final int count;
    private final Iterator<Formula> iterator;
    private int counter;

    public FormulasEnum(final int count, final Formula... fs) {
        this.count = count;
        this.iterator = new FormulasEnumInner(fs);
        this.counter = 0;
    }

    public FormulasEnum(final Formula... fs) {
        this(Integer.MAX_VALUE, fs);
    }

    @Override
    public boolean hasNext() {
        return this.counter < this.count;
    }

    @Override
    public Formula next() {
        if (this.counter < this.count) {
            this.counter++;
            return this.iterator.next();
        }
        throw new NoSuchElementException();
    }
}
