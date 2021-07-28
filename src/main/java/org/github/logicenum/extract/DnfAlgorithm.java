package org.github.logicenum.extract;

import org.github.logicenum.formula.*;

import java.util.*;

import static java.util.Collections.*;
import static org.github.logicenum.formula.Formula.*;

public class DnfAlgorithm implements Algorithm {

    @Override
    public Formula ex(final Formula f, final Formula... attrs) {
        return toDnf(f, Set.of(attrs));
    }

    private Formula toDnf(final Formula f, final Collection<Formula> attrs) {
        final Collection<Formula> operands;
        if (f instanceof And) {
                operands = f.operands();
                final var firstDnf = toDnf(first(operands), attrs);
                final var restDnf = toDnf(and(rest(operands)), attrs);
                final var conjuncts = combinedConjuncts(disjunctions(firstDnf), disjunctions(restDnf), attrs);
                return or(conjuncts);
        }
        if (f instanceof Or) {
            operands = f.operands();
            return or(toDnfs(operands, attrs));
        }
        if (f instanceof Not not) {
            final var arg = operand(not);
            if (arg instanceof And) {
                operands = arg.operands();
                return toDnf(or(not(operands)), attrs);
            }
            if (arg instanceof Or) {
                operands = arg.operands();
                return toDnf(and(not(operands)), attrs);
            }
            if (arg instanceof Not) {
                return toDnf(first(operands(arg)), attrs);
            }
            return allowableOrTrue(not, attrs);
        }
        return allowableOrTrue(f, attrs);
    }

    private Collection<Formula> combinedConjuncts(
            final Collection<Formula> leftConjuncts,
            final Collection<Formula> rightConjuncts,
            final Collection<Formula> attrs) {

        final List<Formula> conjuncts = new ArrayList<>();
        for (final Formula left : leftConjuncts) {
            final boolean leftIsAllowable = left.consistsOnly(attrs);
            for (final Formula right : rightConjuncts) {
                final boolean rightIsAllowable = right.consistsOnly(attrs);
                if (leftIsAllowable && rightIsAllowable) {
                    conjuncts.add(and(Arrays.asList(left, right)));
                } else if (leftIsAllowable) {
                    conjuncts.add(left);
                } else if (rightIsAllowable) {
                    conjuncts.add(right);
                }
            }
        }
        if (conjuncts.isEmpty()) {
            return singletonList(Const.True);
        }
        return unmodifiableList(conjuncts);
    }

    private Collection<Formula> toDnfs(final Collection<Formula> fs, final Collection<Formula> attrs) {
        final List<Formula> list = new ArrayList<>();
        for (final Formula f : fs) {
            final Formula dnf = toDnf(f, attrs);
            if (dnf instanceof Or) {
                list.addAll(operands(dnf));
            } else {
                list.add(dnf);
            }
        }
        return unmodifiableList(list);
    }

    private Formula allowableOrTrue(final Formula f, final Collection<Formula> attrs) {
        if (f.consistsOnly(attrs)) {
            return f;
        } else {
            return Const.True;
        }
    }
}
