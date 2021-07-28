package org.github.logicenum.extract;

import org.github.logicenum.formula.Const;
import org.github.logicenum.formula.Formula;
import org.junit.jupiter.api.Test;

import static org.github.logicenum.formula.Formula.*;
import static org.junit.jupiter.api.Assertions.*;

class SparkAlgorithmTest {

    private final SparkAlgorithm algorithm = new SparkAlgorithm();
    private final Formula a = var("a");
    private final Formula b = var("b");
    private final Formula c = var("c");

    private final Formula a1 = var("a1");
    private final Formula a2 = var("a2");
    private final Formula b1 = var("b1");


    @Test
    public void test0() {
        final var actual = algorithm.ex(a1, a1);
        assertEquals(a1, actual);
    }

    @Test
    public void test1() {
        final var actual = algorithm.ex(b1, a1);
        assertEquals(Const.True, actual);
    }

    @Test
    public void test2() {
        final var f = or(and(a1, b1), a2);
        final var expected = or(a1, a2);
        final var actual = algorithm.ex(f, a1, a2);
        assertEquals(expected, actual);
    }

    @Test
    public void test3() {
        final var f = or(and(a1, a2), b1);
        final var expected = Const.True;
        final var actual = algorithm.ex(f, a1, a2);
        assertEquals(expected, actual);
    }

    @Test
    public void test4() {
        final var f = not(or(a1, b1));
        final var expected = not(a1);
        final var actual = algorithm.ex(f, a1, a2, a1.not(), a2.not());
        assertEquals(expected, actual);
    }

    @Test
    public void test5() {
        final var f = not(and(a1, b1));
        final var expected = Const.True;
        final var actual = algorithm.ex(f, a1, a2, a1.not(), a2.not());
        assertEquals(expected, actual);
    }

    @Test
    public void test6() {
        final var f = not(and(or(a1, a2), b1));
        final var expected = Const.True;
        final var actual = algorithm.ex(f, a1, a2, a1.not(), a2.not());
        assertEquals(expected, actual);
    }

    @Test
    public void test7() {
        final var f = or(and(a1, b1), not(a2));
        final var expected = or(a1, not(a2));
        final var actual = algorithm.ex(f, a1, a2, a1.not(), a2.not());
        assertEquals(expected, actual);
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////

    @Test
    public void testNnf1() {
        final var f = not(or(a, b));
        final var expected = and(a.not(), b.not());
        final var actual = algorithm.nnf(f);
        assertEquals(expected, actual);
    }

    @Test
    public void testNnf2() {
        final var f = not(and(a, b));
        final var expected = or(a.not(), b.not());
        final var actual = algorithm.nnf(f);
        assertEquals(expected, actual);
    }

    @Test
    public void testNnf3() {
        final var f = not(or(not(and(a, b)), c));
        final var expected = and(a, b, c.not());
        final var actual = algorithm.nnf(f);
        assertEquals(expected, actual);
    }

    @Test
    public void testNnf4() {
        final var f = and(a, b.not());
        final var expected = and(a, b.not());
        final var actual = algorithm.nnf(f);
        assertEquals(expected, actual);
    }

    @Test
    public void testNnf5() {
        final var f = or(a, b);
        final var expected = or(a, b);
        final var actual = algorithm.nnf(f);
        assertEquals(expected, actual);
    }

    @Test
    public void testNnf6() {
        final var f = not(or(not(and(a, b)), c.not()));
        final var expected = and(a, b, c);
        final var actual = algorithm.nnf(f);
        assertEquals(expected, actual);
    }
}