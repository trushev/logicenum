package org.github.trushev.logicenum;


import java.util.Collection;

final class And extends BiFormula {

    And(final Collection<Formula> fs) {
        super(Op.and, fs);
    }
}
