package org.github.trushev.logicenum.enumeration;

import java.util.Iterator;
import java.util.NoSuchElementException;
import org.github.trushev.logicenum.formula.Formula;

final class LimitedFormulasEnum implements Iterator<Formula> {

    private final Iterator<Formula> iterator;
    private final long limit;
    private int counter;

    LimitedFormulasEnum(Iterator<Formula> iterator, long limit) {
        this.iterator = iterator;
        this.limit = limit;
        counter = 0;
    }

    @Override
    public boolean hasNext() {
        return counter < limit;
    }

    @Override
    public Formula next() {
        if (counter < limit) {
            counter++;
            return iterator.next();
        }
        throw new NoSuchElementException();
    }

    @Override
    public String toString() {
        return (
            "LimitedFormulasEnum{" +
            "iterator=" +
            iterator +
            ", limit=" +
            limit +
            ", counter=" +
            counter +
            '}'
        );
    }
}
