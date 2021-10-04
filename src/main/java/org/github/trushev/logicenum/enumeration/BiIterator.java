package org.github.trushev.logicenum.enumeration;

import java.util.Iterator;
import org.github.trushev.logicenum.formula.Formula;

abstract class BiIterator implements Iterator<Formula> {

    protected final Formula f;
    protected final Iterator<Formula> fs;

    protected BiIterator(Formula f, Iterator<Formula> fs) {
        this.f = f;
        this.fs = fs;
    }

    @Override
    public boolean hasNext() {
        return fs.hasNext();
    }

    @Override
    public String toString() {
        return "BiIterator{" + "f=" + f + ", fs=" + fs + '}';
    }
}
