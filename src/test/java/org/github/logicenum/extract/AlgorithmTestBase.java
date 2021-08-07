package org.github.logicenum.extract;

import org.github.logicenum.formula.Const;
import org.github.logicenum.formula.Formula;
import org.junit.jupiter.api.Test;

import static org.github.logicenum.formula.Formula.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

abstract class AlgorithmTestBase {

    protected final Formula a1 = var("a1");
    protected final Formula a2 = var("a2");
    protected final Formula b1 = var("b1");

    protected final Algorithm algorithm;

    protected AlgorithmTestBase(final Algorithm algorithm) {
        this.algorithm = algorithm;
    }

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
}