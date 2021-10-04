package org.github.trushev.logicenum.enumeration;

import java.util.Iterator;
import org.github.trushev.logicenum.formula.Formula;

final class FormulasEnumInner implements Iterator<Formula> {

    private final Formulas formulas;

    private Iterator<Formula> iterator;
    private Formulas fixedLengthFormulas;
    private int formulaLength;

    FormulasEnumInner(Formulas formulas) {
        this.formulas = formulas;

        var initFormulaLength = 1;
        iterator = formulas.formulasWithLength(initFormulaLength).iterator();
        fixedLengthFormulas = new Formulas(initFormulaLength);
        formulaLength = initFormulaLength;
    }

    @Override
    public boolean hasNext() {
        return true;
    }

    @Override
    public Formula next() {
        if (iterator.hasNext()) {
            var f = iterator.next();
            if (f.length() == formulaLength && fixedLengthFormulas.put(f)) {
                return f;
            }
            while (iterator.hasNext()) {
                f = iterator.next();
                if (f.length() == formulaLength && fixedLengthFormulas.put(f)) {
                    return f;
                }
            }
        }
        formulas.merge(fixedLengthFormulas);
        formulaLength++;
        fixedLengthFormulas = new Formulas(formulaLength);
        System.out.println("Length: " + formulaLength + ", formula count: " + formulas.size());
        iterator = new FixedLengthFormulas(formulas, formulaLength);
        return next();
    }
}
