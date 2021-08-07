package org.github.logicenum.enu.inner;

import org.github.logicenum.formula.Formula;

import java.util.Iterator;

public final class NotIterator extends UnaIterator {

    public NotIterator(final Iterator<Formula> iterator) {
        super(iterator);
    }

    @Override
    public Formula next() {
        return this.iterator.next().not();
    }
}
