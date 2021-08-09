package org.github.trushev.logicenum.enumeration;

import org.github.trushev.logicenum.formula.Formula;

import java.util.Iterator;
import java.util.NoSuchElementException;

final class LimitedFormulasEnum implements Iterator<Formula> {

    private final Iterator<Formula> iterator;
    private final long limit;
    private int counter;

    LimitedFormulasEnum(final Iterator<Formula> iterator, final long limit) {
        this.iterator = iterator;
        this.limit = limit;
        this.counter = 0;
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

    @Override
    public String toString() {
        return "LimitedFormulasEnum{" +
                "iterator=" + this.iterator +
                ", limit=" + this.limit +
                ", counter=" + this.counter +
                '}';
    }
}
