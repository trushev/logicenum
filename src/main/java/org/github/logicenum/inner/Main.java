package org.github.logicenum.inner;

import org.github.logicenum.formula.Formula;
import org.github.logicenum.inner.InnerIterator.InnerNotIterator;

import java.util.*;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import static org.github.logicenum.formula.Formula.var;

public final class Main {
    public static void main(final String... args) {

        final var range = IntStream.range(0, 1_000_000_000).map(i -> i+1);
        final var iterator = range.iterator();
        final var spliterator = Spliterators.spliteratorUnknownSize(iterator, Spliterator.NONNULL);
        final var stream = StreamSupport.stream(spliterator, false).filter(i -> i % 2 == 0);


    }
}
