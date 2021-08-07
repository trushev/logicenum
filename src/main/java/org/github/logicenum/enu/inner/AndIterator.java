package org.github.logicenum.enu.inner;

import org.github.logicenum.formula.Formula;

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
