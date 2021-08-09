package org.github.trushev.logicenum.enumeration;

import org.github.trushev.logicenum.formula.Formula;

import java.util.Iterator;

final class OrIterator extends BiIterator {

    OrIterator(final Formula f, final Iterator<Formula> fs) {
        super(f, fs);
    }

    @Override
    public Formula next() {
        return this.f.or(this.fs.next());
    }
}
