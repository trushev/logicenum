package org.github.trushev.logicenum.implication;

import org.github.trushev.logicenum.formula.Formula;

public interface Implication {
    Formula ex(Formula f, Formula... attrs);
}
