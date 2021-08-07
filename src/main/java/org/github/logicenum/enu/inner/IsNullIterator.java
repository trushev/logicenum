package org.github.logicenum.enu.inner;

import org.github.logicenum.formula.Formula;

import java.util.Iterator;

public final class IsNullIterator extends UnaIterator {

    public IsNullIterator(final Iterator<Formula> iterator) {
        super(iterator);
    }

    @Override
    public Formula next() {
        return this.iterator.next().isNull();
    }
}
