package org.github.logicenum.next.mid;

import org.github.logicenum.formula.Formula;
import org.github.logicenum.next.Formulas;
import org.github.logicenum.next.inner.AndIterator;
import org.github.logicenum.next.inner.NotIterator;
import org.github.logicenum.next.inner.OrIterator;

import java.util.Collections;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.function.BiFunction;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.Spliterator.NONNULL;
import static java.util.Spliterators.spliteratorUnknownSize;
import static java.util.stream.StreamSupport.stream;

public final class FixedLengthFormulas implements Iterator<Formula> {

    private final Iterator<Iterator<Formula>> iterators;
    private Iterator<Formula> currentIterator;

    public FixedLengthFormulas(final Formulas formulas, final int length) {
        final var s1 = Stream.of(new NotIterator(formulas.getWithLength(length - 1)));
        final var s2 = streamOfIterators(formulas, length, AndIterator::new);
        final var s3 = streamOfIterators(formulas, length, OrIterator::new);
        this.iterators = Stream.concat(s1, Stream.concat(s2, s3)).iterator();
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
        return IntStream.range(1, length - 1)
                .boxed()
                .flatMap(i ->
                        stream(
                                spliteratorUnknownSize(formulas.getWithLength(i), NONNULL),
                                false
                        ).map(f -> fun.apply(f, formulas.getWithLength(length - i - 1)))
                );
    }
}
