package org.github.logicenum.inner;

import java.util.Arrays;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.IntStream;
import java.util.stream.StreamSupport;

public final class Main {
    public static void main(final String... args) {
        final var range = IntStream.range(0, 1_000_000_000).map(i -> i + 1);
        final var iterator = range.iterator();
        System.out.println(iterator.next());
        final var spliterator = Spliterators.spliteratorUnknownSize(iterator, Spliterator.NONNULL);
        final var stream = StreamSupport.stream(spliterator, false).filter(i -> i % 3 == 0);
        final var iterator1 = stream.iterator();
        System.out.println(iterator1.next());

        final var s = IntStream.range(0, 10);
        final var s2 = s.sequential();
        System.out.println(Arrays.toString(s.toArray()));
        System.out.println(Arrays.toString(s2.toArray()));
    }
}
