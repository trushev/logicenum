package org.github.trushev.logicenum.formula;


import java.util.Collection;

public final class And extends BiFormula {

    And(final Collection<Formula> fs) {
        super(Symbol.AND, fs);
    }
}
