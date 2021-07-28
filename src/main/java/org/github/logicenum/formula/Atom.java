package org.github.logicenum.formula;

import java.util.Collection;
import java.util.Collections;

abstract class Atom extends AbstractFormula {

    @Override
    public Collection<Formula> operands() {
        return Collections.emptyList();
    }
}
