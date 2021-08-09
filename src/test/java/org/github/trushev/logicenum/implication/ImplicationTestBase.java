package org.github.trushev.logicenum.implication;

import org.github.trushev.logicenum.formula.Const;
import org.github.trushev.logicenum.formula.Formula;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

abstract class ImplicationTestBase {

    protected final Formula a1 = Formula.var("a1");
    protected final Formula a2 = Formula.var("a2");
    protected final Formula b1 = Formula.var("b1");

    protected final Implication implication;

    protected ImplicationTestBase(final Implication implication) {
        this.implication = implication;
    }

    @Test
    public void test0() {
        final var actual = implication.ex(a1, a1);
        assertEquals(a1, actual);
    }

    @Test
    public void test1() {
        final var actual = implication.ex(b1, a1);
        Assertions.assertEquals(Const.True, actual);
    }

    @Test
    public void test2() {
        final var f = Formula.or(Formula.and(a1, b1), a2);
        final var expected = Formula.or(a1, a2);
        final var actual = implication.ex(f, a1, a2);
        assertEquals(expected, actual);
    }

    @Test
    public void test3() {
        final var f = Formula.or(Formula.and(a1, a2), b1);
        final var expected = Const.True;
        final var actual = implication.ex(f, a1, a2);
        assertEquals(expected, actual);
    }

    @Test
    public void test4() {
        final var f = Formula.not(Formula.or(a1, b1));
        final var expected = Formula.not(a1);
        final var actual = implication.ex(f, a1, a2, a1.not(), a2.not());
        assertEquals(expected, actual);
    }

    @Test
    public void test5() {
        final var f = Formula.not(Formula.and(a1, b1));
        final var expected = Const.True;
        final var actual = implication.ex(f, a1, a2, a1.not(), a2.not());
        assertEquals(expected, actual);
    }

    @Test
    public void test6() {
        final var f = Formula.not(Formula.and(Formula.or(a1, a2), b1));
        final var expected = Const.True;
        final var actual = implication.ex(f, a1, a2, a1.not(), a2.not());
        assertEquals(expected, actual);
    }

    @Test
    public void test7() {
        final var f = Formula.or(Formula.and(a1, b1), Formula.not(a2));
        final var expected = Formula.or(a1, Formula.not(a2));
        final var actual = implication.ex(f, a1, a2, a1.not(), a2.not());
        assertEquals(expected, actual);
    }
}