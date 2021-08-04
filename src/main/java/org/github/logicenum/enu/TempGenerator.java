package org.github.logicenum.enu;

import org.github.logicenum.formula.Formula;

import java.util.*;

import static java.util.Collections.*;
import static org.github.logicenum.formula.Formula.var;

public final class TempGenerator {

    private static abstract class InnerIterator implements Iterator<Formula> {}

    private static final class InnerNotIterator extends InnerIterator {

        private final Iterator<Formula> iterator;

        private InnerNotIterator(final Iterator<Formula> iterator) {
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

    private static abstract class InnerBiIterator implements Iterator<Formula> {

        protected final Iterator<Formula> iterator1;
        protected final Iterator<Formula> iterator2;

        InnerBiIterator(
                final Iterator<Formula> iterator1, final Iterator<Formula> iterator2) {
            this.iterator1 = iterator1;
            this.iterator2 = iterator2;
        }

        @Override
        public boolean hasNext() {
            return this.iterator1.hasNext() && this.iterator2.hasNext();
        }
    }

    private static final class InnerOrIterator extends InnerBiIterator {

        InnerOrIterator(final Iterator<Formula> iterator1, final Iterator<Formula> iterator2) {
            super(iterator1, iterator2);
        }

        @Override
        public Formula next() {
            return this.iterator1.next().or(this.iterator2.next());
        }
    }

    private static final class InnerAndIterator extends InnerBiIterator {

        InnerAndIterator(final Iterator<Formula> iterator1, final Iterator<Formula> iterator2) {
            super(iterator1, iterator2);
        }

        @Override
        public Formula next() {
            return this.iterator1.next().and(this.iterator2.next());
        }
    }

    // TODO: тоже самое на ленивых интераторах
    public static Set<Formula>
    generateAnd(final Map<Integer, Set<Formula>> formulas, final int n) {
        final var result = new HashSet<Formula>();
        for (int i = 1; i < n-1; i++) {
            final var j = n - i - 1;
            final var fs1 = formulas.get(i);
            if (fs1 == null) continue;
            for (final var f1 : fs1) {
                final var fs2 = formulas.get(j);
                if (fs2 == null) continue;
                for (final var f2 : fs2) {
                    final var f = f1.and(f2);
                    System.out.println(f.length());
                    result.add(f);
                }
            }
        }
        return unmodifiableSet(result);
    }

    public static void main(final String... args) {
        final var a = var("a");
        final var b = var("b");
        final var c = var("c");

        final var formulas = new HashMap<Integer, Set<Formula>>();
        formulas.put(1, Set.of(a, b, c));


        var res = generateAnd(formulas, 3);
        res.forEach(f -> System.out.println("f: " + f));
        formulas.put(3, res);

        res = generateAnd(formulas, 4);
        res.forEach(f -> System.out.println("f: " + f));
        formulas.put(4, res);
    }
}
