package org.github.trushev.logicenum.enu.inner;

import org.github.trushev.logicenum.formula.Formula;

import java.util.Iterator;

abstract class BiIterator implements Iterator<Formula> {

    protected final Formula f;
    protected final Iterator<Formula> fs;

    protected BiIterator(final Formula f, final Iterator<Formula> fs) {
        this.f = f;
        this.fs = fs;
    }

    @Override
    public boolean hasNext() {
        return this.fs.hasNext();
    }
}