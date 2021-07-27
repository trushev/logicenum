package org.github.logicenum.formula;


import java.util.Collection;

final class Or extends BiFormula {

    Or(final Collection<Formula> fs) {
        super(Op.OR, fs);
    }
}
