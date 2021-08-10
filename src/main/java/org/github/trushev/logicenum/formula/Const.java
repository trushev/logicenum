package org.github.trushev.logicenum.formula;

import java.util.Objects;
import java.util.stream.Stream;

public final class Const extends Atom {

    public static final Const True = new Const(Val.TRUE);
    public static final Const False = new Const(Val.FALSE);
    public static final Const Unknown = new Const(Val.UNKNOWN);

    private final Val val;

    private Const(final Val val) {
        this.val = val;
    }

    public double asNumber() {
        return this.val.number;
    }

    @Override
    public Stream<Formula> vars() {
        return Stream.empty();
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
        TRUE("True", 1.0),
        FALSE("False", 0.0),
        UNKNOWN("Unknown", 0.5),
        ;

        private final String name;
        private final double number;

        Val(final String name, final double number) {
            this.name = name;
            this.number = number;
        }

        @Override
        public String toString() {
            return this.name;
        }
    }
}
