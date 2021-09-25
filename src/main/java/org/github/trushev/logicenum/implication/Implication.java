package org.github.trushev.logicenum.implication;

import org.github.trushev.logicenum.formula.Formula;

public interface Implication {
    Formula ex(final Formula f, final Formula... attrs);
}
