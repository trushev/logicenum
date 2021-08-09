package org.github.trushev.logicenum.formula;

import java.util.Collection;
import java.util.Collections;

public abstract class Atom extends AbstractFormula {

    @Override
    public Collection<Formula> operands() {
        return Collections.emptyList();
    }
}
