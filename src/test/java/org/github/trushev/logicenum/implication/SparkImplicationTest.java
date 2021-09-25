package org.github.trushev.logicenum.implication;

import static org.github.trushev.logicenum.formula.Formula.*;
import static org.junit.jupiter.api.Assertions.*;

import org.github.trushev.logicenum.formula.Formula;
import org.junit.jupiter.api.Test;

public class SparkImplicationTest extends ImplicationTestBase {

    private final Formula a = var("a");
    private final Formula b = var("b");
    private final Formula c = var("c");

    SparkImplicationTest() {
        super(new SparkImplication());
    }

    @Test
    public void testNnf1() {
        final var f = not(or(a, b));
        final var expected = and(a.not(), b.not());
        final var actual = ((SparkImplication) implication).nnf(f);
        assertEquals(expected, actual);
    }

    @Test
    public void testNnf2() {
        final var f = not(and(a, b));
        final var expected = or(a.not(), b.not());
        final var actual = ((SparkImplication) implication).nnf(f);
        assertEquals(expected, actual);
    }

    @Test
    public void testNnf3() {
        final var f = not(or(not(and(a, b)), c));
        final var expected = and(a, b, c.not());
        final var actual = ((SparkImplication) implication).nnf(f);
        assertEquals(expected, actual);
    }

    @Test
    public void testNnf4() {
        final var f = and(a, b.not());
        final var expected = and(a, b.not());
        final var actual = ((SparkImplication) implication).nnf(f);
        assertEquals(expected, actual);
    }

    @Test
    public void testNnf5() {
        final var f = or(a, b);
        final var expected = or(a, b);
        final var actual = ((SparkImplication) implication).nnf(f);
        assertEquals(expected, actual);
    }

    @Test
    public void testNnf6() {
        final var f = not(or(not(and(a, b)), c.not()));
        final var expected = and(a, b, c);
        final var actual = ((SparkImplication) implication).nnf(f);
        assertEquals(expected, actual);
    }
}
