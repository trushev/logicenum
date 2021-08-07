package org.github.logicenum.enu.inner;

import org.github.logicenum.formula.Formula;

import java.util.Iterator;

public final class NotIterator implements Iterator<Formula> {

    private final Iterator<Formula> iterator;

    public NotIterator(final Iterator<Formula> iterator) {
        this.iterator = iterator;
    }

    @Override
    public boolean hasNext() {
        return this.iterator.hasNext();
    }

    @Override
    public Formula next() {
        return this.iterator.next().not();
    }
}
