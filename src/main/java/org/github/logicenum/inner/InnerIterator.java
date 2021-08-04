package org.github.logicenum.inner;

import org.github.logicenum.formula.Formula;

import java.util.Iterator;

public abstract class InnerIterator implements Iterator<Formula> {

    private static abstract class InnerBiIterator implements Iterator<Formula> {

        protected final Iterator<Formula> iterator1;
        protected final Iterator<Formula> iterator2;

        protected InnerBiIterator(
                final Iterator<Formula> iterator1,
                final Iterator<Formula> iterator2
        ) {
            this.iterator1 = iterator1;
            this.iterator2 = iterator2;
        }

        @Override
        public boolean hasNext() {
            return this.iterator1.hasNext() && this.iterator2.hasNext();
        }
    }

    public static final class InnerNotIterator extends InnerIterator {

        private final Iterator<Formula> iterator;

        public InnerNotIterator(final Iterator<Formula> iterator) {
            this.iterator = iterator;
        }

        @Override
        public boolean hasNext() {
            return this.iterator.hasNext();
        }
        @Override
        public Formula next() {
            return this.iterator.next().not();
        }

    }

    private static final class InnerOrIterator extends InnerBiIterator {

        InnerOrIterator(
                final Iterator<Formula> iterator1,
                final Iterator<Formula> iterator2
        ) {
            super(iterator1, iterator2);
        }

        @Override
        public Formula next() {
            return this.iterator1.next().or(this.iterator2.next());
        }
    }

    private static final class InnerAndIterator extends InnerBiIterator {

        InnerAndIterator(
                final Iterator<Formula> iterator1,
                final Iterator<Formula> iterator2
        ) {
            super(iterator1, iterator2);
        }

        @Override
        public Formula next() {
            return this.iterator1.next().and(this.iterator2.next());
        }
    }
}
