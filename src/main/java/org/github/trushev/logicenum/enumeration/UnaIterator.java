package org.github.trushev.logicenum.enumeration;

import java.util.Iterator;
import java.util.stream.Stream;
import org.github.trushev.logicenum.formula.Formula;

abstract class UnaIterator implements Iterator<Formula> {

    protected final Iterator<Formula> iterator;

    protected UnaIterator(final Iterator<Formula> iterator) {
        this.iterator = iterator;
    }

    protected UnaIterator(final Stream<Formula> stream) {
        this(stream.iterator());
    }

    @Override
    public boolean hasNext() {
        return this.iterator.hasNext();
    }

    @Override
    public String toString() {
        return "UnaIterator{" + "iterator=" + this.iterator + '}';
    }
}
