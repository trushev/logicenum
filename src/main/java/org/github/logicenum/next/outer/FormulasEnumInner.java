package org.github.logicenum.next.outer;

import org.github.logicenum.formula.Formula;
import org.github.logicenum.next.Formulas;
import org.github.logicenum.next.mid.FixedLengthFormulas;

import java.util.Iterator;

final class FormulasEnumInner implements Iterator<Formula> {

    private final Formulas formulas;

    private int length;
    private Iterator<Formula> iterator;
    private Formulas currentFormulas;

    FormulasEnumInner(final Formula... fs) {
        this.formulas = new Formulas();
        this.formulas.put(fs);

        this.length = 1;
        this.iterator = this.formulas.getWithLength(this.length);
        this.currentFormulas = new Formulas();
    }

    @Override
    public boolean hasNext() {
        return true;
    }

    @Override
    public Formula next() {
        if (this.iterator.hasNext()) {
            var f = this.iterator.next();
            while (this.iterator.hasNext() && (f.length() != this.length || !this.currentFormulas.put(f))) {
                f = this.iterator.next();
            }
            return f;
        }
        this.formulas.merge(this.currentFormulas);
        this.currentFormulas = new Formulas();
        this.length++;
        System.out.println("Length: " + this.length);
        this.iterator = new FixedLengthFormulas(this.formulas, this.length);
        return next();
    }
}
