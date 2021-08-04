package org.github.logicenum.inner;

import org.github.logicenum.formula.Formula;

import java.util.Iterator;

// TODO: сделать декоратор для считающего итератора
public final class OuterIterator implements Iterator<Formula> {

    private final int lengthLimit;
    private int currLength;

    public OuterIterator(final int lengthLimit) {
        this.lengthLimit = lengthLimit;
        this.currLength = 1; // TODO: 1?
    }

    @Override
    public boolean hasNext() {
        return this.currLength < this.lengthLimit; // TODO: строгое?
    }

    @Override
    public Formula next() {
        this.currLength++;
        return null;
    }

    public static void main(final String... args) {
        final var lengthLimit = 100;
        final var iterator = new OuterIterator(lengthLimit);
        while (iterator.hasNext()) {
            final var f = iterator.next();
            System.out.println("f: " + f);
        }
    }
}
