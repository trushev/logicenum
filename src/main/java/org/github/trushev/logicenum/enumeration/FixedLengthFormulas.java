package org.github.trushev.logicenum.enumeration;

import java.util.Collections;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.function.BiFunction;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import org.github.trushev.logicenum.formula.Formula;

final class FixedLengthFormulas implements Iterator<Formula> {

    private final Iterator<Iterator<Formula>> iterators;
    private Iterator<Formula> currentIterator;

    FixedLengthFormulas(final Formulas formulas, final int length) {
        final var s1 = Stream.of(new NotIterator(formulas.formulasWithLength(length - 1)));
        final var s2 = Stream.of(new IsNullIterator(formulas.formulasWithLength(length - 1)));
        final var s3 = streamOfIterators(formulas, length, AndIterator::new);
        final var s4 = streamOfIterators(formulas, length, OrIterator::new);
        this.iterators = Stream.concat(s1, Stream.concat(s2, Stream.concat(s3, s4))).iterator();
        if (this.iterators.hasNext()) {
            this.currentIterator = this.iterators.next();
        } else {
            this.currentIterator = Collections.emptyIterator();
        }
    }

    @Override
    public boolean hasNext() {
        if (!this.currentIterator.hasNext() && !this.iterators.hasNext()) {
            return false;
        }
        if (!this.currentIterator.hasNext()) {
            this.currentIterator = this.iterators.next();
            return hasNext();
        }
        return true;
    }

    @Override
    public Formula next() {
        if (this.currentIterator.hasNext()) {
            return this.currentIterator.next();
        }
        while (!this.currentIterator.hasNext() && this.iterators.hasNext()) {
            this.currentIterator = this.iterators.next();
        }
        if (this.currentIterator.hasNext()) {
            return this.currentIterator.next();
        }
        throw new NoSuchElementException();
    }

    private static Stream<Iterator<Formula>> streamOfIterators(
        final Formulas formulas,
        final int length,
        final BiFunction<Formula, Iterator<Formula>, Iterator<Formula>> fun
    ) {
        return IntStream
            .range(1, length - 1)
            .boxed()
            .flatMap(
                i ->
                    formulas
                        .formulasWithLength(i)
                        .map(f -> fun.apply(f, formulas.formulasWithLength(length - i - 1).iterator()))
            );
    }

    @Override
    public String toString() {
        return (
            "FixedLengthFormulas{" + "iterators=" + this.iterators + ", currentIterator=" + this.currentIterator + '}'
        );
    }
}
