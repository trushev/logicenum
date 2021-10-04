package org.github.trushev.logicenum.implication;

import static org.github.trushev.logicenum.formula.Formula.*;
import static org.junit.jupiter.api.Assertions.*;

import org.github.trushev.logicenum.formula.Formula;
import org.junit.jupiter.api.Test;

public final class SparkImplicationTest extends ImplicationTestBase {

    private final Formula a = var("a");
    private final Formula b = var("b");
    private final Formula c = var("c");

    SparkImplicationTest() {
        super(new SparkImplication());
    }

    @Test
    public void testNnf1() {
        var f = not(or(a, b));
        var expected = and(a.not(), b.not());
        var actual = ((SparkImplication) implication).nnf(f);
        assertEquals(expected, actual);
    }

    @Test
    public void testNnf2() {
        var f = not(and(a, b));
        var expected = or(a.not(), b.not());
        var actual = ((SparkImplication) implication).nnf(f);
        assertEquals(expected, actual);
    }

    @Test
    public void testNnf3() {
        var f = not(or(not(and(a, b)), c));
        var expected = and(a, b, c.not());
        var actual = ((SparkImplication) implication).nnf(f);
        assertEquals(expected, actual);
    }

    @Test
    public void testNnf4() {
        var f = and(a, b.not());
        var expected = and(a, b.not());
        var actual = ((SparkImplication) implication).nnf(f);
        assertEquals(expected, actual);
    }

    @Test
    public void testNnf5() {
        var f = or(a, b);
        var expected = or(a, b);
        var actual = ((SparkImplication) implication).nnf(f);
        assertEquals(expected, actual);
    }

    @Test
    public void testNnf6() {
        var f = not(or(not(and(a, b)), c.not()));
        var expected = and(a, b, c);
        var actual = ((SparkImplication) implication).nnf(f);
        assertEquals(expected, actual);
    }
}
