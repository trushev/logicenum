package org.github.trushev.logicenum.formula;

import java.util.Collection;
import java.util.stream.Stream;

public abstract sealed class Atom extends AbstractFormula permits Const, Var {

    protected Atom(Collection<Formula> operands, int length) {
        super(operands, length);
    }

    @Override
    public Stream<Formula> operands() {
        return Stream.empty();
    }
}
