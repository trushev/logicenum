package org.github.trushev.logicenum.enumeration.iterators;

import org.github.trushev.logicenum.formula.Formula;

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
