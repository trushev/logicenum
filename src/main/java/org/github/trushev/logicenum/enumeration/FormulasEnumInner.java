package org.github.trushev.logicenum.enumeration;

import java.util.Iterator;
import org.github.trushev.logicenum.formula.Formula;

final class FormulasEnumInner implements Iterator<Formula> {

    private final Formulas formulas;

    private Iterator<Formula> iterator;
    private Formulas fixedLengthFormulas;
    private int formulaLength;

    FormulasEnumInner(final Formulas formulas) {
        this.formulas = formulas;

        final var initFormulaLength = 1;
        this.iterator = this.formulas.formulasWithLength(initFormulaLength).iterator();
        this.fixedLengthFormulas = new Formulas(initFormulaLength);
        this.formulaLength = initFormulaLength;
    }

    @Override
    public boolean hasNext() {
        return true;
    }

    @Override
    public Formula next() {
        if (this.iterator.hasNext()) {
            var f = this.iterator.next();
            if (f.length() == this.formulaLength && this.fixedLengthFormulas.put(f)) {
                return f;
            }
            while (this.iterator.hasNext()) {
                f = this.iterator.next();
                if (f.length() == this.formulaLength && this.fixedLengthFormulas.put(f)) {
                    return f;
                }
            }
        }
        this.formulas.merge(this.fixedLengthFormulas);
        this.formulaLength++;
        this.fixedLengthFormulas = new Formulas(this.formulaLength);
        System.out.println("Length: " + this.formulaLength + ", formula count: " + this.formulas.size());
        this.iterator = new FixedLengthFormulas(this.formulas, this.formulaLength);
        return next();
    }
}
