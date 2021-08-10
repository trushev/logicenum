package org.github.trushev.logicenum.formula;

import java.util.Collection;
import java.util.stream.Stream;

public abstract class Atom extends AbstractFormula {

    protected Atom(final Collection<Formula> operands, final int length) {
        super(operands, length);
    }

    @Override
    public Stream<Formula> operands() {
        return Stream.empty();
    }
}
