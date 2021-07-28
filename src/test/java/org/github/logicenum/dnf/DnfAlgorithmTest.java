package org.github.logicenum.dnf;

import org.github.logicenum.formula.Const;
import org.github.logicenum.formula.Formula;
import org.junit.jupiter.api.Test;

import static org.github.logicenum.formula.Formula.*;
import static org.github.logicenum.formula.Formula.var;
import static org.junit.jupiter.api.Assertions.assertEquals;

class DnfAlgorithmTest {

    private final DnfAlgorithm algorithm = new DnfAlgorithm();
    private final Formula a1 = var("a1");
    private final Formula a2 = var("a2");
    private final Formula b1 = var("b1");
    private final Formula b2 = var("b2");

    @Test
    public void test0() {
        final var actual = algorithm.toDnf(a1, a1);
        assertEquals(a1, actual);
    }

    @Test
    public void test1() {
        final var actual = algorithm.toDnf(b1, a1);
        assertEquals(Const.True, actual);
    }

    @Test
    public void test2() {
        final var f = or(and(a1, b1), a2);
        final var expected = or(a1, a2);
        final var actual = algorithm.toDnf(f, a1, a2);
        assertEquals(expected, actual);
    }

    @Test
    public void test3() {
        final var f = or(and(a1, a2), b1);
        final var expected = Const.True;
        final var actual = algorithm.toDnf(f, a1, a2);
        assertEquals(expected, actual);
    }

    @Test
    public void test4() {
        final var f = not(or(a1, b1));
        final var expected = not(a1);
        final var actual = algorithm.toDnf(f, a1, a2, a1.not(), a2.not());
        assertEquals(expected, actual);
    }

    @Test
    public void test5() {
        final var f = not(and(a1, b1));
        final var expected = Const.True;
        final var actual = algorithm.toDnf(f, a1, a2, a1.not(), a2.not());
        assertEquals(expected, actual);
    }

    @Test
    public void test6() {
        final var f = not(and(or(a1, a2), b1));
        final var expected = Const.True;
        final var actual = algorithm.toDnf(f, a1, a2, a1.not(), a2.not());
        assertEquals(expected, actual);
    }
}
