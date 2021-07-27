package org.github.logicenum.formula;


import java.util.Collection;

final class And extends BiFormula {

    And(final Collection<Formula> fs) {
        super(Op.AND, fs);
    }
}
