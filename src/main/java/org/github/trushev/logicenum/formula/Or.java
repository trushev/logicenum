package org.github.trushev.logicenum.formula;


import java.util.Collection;

public final class Or extends BiFormula {

    Or(final Collection<Formula> fs) {
        super(Symbol.OR, fs);
    }
}