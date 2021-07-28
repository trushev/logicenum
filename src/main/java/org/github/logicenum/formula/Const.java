package org.github.logicenum.formula;

import java.util.Objects;

final class Const extends Atom {

    static final Const True = new Const(Val.TRUE);
    static final Const False = new Const(Val.FALSE);

    private final Val val;

    private Const(final Val val) {
        this.val = val;
    }

    @Override
    public int length() {
        return 1;
    }

    @Override
    public Formula not() {
        if (this.val == Val.TRUE) {
            return False;
        }
        return True;
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
        TRUE("t"),
        FALSE("f"),
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
