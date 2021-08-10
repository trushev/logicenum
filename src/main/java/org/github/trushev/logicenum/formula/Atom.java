package org.github.trushev.logicenum.formula;

import java.util.stream.Stream;

public abstract class Atom extends AbstractFormula {

    @Override
    public Stream<Formula> operands() {
        return Stream.empty();
    }
}
