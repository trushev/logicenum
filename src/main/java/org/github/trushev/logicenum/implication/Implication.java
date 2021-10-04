package org.github.trushev.logicenum.implication;

import org.github.trushev.logicenum.formula.Formula;

public interface Implication {
    Formula imply(Formula f, Formula... attrs);
}
