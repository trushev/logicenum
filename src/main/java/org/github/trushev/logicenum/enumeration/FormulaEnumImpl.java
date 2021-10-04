package org.github.trushev.logicenum.enumeration;

import static java.util.Spliterator.*;

import java.util.Iterator;
import java.util.Spliterators;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;
import org.github.trushev.logicenum.formula.Formula;

final class FormulaEnumImpl implements FormulaEnum {

    private final Iterator<Formula> iterator;

    FormulaEnumImpl(Iterator<Formula> iterator) {
        this.iterator = iterator;
    }

    @Override
    public Stream<Formula> formulas() {
        return StreamSupport.stream(Spliterators.spliteratorUnknownSize(iterator, NONNULL | DISTINCT), false);
    }

    @Override
    public String toString() {
        return "FormulaEnumImpl{" + "iterator=" + iterator + '}';
    }
}
