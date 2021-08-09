package org.github.trushev.logicenum.implication;

import org.github.trushev.logicenum.formula.*;

import java.util.*;

import static java.util.Collections.*;

public class DnfImplication implements Implication {

    @Override
    public Formula ex(final Formula f, final Formula... attrs) {
        return toDnf(f, Set.of(attrs));
    }

    private Formula toDnf(final Formula f, final Collection<Formula> attrs) {
        final Collection<Formula> operands;
        if (f instanceof And) {
                operands = f.operands();
                final var firstDnf = toDnf(Formula.first(operands), attrs);
                final var restDnf = toDnf(Formula.and(Formula.rest(operands)), attrs);
                final var conjuncts = combinedConjuncts(Formula.disjunctions(firstDnf), Formula.disjunctions(restDnf), attrs);
                return Formula.or(conjuncts);
        }
        if (f instanceof Or) {
            operands = f.operands();
            return Formula.or(toDnfs(operands, attrs));
        }
        if (f instanceof Not not) {
            final var arg = Formula.operand(not);
            if (arg instanceof And) {
                operands = arg.operands();
                return toDnf(Formula.or(Formula.not(operands)), attrs);
            }
            if (arg instanceof Or) {
                operands = arg.operands();
                return toDnf(Formula.and(Formula.not(operands)), attrs);
            }
            if (arg instanceof Not) {
                return toDnf(Formula.first(Formula.operands(arg)), attrs);
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
                    conjuncts.add(Formula.and(Arrays.asList(left, right)));
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
                list.addAll(Formula.operands(dnf));
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
