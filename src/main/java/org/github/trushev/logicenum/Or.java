package org.github.trushev.logicenum;


import java.util.Collection;

final class Or extends BiFormula {

    Or(final Collection<Formula> fs) {
        super(Op.or, fs);
    }
}
