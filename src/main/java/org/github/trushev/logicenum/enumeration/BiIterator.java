package org.github.trushev.logicenum.enumeration;

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

    @Override
    public String toString() {
        return "BiIterator{" +
                "f=" + this.f +
                ", fs=" + this.fs +
                '}';
    }
}
