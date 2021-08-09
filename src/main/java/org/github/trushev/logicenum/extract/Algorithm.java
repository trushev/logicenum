package org.github.trushev.logicenum.extract;

import org.github.trushev.logicenum.formula.Formula;

public interface Algorithm {

    Formula ex(final Formula f, final Formula... attrs);
}
