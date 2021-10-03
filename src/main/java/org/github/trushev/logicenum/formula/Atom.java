package org.github.trushev.logicenum.formula;

import java.util.Collection;
import java.util.stream.Stream;

public abstract sealed class Atom extends AbstractFormula permits Const, Var {

    protected Atom(final Collection<Formula> operands, final int length) {
        super(operands, length);
    }

    @Override
    public Stream<Formula> operands() {
        return Stream.empty();
    }
}
