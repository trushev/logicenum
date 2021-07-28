package org.github.logicenum.extract;

import org.github.logicenum.formula.Const;
import org.github.logicenum.formula.Formula;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.github.logicenum.formula.Formula.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

class AlgorithmTest {

    private final Formula a1 = var("a1");
    private final Formula a2 = var("a2");
    private final Formula b1 = var("b1");

    @SuppressWarnings("unused")
    private static Stream<Algorithm> algorithms() {
        return Stream.of(new DnfAlgorithm(), new SparkAlgorithm());
    }

    @ParameterizedTest
    @MethodSource("algorithms")
    public void test0(final Algorithm algorithm) {
        final var actual = algorithm.ex(a1, a1);
        assertEquals(a1, actual);
    }

    @ParameterizedTest
    @MethodSource("algorithms")
    public void test1(final Algorithm algorithm) {
        final var actual = algorithm.ex(b1, a1);
        assertEquals(Const.True, actual);
    }

    @ParameterizedTest
    @MethodSource("algorithms")
    public void test2(final Algorithm algorithm) {
        final var f = or(and(a1, b1), a2);
        final var expected = or(a1, a2);
        final var actual = algorithm.ex(f, a1, a2);
        assertEquals(expected, actual);
    }

    @ParameterizedTest
    @MethodSource("algorithms")
    public void test3(final Algorithm algorithm) {
        final var f = or(and(a1, a2), b1);
        final var expected = Const.True;
        final var actual = algorithm.ex(f, a1, a2);
        assertEquals(expected, actual);
    }

    @ParameterizedTest
    @MethodSource("algorithms")
    public void test4(final Algorithm algorithm) {
        final var f = not(or(a1, b1));
        final var expected = not(a1);
        final var actual = algorithm.ex(f, a1, a2, a1.not(), a2.not());
        assertEquals(expected, actual);
    }

    @ParameterizedTest
    @MethodSource("algorithms")
    public void test5(final Algorithm algorithm) {
        final var f = not(and(a1, b1));
        final var expected = Const.True;
        final var actual = algorithm.ex(f, a1, a2, a1.not(), a2.not());
        assertEquals(expected, actual);
    }

    @ParameterizedTest
    @MethodSource("algorithms")
    public void test6(final Algorithm algorithm) {
        final var f = not(and(or(a1, a2), b1));
        final var expected = Const.True;
        final var actual = algorithm.ex(f, a1, a2, a1.not(), a2.not());
        assertEquals(expected, actual);
    }

    @ParameterizedTest
    @MethodSource("algorithms")
    public void test7(final Algorithm algorithm) {
        final var f = or(and(a1, b1), not(a2));
        final var expected = or(a1, not(a2));
        final var actual = algorithm.ex(f, a1, a2, a1.not(), a2.not());
        assertEquals(expected, actual);
    }
}
