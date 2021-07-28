package org.github.logicenum.extract;

import org.github.logicenum.formula.Formula;

public interface Algorithm {

    Formula ex(final Formula f, final Formula... attrs);
}
