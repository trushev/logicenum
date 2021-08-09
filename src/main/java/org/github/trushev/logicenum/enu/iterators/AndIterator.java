package org.github.trushev.logicenum.enu.iterators;

import org.github.trushev.logicenum.formula.Formula;

import java.util.Iterator;

public final class AndIterator extends BiIterator {

    public AndIterator(final Formula f, final Iterator<Formula> fs) {
        super(f, fs);
    }

    @Override
    public Formula next() {
        return this.f.and(this.fs.next());
    }
}
