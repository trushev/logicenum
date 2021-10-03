package org.github.trushev.logicenum.formula;

import java.util.Collection;
import java.util.stream.Stream;

abstract sealed class AbstractVarsFormula extends AbstractFormula permits BiFormula, UnaFormula {

    protected final Collection<Formula> vars;

    protected AbstractVarsFormula(
        final Collection<Formula> operands,
        final Collection<Formula> vars,
        final int length
    ) {
        super(operands, length);
        this.vars = vars;
    }

    @Override
    public Stream<Formula> vars() {
        return this.vars.stream();
    }
}
