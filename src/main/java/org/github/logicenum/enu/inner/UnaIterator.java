package org.github.logicenum.enu.inner;

import org.github.logicenum.formula.Formula;

import java.util.Iterator;

abstract class UnaIterator implements Iterator<Formula> {

    protected final Iterator<Formula> iterator;

    protected UnaIterator(final Iterator<Formula> iterator) {
        this.iterator = iterator;
    }

    @Override
    public boolean hasNext() {
        return this.iterator.hasNext();
    }
}
