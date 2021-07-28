package org.github.logicenum.formula;

import java.util.Collection;
import java.util.Collections;
import java.util.Objects;

public final class Const extends Atom {

    public static final Const True = new Const(Val.TRUE);
    public static final Const False = new Const(Val.FALSE);
    public static final Const Unknown = new Const(Val.UNKNOWN);

    private final Val val;

    private Const(final Val val) {
        this.val = val;
    }

    @Override
    public Collection<Formula> vars() {
        return Collections.emptyList();
    }

    @Override
    public int length() {
        return 1;
    }

    @Override
    public Formula not() {
        return switch (this.val) {
            case FALSE -> True;
            case TRUE -> False;
            case UNKNOWN -> Unknown;
        };
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Const c)) {
            return false;
        }
        return this.val == c.val;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.val);
    }

    @Override
    public String toString() {
        return this.val.toString();
    }

    private enum Val {
        TRUE("True"),
        FALSE("False"),
        UNKNOWN("Unknown"),
        ;

        private final String name;

        Val(final String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return this.name;
        }
    }
}
