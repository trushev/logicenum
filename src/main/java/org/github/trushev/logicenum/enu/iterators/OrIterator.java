package org.github.trushev.logicenum.enu.iterators;

import org.github.trushev.logicenum.formula.Formula;

import java.util.Iterator;

public final class OrIterator extends BiIterator {

    public OrIterator(final Formula f, final Iterator<Formula> fs) {
        super(f, fs);
    }

    @Override
    public Formula next() {
        return this.f.or(this.fs.next());
    }
}
