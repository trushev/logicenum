package org.github.trushev.logicenum.formula;

import java.util.Collection;
import java.util.stream.Stream;

abstract sealed class AbstractFormula implements Formula permits AbstractVarsFormula, Atom {

    protected final Collection<Formula> operands;
    private final int length;

    protected AbstractFormula(Collection<Formula> operands, int length) {
        this.operands = operands;
        this.length = length;
    }

    @Override
    public Stream<Formula> operands() {
        return this.operands.stream();
    }

    @Override
    public Formula or(Formula f) {
        return Or.of(this, f);
    }

    @Override
    public Formula and(Formula f) {
        return And.of(this, f);
    }

    @Override
    public Formula not() {
        return new Not(this);
    }

    @Override
    public Formula isNull() {
        return new IsNull(this);
    }

    @Override
    public int length() {
        return this.length;
    }

    @Override
    public boolean deepEquals(Formula f) {
        return this.equals(f) || Utils.deepEquals(this, f);
    }

    @Override
    public boolean consistsOnly(Collection<Formula> fs) {
        var vars = vars().toList();
        if (vars.isEmpty()) {
            return false; // TODO: why?
        }
        return vars.stream().filter(v -> !fs.contains(v)).findAny().isEmpty();
    }
}
