package org.github.trushev.logicenum.enumeration;

import org.github.trushev.logicenum.formula.Formula;

import java.util.Iterator;
import java.util.Spliterators;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import static java.util.Spliterator.*;

final class FormulaEnumImpl implements FormulaEnum {

    private final Iterator<Formula> iterator;

    FormulaEnumImpl(final Iterator<Formula> iterator) {
        this.iterator = iterator;
    }

    @Override
    public Stream<Formula> formulas() {
        return StreamSupport.stream(
                Spliterators.spliteratorUnknownSize(this.iterator, NONNULL | DISTINCT),
                false
        );
    }

    @Override
    public String toString() {
        return "FormulaEnumImpl{" +
                "iterator=" + this.iterator +
                '}';
    }
}
