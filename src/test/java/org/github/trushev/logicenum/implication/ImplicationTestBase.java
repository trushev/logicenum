package org.github.trushev.logicenum.implication;

import static org.github.trushev.logicenum.formula.Formula.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.github.trushev.logicenum.formula.Const;
import org.github.trushev.logicenum.formula.Formula;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public abstract class ImplicationTestBase {

    protected final Formula a1 = var("a1");
    protected final Formula a2 = var("a2");
    protected final Formula b1 = var("b1");

    protected final Implication implication;

    protected ImplicationTestBase(Implication implication) {
        this.implication = implication;
    }

    @Test
    public void test0() {
        var actual = implication.imply(a1, a1);
        assertEquals(a1, actual);
    }

    @Test
    public void test1() {
        var actual = implication.imply(b1, a1);
        Assertions.assertEquals(Const.True, actual);
    }

    @Test
    public void test2() {
        var f = or(and(a1, b1), a2);
        var expected = or(a1, a2);
        var actual = implication.imply(f, a1, a2);
        assertEquals(expected, actual);
    }

    @Test
    public void test3() {
        var f = or(and(a1, a2), b1);
        var expected = Const.True;
        var actual = implication.imply(f, a1, a2);
        assertEquals(expected, actual);
    }

    @Test
    public void test4() {
        var f = not(or(a1, b1));
        var expected = not(a1);
        var actual = implication.imply(f, a1, a2, a1.not(), a2.not());
        assertEquals(expected, actual);
    }

    @Test
    public void test5() {
        var f = not(and(a1, b1));
        var expected = Const.True;
        var actual = implication.imply(f, a1, a2, a1.not(), a2.not());
        assertEquals(expected, actual);
    }

    @Test
    public void test6() {
        var f = not(and(or(a1, a2), b1));
        var expected = Const.True;
        var actual = implication.imply(f, a1, a2, a1.not(), a2.not());
        assertEquals(expected, actual);
    }

    @Test
    public void test7() {
        var f = or(and(a1, b1), not(a2));
        var expected = or(a1, not(a2));
        var actual = implication.imply(f, a1, a2, a1.not(), a2.not());
        assertEquals(expected, actual);
    }
}
