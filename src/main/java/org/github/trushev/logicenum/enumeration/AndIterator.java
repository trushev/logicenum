package org.github.trushev.logicenum.enumeration;

import java.util.Iterator;
import org.github.trushev.logicenum.formula.Formula;

final class AndIterator extends BiIterator {

    AndIterator(Formula f, Iterator<Formula> fs) {
        super(f, fs);
    }

    @Override
    public Formula next() {
        return f.and(fs.next());
    }
}
