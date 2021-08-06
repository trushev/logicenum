package org.github.logicenum.next.inner;

import org.github.logicenum.formula.Formula;

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
