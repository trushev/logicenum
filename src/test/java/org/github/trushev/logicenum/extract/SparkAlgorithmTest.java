package org.github.trushev.logicenum.extract;

import org.github.trushev.logicenum.formula.Formula;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SparkAlgorithmTest extends AlgorithmTestBase {

    private final Formula a = Formula.var("a");
    private final Formula b = Formula.var("b");
    private final Formula c = Formula.var("c");

    SparkAlgorithmTest() {
        super(new SparkAlgorithm());
    }

    @Test
    public void testNnf1() {
        final var f = Formula.not(Formula.or(a, b));
        final var expected = Formula.and(a.not(), b.not());
        final var actual = ((SparkAlgorithm)algorithm).nnf(f);
        assertEquals(expected, actual);
    }

    @Test
    public void testNnf2() {
        final var f = Formula.not(Formula.and(a, b));
        final var expected = Formula.or(a.not(), b.not());
        final var actual = ((SparkAlgorithm)algorithm).nnf(f);
        assertEquals(expected, actual);
    }

    @Test
    public void testNnf3() {
        final var f = Formula.not(Formula.or(Formula.not(Formula.and(a, b)), c));
        final var expected = Formula.and(a, b, c.not());
        final var actual = ((SparkAlgorithm)algorithm).nnf(f);
        assertEquals(expected, actual);
    }

    @Test
    public void testNnf4() {
        final var f = Formula.and(a, b.not());
        final var expected = Formula.and(a, b.not());
        final var actual = ((SparkAlgorithm)algorithm).nnf(f);
        assertEquals(expected, actual);
    }

    @Test
    public void testNnf5() {
        final var f = Formula.or(a, b);
        final var expected = Formula.or(a, b);
        final var actual = ((SparkAlgorithm)algorithm).nnf(f);
        assertEquals(expected, actual);
    }

    @Test
    public void testNnf6() {
        final var f = Formula.not(Formula.or(Formula.not(Formula.and(a, b)), c.not()));
        final var expected = Formula.and(a, b, c);
        final var actual = ((SparkAlgorithm)algorithm).nnf(f);
        assertEquals(expected, actual);
    }
}