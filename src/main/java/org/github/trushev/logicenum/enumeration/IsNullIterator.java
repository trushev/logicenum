package org.github.trushev.logicenum.enumeration;

import org.github.trushev.logicenum.formula.Formula;

import java.util.stream.Stream;

final class IsNullIterator extends UnaIterator {

    IsNullIterator(final Stream<Formula> stream) {
        super(stream);
    }

    @Override
    public Formula next() {
        return this.iterator.next().isNull();
    }
}
