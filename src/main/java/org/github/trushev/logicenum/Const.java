package org.github.trushev.logicenum;

import java.util.Objects;

final class Const implements Formula {

    static final Const True = new Const(Val.TRUE);
    static final Const False = new Const(Val.FALSE);

    private final Val val;

    private Const(final Val val) {
        this.val = val;
    }

    Const neg() {
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
        if (!(o instanceof Const aConst)) {
            return false;
        }
        return this.val == aConst.val;
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
