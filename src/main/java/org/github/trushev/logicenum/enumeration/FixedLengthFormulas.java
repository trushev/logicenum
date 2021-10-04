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

    FixedLengthFormulas(Formulas formulas, int length) {
        var s1 = Stream.of(new NotIterator(formulas.formulasWithLength(length - 1)));
        var s2 = Stream.of(new IsNullIterator(formulas.formulasWithLength(length - 1)));
        var s3 = streamOfIterators(formulas, length, AndIterator::new);
        var s4 = streamOfIterators(formulas, length, OrIterator::new);
        iterators = Stream.concat(s1, Stream.concat(s2, Stream.concat(s3, s4))).iterator();
        if (iterators.hasNext()) {
            currentIterator = iterators.next();
        } else {
            currentIterator = Collections.emptyIterator();
        }
    }

    @Override
    public boolean hasNext() {
        if (!currentIterator.hasNext() && !iterators.hasNext()) {
            return false;
        }
        if (!currentIterator.hasNext()) {
            currentIterator = iterators.next();
            return hasNext();
        }
        return true;
    }

    @Override
    public Formula next() {
        if (currentIterator.hasNext()) {
            return currentIterator.next();
        }
        while (!currentIterator.hasNext() && iterators.hasNext()) {
            currentIterator = iterators.next();
        }
        if (currentIterator.hasNext()) {
            return currentIterator.next();
        }
        throw new NoSuchElementException();
    }

    private static Stream<Iterator<Formula>> streamOfIterators(
        Formulas formulas,
        int length,
        BiFunction<Formula, Iterator<Formula>, Iterator<Formula>> fun
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
            "FixedLengthFormulas{" + "iterators=" + iterators + ", currentIterator=" + currentIterator + '}'
        );
    }
}
